package com.example.app.users;

import static android.content.Context.MODE_PRIVATE;
import static com.example.app.Pages.MainActivity.NAME_SP;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Paint;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserManager {

    private User user = null;
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Application application;
    private MasterKey masterKey;
    private SharedPreferences sharedPreferences;

    public UserManager(Application application){
        userRepository = new UserRepository(application);
        this.application = application;


        try {
            masterKey = new MasterKey.Builder(application.getApplicationContext())
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            sharedPreferences = EncryptedSharedPreferences.create(
                    application.getApplicationContext(),
                    "secret_shared_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        }
        catch (Exception ignored){
            sharedPreferences = application.getSharedPreferences(NAME_SP, MODE_PRIVATE);
        }

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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", key);
        editor.putInt("id", id);
        //editor.putBoolean("firstTime", true);
        editor.apply();
        user = new User(id,key,name);
    }
    public void saveUserInCache(User user){
        int key = user.getKey();
        int id = user.getId();
        String name = user.getName();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", key);
        editor.putInt("id", id);
        //editor.putBoolean("firstTime", true);
        editor.apply();
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
        int id = sharedPreferences.getInt("id",-1);
        int key = sharedPreferences.getInt("key",-1);
        if (id == -1 || key == -1){
            return null;
        }
        user = getUser(id, key);
        return user;
    }
    public ArrayList<Boolean> getConfigFromCache(){
        ArrayList<Boolean> arrayList = new ArrayList<>();
        boolean firstTime = sharedPreferences.getBoolean("firstTime", false);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.remove("firstTime");
        //editor.apply();
        arrayList.add(firstTime);
        return arrayList;
    }
    public void clearCache(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
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
