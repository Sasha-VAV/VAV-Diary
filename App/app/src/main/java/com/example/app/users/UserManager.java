package com.example.app.users;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserManager {

    private User user = null;
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public UserManager(Application application) {
        userRepository = new UserRepository(application);
    }

    public User findById(int id){

        AtomicBoolean ok = new AtomicBoolean(false);
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
        while (!isEnded.get()){

        }
        return user;
    }
    public boolean createUser(User user){
        this.user = user;
        try {
            executorService.execute(() -> userRepository.insert(user));
            return true;
        }
        catch (Exception ignored){
            return false;
        }
    }

}
