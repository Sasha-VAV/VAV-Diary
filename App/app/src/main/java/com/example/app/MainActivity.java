package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app.posts.Post;
import com.example.app.users.User;
import com.example.app.users.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private int id;
    private int key;
    public static boolean isLogged;
    public final static String NAME_SP = "1";
    private User user;
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private boolean logIn(){
        SharedPreferences sharedPreferences = getSharedPreferences(NAME_SP, MODE_PRIVATE);
        id = sharedPreferences.getInt("id",-1);
        key = sharedPreferences.getInt("key",-1);
        Log.d("ok2", String.valueOf(key));
        if (key == -1 || id == -1)
            return false;
        //TODO GET USER

        userRepository = new UserRepository(getApplication());

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
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isLogged) {

            isLogged = logIn();
            if (!isLogged) {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }

            Log.d("ok7", String.valueOf(key));
            Toast.makeText(MainActivity.this, String.valueOf("Logged with key:\n" + key), Toast.LENGTH_SHORT).show();
        }

        Button sendButton = findViewById(R.id.sendButton);
        EditText edHeader = findViewById(R.id.header_title);
        EditText edText = findViewById(R.id.text);
        EditText edTag = findViewById(R.id.edTag);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id = user.getPostLinkedList().size();
                int author = Math.abs(id) - Math.abs(key);
                Post post = new Post(id, author, edHeader.getText().toString()
                        , edTag.getText().toString()
                        , edText.getText().toString());



                if (id != -1){
                    Intent intent = new Intent(MainActivity.this, PostActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else{
                    //TODO check if it works
                    Toast toast = new Toast(getApplicationContext());
                    toast.setText("Something went wrong");
                    toast.show();
                }
            }
        });
        ImageButton toHomeButton = findViewById(R.id.toHome)
                , toDiaryButton = findViewById(R.id.toDiary)
                , toProfileButton = findViewById(R.id.toProfile);
        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });
        toDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });
        toProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });

    }
}