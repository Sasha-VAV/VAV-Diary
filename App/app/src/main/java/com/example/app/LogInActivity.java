package com.example.app;


import static com.example.app.MainActivity.NAME_SP;
import static com.example.app.MainActivity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.users.User;
import com.example.app.users.UserManager;
import com.example.app.users.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LogInActivity extends AppCompatActivity {
    private final int ADMIN_KEY = "Password".hashCode();
    private final int ADMIN_ID = "Admin".hashCode();
    private UserManager userManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Button logInButton = findViewById(R.id.LogInButton);
        Button registerButton = findViewById(R.id.RegisterButton);
        EditText loginEd = findViewById(R.id.loginEd)
                , passwordEd = findViewById(R.id.passwordEd);



        loginEd.setText("Admin");
        passwordEd.setText("Password");

        userManager = new UserManager(getApplication());

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = loginEd.getText().toString().hashCode();
                int key = passwordEd.getText().toString().hashCode();
                user = userManager.getUser(id, key);
                if (user == null){
                    Toast.makeText(LogInActivity.this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                }
                else{
                    userManager.saveUserInCache(user);
                    Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = loginEd.getText() + "|" + passwordEd.getText();
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                intent.putExtra("LP",s);
                startActivity(intent);
            }
        });
        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
