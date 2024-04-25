package com.example.app;

import static com.example.app.MainActivity.NAME_SP;
import static com.example.app.MainActivity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.users.User;
import com.example.app.users.UserManager;
import com.example.app.users.UserRepository;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProfileActivity extends AppCompatActivity {
    private int id;
    private int key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);



        TextView name = findViewById(R.id.name);
        if (user.getPostIds() == null){
            user.setPostIds("");
        }
        name.setText(user.getName());

        Button settingsButton = findViewById(R.id.SettingsButton);
        ImageButton toHomeButton = findViewById(R.id.toHome)
                , toDiaryButton = findViewById(R.id.toDiary)
                , toProfileButton = findViewById(R.id.toProfile);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(NAME_SP, MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                Log.d("ok6", "unlogged");
                Toast.makeText(ProfileActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        toDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        toProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager userManager = new UserManager(getApplication());
                user.setName(user.getName() + "!");
                userManager.updateUser(user);
                userManager.saveUserInCache(user);
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
