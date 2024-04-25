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
import com.example.app.posts.PostManager;
import com.example.app.users.User;
import com.example.app.users.UserManager;
import com.example.app.users.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    public static User user;
    public final static String NAME_SP = "1";
    private PostManager postManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager userManager = new UserManager(getApplication());
        if (user == null && userManager.getUserFromCache() == null) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (user == null && userManager.getCurrentUser() != null) {
            user = userManager.getCurrentUser();
        }
        userManager.setUser(user);
        postManager = new PostManager(getApplication(), user);

        Button sendButton = findViewById(R.id.sendButton);
        EditText edHeader = findViewById(R.id.header_title);
        EditText edText = findViewById(R.id.text);
        EditText edTag = findViewById(R.id.edTag);
        edHeader.setText("test2");
        edText.setText("test2t");
        edTag.setText("#test2");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sH = edHeader.getText().toString()
                        , sTe = edText.getText().toString()
                        , sTa = edTag.getText().toString();
                Post post = new Post(user.getId(), sH, sTa, sTe);
                postManager.createPost(post);
                user = userManager.refreshUser();

                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        ImageButton toHomeButton = findViewById(R.id.toHome)
                , toDiaryButton = findViewById(R.id.toDiary)
                , toProfileButton = findViewById(R.id.toProfile);
        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        toDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        toProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}