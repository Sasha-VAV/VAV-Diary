package com.example.app.posts;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app.users.User;

@Dao
public interface PostDAO {

    @Query("SELECT * FROM Post_Table WHERE id=:id")
    PostForDB findById(int id);

    @Insert
    void insert(PostForDB postForDB);

}
