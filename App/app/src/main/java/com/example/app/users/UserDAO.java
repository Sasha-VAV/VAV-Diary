package com.example.app.users;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User_Table WHERE id=:id")
    User findById(int id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

}
