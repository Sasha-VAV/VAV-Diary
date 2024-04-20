package com.example.app.posts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.app.users.User;
import com.example.app.users.UserDAO;

@Database(entities = {PostForDB.class},
        version = 1
        //, autoMigrations = {@AutoMigration(from = 1, to = 2)}
)
public abstract class PostDB extends RoomDatabase {
    public abstract PostDAO postDAO();
}
