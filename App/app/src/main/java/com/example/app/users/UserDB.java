package com.example.app.users;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},
        version = 1
        //, autoMigrations = {@AutoMigration(from = 1, to = 2)}
)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDAO userDAO();
}
