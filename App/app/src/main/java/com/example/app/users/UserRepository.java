package com.example.app.users;

import android.app.Application;

import androidx.room.Room;

public class UserRepository {
    private final UserDAO userDAO;

    public UserRepository(Application application) {
        UserDB db = Room.databaseBuilder(application.getApplicationContext(), UserDB.class, "UserDB")
                .build();
        userDAO = db.userDAO();
    }

    public void insert(User user){
        userDAO.insert(user);
    }
    public User findById(int id){
        return userDAO.findById(id);
    }
    public void update(User user){
        userDAO.update(user);
    }
}
