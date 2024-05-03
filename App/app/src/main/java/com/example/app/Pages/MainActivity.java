package com.example.app.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.app.Authorization.WelcomeActivity;
import com.example.app.Notifications.MyWorker;
import com.example.app.Permissions.PermissionManager;
import com.example.app.R;
import com.example.app.users.User;
import com.example.app.users.UserManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static User user;
    public final static String NAME_SP = "1";
    public static boolean isSaveLocation = false;
    public static boolean isSendNotifications = false;

    public static Application application;
    private String localization;
    public static HashMap<String, String> dictionary1;
    public static PermissionManager permissionManager = new PermissionManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        application = getApplication();


        SharedPreferences sharedPreferences = getSharedPreferences(NAME_SP, MODE_PRIVATE);
        if (sharedPreferences!= null){
            isSaveLocation = sharedPreferences.getBoolean("loc", false) && PermissionManager.locationPermissionGranted();
            isSendNotifications = sharedPreferences.getBoolean("ntf", false) && PermissionManager.notificationPermissionGranted();
            localization = sharedPreferences.getString("lang","-1");
        }
        if (!Objects.equals(localization, "-1")){
            dictionary1 = new HashMap<>();
            dictionary1.put("Русский","ru");
            dictionary1.put("English","en");
            localization = dictionary1.get(localization);
            if (localization != null){
                Locale locale = new Locale(localization);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.locale = locale;
                getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
                MyWorker.header = getString(R.string.header_notification);
                MyWorker.text = getString(R.string.text_notification);
            }
        }

        setContentView(R.layout.activity_main);


        UserManager userManager = new UserManager(getApplication());
        if (user == null && userManager.getUserFromCache() == null) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (user == null && userManager.getCurrentUser() != null) {
            user = userManager.getCurrentUser();
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNV);
        NavController navController = Navigation.findNavController(this, R.id.hostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            if (bundle.getInt("was") == 1){
                navController.navigate(R.id.action_mainFragment_to_actionsFragment2);
            }
        }

        if (userManager.getConfigFromCache().get(0)){
            navController.navigate(R.id.action_mainFragment_to_postSettingsFragment);
        }

        ImageButton createPostButton = findViewById(R.id.createButton);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.mainFragment);
            }
        });

    }
}