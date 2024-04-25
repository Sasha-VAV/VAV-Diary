package com.example.app;

import static com.example.app.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.posts.Post;
import com.example.app.posts.PostFragment;
import com.example.app.posts.PostManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiaryActivity extends AppCompatActivity {
    private PostManager postManager;
    public static Post currentPost;
    public static boolean postIsClicked;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_activity);
        ImageButton toHomeButton = findViewById(R.id.toHome)
                , toDiaryButton = findViewById(R.id.toDiary)
                , toProfileButton = findViewById(R.id.toProfile);

        postManager = new PostManager(getApplication(), user);
        TextView tx = findViewById(R.id.tx);
        EditText ed = findViewById(R.id.ed);
        //tx.setText(user.getPostIds());
        //Post post = postManager.findPostById(2);
        //tx.setText(post.toString());
        //tx.setText(postManager.getUserPosts(-1).toString());

        PostFragment postFragment = new PostFragment(postManager.getUserPosts(-1), getApplication());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!postFragment.isAdded())
            ft.add(R.id.fr1, postFragment);
        ft.commit();
        Runnable onPostListener = new Runnable() {
            @Override
            public void run() {
                while (true){
                    while (!postIsClicked){

                    }
                    postIsClicked = false;
                    Intent intent = new Intent(DiaryActivity.this, PostActivity.class);
                    startActivity(intent);
                }
            }
        };
        executorService.execute(onPostListener);


        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Post post = postManager.findPostById(Integer.parseInt(ed.getText().toString()));
                    tx.setText(post.toString());
                }
                catch (Exception ignored){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    Post post = postManager.findPostByAuthorN(Integer.parseInt(s.toString()), user.getId());
                    tx.setText(post.toString());

                    //--------------------------------------
                    /*

                    TextView textView = findViewById(R.id.text);
                    TextView textTime = findViewById(R.id.textTime);
                    textTime.setText(post.getTime());
                    textView.setText(post.getHead());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentPost = post;
                            Intent intent = new Intent(DiaryActivity.this, PostActivity.class);
                            startActivity(intent);
                        }
                    });

                     */
                    //--------------------------------------
                }
                catch (Exception ignored){
                    tx.setText("Unlucky");
                }
            }
        });








        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        toDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        toProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
