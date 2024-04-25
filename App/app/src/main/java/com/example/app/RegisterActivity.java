package com.example.app;


import static com.example.app.MainActivity.NAME_SP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.users.User;
import com.example.app.users.UserManager;
import com.example.app.users.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    private UserManager userManager;
    String s;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        userManager = new UserManager(getApplication());

        Button registerButton = findViewById(R.id.RegisterButton);
        EditText loginEd = findViewById(R.id.loginEd)
                , passwordEd = findViewById(R.id.passwordEd)
                , nametagEd = findViewById(R.id.nametagEd);


        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.get("LP") != null) {
            s = (String) extras.get("LP");
            loginEd.setText(s.substring(0,s.indexOf('|')));
            passwordEd.setText(s.substring(s.indexOf('|') + 1));
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    userManager.saveUserInCache(String.valueOf(loginEd.getText()).hashCode(),
                            String.valueOf(passwordEd.getText()).hashCode()
                    , String.valueOf(nametagEd.getText()));
                    Exception exception = new Exception();
                    if (!userManager.createUser(userManager.getCurrentUser()))
                        Toast.makeText(RegisterActivity.this, "This login already exists", Toast.LENGTH_SHORT).show();
                    else{
                        MainActivity.user = userManager.getCurrentUser();
                        Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception exception){
                    Toast.makeText(RegisterActivity.this, "This login already exists", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
