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

public class MainActivity extends AppCompatActivity {
    //public static Handler handler;
    private int key;
    public static boolean isLogged;
    public final static String NAME_SP = "1";
    private boolean logIn(){
        SharedPreferences sharedPreferences = getSharedPreferences(NAME_SP, MODE_PRIVATE);
        key = sharedPreferences.getInt("key",-1);
        Log.d("ok2", String.valueOf(key));
        if (key == -1)
            return false;

        //Toast.makeText(MainActivity.this, key, Toast.LENGTH_SHORT).show();
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
        EditText editText = findViewById(R.id.header_title);
        EditText editText1 = findViewById(R.id.text);
        EditText editText2 = findViewById(R.id.edTag);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(editText.getText().toString()
                        , editText2.getText().toString()
                        , editText1.getText().toString());
                int id = post.sendPost(key);
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