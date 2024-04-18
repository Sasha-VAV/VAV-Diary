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
import com.example.app.users.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    String s;
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Button registerButton = findViewById(R.id.RegisterButton);
        EditText loginEd = findViewById(R.id.loginEd)
                , passwordEd = findViewById(R.id.passwordEd)
                , nametagEd = findViewById(R.id.nametagEd);


        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.get("LP") != null) {
            s = (String) extras.get("LP");
            loginEd.setText(s.substring(0,s.indexOf('|')));
            passwordEd.setText(s.substring(s.indexOf('|') + 1));
            Log.d("ok1", s);
        }

        userRepository = new UserRepository(getApplication());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO SAVE NAME TAG

                String s1 = String.valueOf(loginEd.getText());
                String s2 = String.valueOf(passwordEd.getText());
                SharedPreferences.Editor editor = getSharedPreferences(NAME_SP, MODE_PRIVATE).edit();
                Log.d("ok4", String.valueOf(s.hashCode()));
                editor.putInt("key", s2.hashCode());
                editor.putInt("id", s1.hashCode());
                editor.apply();
                //TODO DB

                User user = new User(s1.hashCode(), s2.hashCode(), nametagEd.getText().toString());

                try{
                    executorService.execute(() -> userRepository.insert(user));
                }
                catch (Exception ignored){
                    Toast.makeText(RegisterActivity.this, "This login already exists", Toast.LENGTH_SHORT).show();
                }



                //END of db
                //Toast.makeText(RegisterActivity.this, "Signed up as\n"+userRepository.findById(s.hashCode()), Toast.LENGTH_SHORT).show();

                MainActivity.isLogged = true;
                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                intent.putExtra("key", s2.hashCode());
                intent.putExtra("id", user.getId());
                startActivity(intent);
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
