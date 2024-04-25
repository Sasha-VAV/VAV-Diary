package com.example.app.posts;

import android.app.Application;

import androidx.room.Room;

import com.example.app.users.User;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    private final PostDAO postDAO;

    public PostRepository(Application application) {
        PostDB db = Room.databaseBuilder(application.getApplicationContext(), PostDB.class, "PostDB")
                .build();
        postDAO = db.postDAO();
    }

    public int insert(PostForDB postForDB){
        postForDB.setId((int) postDAO.insert(postForDB));
        return postForDB.getId();
    }
    public PostForDB findById(int id){
        return postDAO.findById(id);
    }

    public ArrayList<PostForDB> findByAuthor(int author){
        ArrayList<PostForDB> result = new ArrayList<>(postDAO.findByAuthor(author));
        return result;
    }

}
