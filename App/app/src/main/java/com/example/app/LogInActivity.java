package com.example.app;


import static com.example.app.MainActivity.NAME_SP;

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
import com.example.app.users.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LogInActivity extends AppCompatActivity {
    public Handler handler;
    private final int ADMIN_KEY = "Password".hashCode();
    private final int ADMIN_ID = "Admin".hashCode();
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
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

        userRepository = new UserRepository(getApplication());

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences sharedPreferences = getSharedPreferences(NAME_SP, MODE_PRIVATE);
                //int key = sharedPreferences.getInt("key",-1);
                //Toast.makeText(LogInActivity.this, key, Toast.LENGTH_SHORT).show();
                int id = loginEd.getText().toString().hashCode();
                int key = passwordEd.getText().toString().hashCode();
                if (key == ADMIN_KEY && id == ADMIN_ID) {
                    Log.d("ok5", String.valueOf(key));
                    Toast.makeText(LogInActivity.this, "Logged as admin", Toast.LENGTH_SHORT).show();

                    //Remember user
                    SharedPreferences.Editor editor = getSharedPreferences(NAME_SP, MODE_PRIVATE).edit();
                    editor.putInt("key", key);
                    editor.putInt("id", id);
                    editor.apply();

                    MainActivity.isLogged = true;


                    Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    AtomicBoolean atomicBooleanOk = new AtomicBoolean();
                    atomicBooleanOk.set(false);
                    AtomicBoolean stop = atomicBooleanOk;
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean ok = false;
                            ok = userRepository.findById(id) != null;

                            //Toast.makeText(LogInActivity.this, Boolean.toString(ok), Toast.LENGTH_SHORT).show();
                            if (ok) {
                                //Toast.makeText(LogInActivity.this, Boolean.toString(ok), Toast.LENGTH_SHORT).show();
                                User user = userRepository.findById(id);
                                if (user.getKey() != key){
                                    atomicBooleanOk.set(true);
                                }
                                else{
                                    SharedPreferences.Editor editor = getSharedPreferences(NAME_SP, MODE_PRIVATE).edit();
                                    editor.putInt("id", user.getId());
                                    editor.putInt("key", key);
                                    editor.apply();

                                    //Toast.makeText(LogInActivity.this, "Logged as admin" + user.getName(), Toast.LENGTH_SHORT).show();

                                    MainActivity.isLogged = true;

                                    Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                                    intent.putExtra("key", key);
                                    intent.putExtra("id", user.getId());
                                    stop.set(true);
                                    startActivity(intent);
                                }
                            }
                            else{
                                atomicBooleanOk.set(true);
                            }

                        }
                    });
                    while (true)
                    {
                        if (atomicBooleanOk.get()) {
                            Toast.makeText(LogInActivity.this, "Wrong login or password"
                                            , Toast.LENGTH_SHORT)
                                    .show();
                            stop.set(true);
                        }
                        if (stop.get())
                            break;
                    }
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
