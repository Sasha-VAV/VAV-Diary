package com.example.app;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class PostSettingsActivity extends AppCompatActivity {
    private static ArrayList<String> PERMISSIONS = new ArrayList<>();
    private static String[] permissions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_settings);
        CheckBox checkBox = findViewById(R.id.saveLocation);
        checkBox.setActivated(MainActivity.isSaveLocation);
        Button saveButton = findViewById(R.id.sendButton);
        ImageButton returnButton = findViewById(R.id.returnButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO request a permission
                // What will be if you gonna send no permissions

                if (checkBox.isChecked()){
                    PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    PERMISSIONS.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
                    if (!locationPermissionGranted()){
                        askForPermissions();
                    }
                    MainActivity.isSaveLocation = true;
                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.NAME_SP, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loc", true);
                    editor.apply();
                }
                finish();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private ActivityResultLauncher<String[]> requestLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                AtomicBoolean permissionGranted = new AtomicBoolean(true);
                permissions.forEach((key, value) -> {
                    if (Arrays.asList(PERMISSIONS).contains(key) && !value){
                        Toast.makeText(this,
                                key+" "+ value,
                                Toast.LENGTH_SHORT).show();
                        permissionGranted.set(false);
                    }
                });
            });
    public boolean locationPermissionGranted(){
        AtomicBoolean good = new AtomicBoolean(true);
        permissions = new String[PERMISSIONS.size()];
        for (int i = 0; i < PERMISSIONS.size(); i++){
            permissions[i] = PERMISSIONS.get(i);
        }
        Arrays.asList(permissions).forEach(it ->{
            if (ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED
            && (it.equals(Manifest.permission.ACCESS_FINE_LOCATION) || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION))){
                good.set(false);
            }
        });
        return good.get();
    }
    public void askForPermissions(){
        permissions = new String[PERMISSIONS.size()];
        for (int i = 0; i < PERMISSIONS.size(); i++){
            permissions[i] = PERMISSIONS.get(i);
        }
        requestLauncher.launch(permissions);
    }
}
