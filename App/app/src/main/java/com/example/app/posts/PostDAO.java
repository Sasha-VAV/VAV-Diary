package com.example.app.posts;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app.users.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PostDAO {

    @Query("SELECT * FROM Post_Table WHERE id=:id")
    PostForDB findById(int id);

    @Query("SELECT * FROM Post_Table WHERE Post_author=:author")
    List<PostForDB> findByAuthor(int author);

    @Insert
    long insert(PostForDB postForDB);

}
