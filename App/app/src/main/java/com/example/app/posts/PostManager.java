package com.example.app.posts;

import android.app.Application;

import com.example.app.users.User;
import com.example.app.users.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class PostManager {
    private User user;
    private ArrayList<Post> posts;
    private ArrayList<PostForDB> postForDBS;
    private PostRepository postRepository;
    private Application application;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    PostForDB postForDB;

    public PostManager(Application application, User user) {
        this.user = user;
        this.application = application;
        postRepository = new PostRepository(application);
    }

    public Post findPostById(int id){

        AtomicBoolean isEnded = new AtomicBoolean(false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                postForDB = postRepository.findById(id);
                isEnded.set(true);
            }
        };

        executorService.execute(runnable);

        while (!isEnded.get()){

        }
        if (postForDB == null || postForDB.getAuthor() != user.getId())
            return null;
        return new Post(postForDB);
    }
    public ArrayList<Post> findAllPosts(int author){
        AtomicBoolean isEnded = new AtomicBoolean(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                postForDBS = postRepository.findByAuthor(author);
                isEnded.set(true);
            }
        };

        executorService.execute(runnable);

        while (!isEnded.get()){

        }
        posts = new ArrayList<>();
        for (PostForDB it:
                postForDBS) {
            posts.add(new Post(it));
        }
        return posts;
    }
    public Post findPostByAuthorN(int n, int author){
        if (posts == null)
            posts = findAllPosts(author);
        if (n < 0)
            return posts.get(posts.size() + n);
        return posts.get(n);
    }
    
    public boolean createPost(Post post){

        postForDB = post.toPostForDB();
        AtomicBoolean isEnded = new AtomicBoolean(false);
        AtomicBoolean isError = new AtomicBoolean(false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    int id = postRepository.insert(postForDB);
                    user.insertPostId(id);
                    UserManager userManager = new UserManager(application);
                    userManager.updateUser(user);
                    isEnded.set(true);
                }
                catch (Exception ignored){
                    isError.set(true);
                }
            }
        };
        executorService.execute(runnable);
        while (!isEnded.get() && !isError.get()){

        }
        return isEnded.get();
    }

    public ArrayList<Post> getUserPosts(int n){

        /*posts = new ArrayList<>();
        String s = user.getPostIds();
        if (Objects.equals(s, ""))
            return posts;
        ArrayList<Integer> ids = new ArrayList<>();
        while (s.length() > 0){
            int id = Integer.parseInt(s.substring(1, s.indexOf("}")));
            ids.add(id);
            s = s.substring(s.indexOf("}") + 1);
        }
        if (n == -1)
            n = ids.size();
        for (int i = ids.size() - 1; i >ids.size()- n - 1; i--) {
            if (ids.size()- n - 1 == 0)
                return posts;
            int id = ids.get(i);
            Post post = findPostById(id);
            if (post != null)
                posts.add(post);
        }
        return posts;*/
        if (posts == null)
            posts = findAllPosts(user.getId());
        ArrayList<Post> arrayList = new ArrayList<>();
        if (n == -1)
            n = posts.size();
        for (int i = posts.size() - 1; i > posts.size() - n - 1; i--){
            if (i < 0)
                return arrayList;
            arrayList.add(posts.get(i));
        }

        return arrayList;
    }
    public ArrayList<Post> getPosts(){
        return posts;
    }
}
