package com.example.app.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.app.Authorization.WelcomeActivity;
import com.example.app.R;
import com.example.app.posts.PostManager;
import com.example.app.users.User;
import com.example.app.users.UserManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static User user;
    public final static String NAME_SP = "1";
    public static boolean isSaveLocation = false;

    public static Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Locale locale = new Locale("ru");
        Locale.setDefault(locale);

        //ALL TODO 1 english language 2 notifications 3 all text to string value 4 SecuritySharedPreferences

        SharedPreferences sharedPreferences = getSharedPreferences(NAME_SP, MODE_PRIVATE);
        if (sharedPreferences!= null){
            isSaveLocation = sharedPreferences.getBoolean("loc", false);
        }

        application = getApplication();
        UserManager userManager = new UserManager(getApplication());



        if (user == null && userManager.getUserFromCache() == null) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (user == null && userManager.getCurrentUser() != null) {
            user = userManager.getCurrentUser();
        }

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNV);
        NavController navController = Navigation.findNavController(this, R.id.hostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        ImageButton createPostButton = findViewById(R.id.createButton);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.mainFragment);
            }
        });

    }
}