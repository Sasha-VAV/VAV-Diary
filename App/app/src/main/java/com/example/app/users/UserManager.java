package com.example.app.users;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.SharedPreferencesKt.edit;
import static com.example.app.MainActivity.NAME_SP;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserManager {

    private User user = null;
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Application application;

    public UserManager(Application application) {
        userRepository = new UserRepository(application);
        this.application = application;
    }

    private User findById(int id){

        AtomicBoolean ok = new AtomicBoolean(true);
        AtomicBoolean isEnded = new AtomicBoolean(false);
        Runnable findUser = new Runnable() {
            @Override
            public void run() {
                user = userRepository.findById(id);
                if (user == null)
                    ok.set(false);
                isEnded.set(true);
            }
        };

        executorService.execute(findUser);
        while (!isEnded.get() && ok.get()){

        }
        if (!ok.get())
            return null;
        return user;
    }
    public boolean createUser(User user){
        this.user = user;
        try {
            AtomicBoolean isEnded = new AtomicBoolean(false);
            AtomicBoolean isError = new AtomicBoolean(false);
            Runnable findUser = new Runnable() {
                @Override
                public void run() {
                    try {
                        userRepository.insert(user);
                        isEnded.set(true);
                    }
                    catch (Exception ignored){
                        isError.set(true);
                    }
                }
            };



            executorService.execute(findUser);
            while (!isEnded.get()){
                if (isError.get()){
                    return false;
                }
            }
            //TODO NAV BUTTON
            return true;
        }
        catch (Exception ignored){
            return false;
        }
    }
    public void saveUserInCache(int id, int key, String name){
        SharedPreferences.Editor editor = application.getSharedPreferences(NAME_SP, MODE_PRIVATE).edit();
        editor.putInt("key", key);
        editor.putInt("id", id);
        editor.apply();
        user = new User(id,key,name);
    }
    public void saveUserInCache(User user){
        int key = user.getKey();
        int id = user.getId();
        String name = user.getName();
        SharedPreferences.Editor editor = application.getSharedPreferences(NAME_SP, MODE_PRIVATE).edit();
        editor.putInt("key", key);
        editor.putInt("id", id);
        editor.apply();
        user = new User(id,key,name);
    }
    public User getCurrentUser(){
        return user;
    }
    public User getUser(int id, int key) {
        user = findById(id);
        if (user == null)
            return null;
        if (user.getKey() != key)
            return null;
        return user;
    }
    public User getUserFromCache(){
        SharedPreferences sharedPreferences = application.getSharedPreferences(NAME_SP, MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",-1);
        int key = sharedPreferences.getInt("key",-1);
        if (id == -1 || key == -1){
            return null;
        }
        user = getUser(id, key);
        return user;
    }

    public boolean addPostId(int id){
        user.insertPostId(id);
        updateUser(user);
        return true;
    }
    public User refreshUser(){
        return findById(user.getId());
    }
    public boolean updateUser(User user){
        try
        {
            AtomicBoolean isEnded = new AtomicBoolean(false);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    userRepository.update(user);
                    isEnded.set(true);
                }
            };
            executorService.execute(runnable);
            while (!isEnded.get()) {

            }
            return true;
        }
        catch (Exception ignored){
            return false;
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
