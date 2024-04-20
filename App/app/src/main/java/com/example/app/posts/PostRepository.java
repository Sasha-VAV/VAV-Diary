package com.example.app.posts;

import android.app.Application;

import androidx.room.Room;

import com.example.app.users.User;

public class PostRepository {
    private final PostDAO postDAO;

    public PostRepository(Application application) {
        PostDB db = Room.databaseBuilder(application.getApplicationContext(), PostDB.class, "PostDB")
                .build();
        postDAO = db.postDAO();
    }

    public void insert(PostForDB postForDB){
        postDAO.insert(postForDB);
    }
    public PostForDB findById(int id){
        return postDAO.findById(id);
    }
}
