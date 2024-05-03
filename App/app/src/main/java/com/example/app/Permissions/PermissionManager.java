package com.example.app.Permissions;

import static android.content.Context.MODE_PRIVATE;

import static com.example.app.Pages.MainActivity.application;
import static com.example.app.Pages.PostSettingsFragment.view;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.work.WorkManager;

import com.example.app.Pages.MainActivity;
import com.example.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class PermissionManager {

    public static ArrayList<String> PERMISSIONS = new ArrayList<>();
    public static String[] permissions;
    private AtomicBoolean isEnded = new AtomicBoolean(false);
    private Activity activity;
    boolean isSave = false;
    boolean isSend = false;
    boolean isApplied1 = false;
    boolean isApplied2 = false;
    public static final int REQUEST_CODE_PERMISSIONS = 1;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    public static boolean locationPermissionGranted(){
        PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
        AtomicBoolean good = new AtomicBoolean(false);
        permissions = new String[PERMISSIONS.size()];
        for (int i = 0; i < PERMISSIONS.size(); i++){
            permissions[i] = PERMISSIONS.get(i);
        }
        Arrays.asList(permissions).forEach(it ->{
            if (ContextCompat.checkSelfPermission(application, it) == PackageManager.PERMISSION_GRANTED
                    && (it.equals(Manifest.permission.ACCESS_FINE_LOCATION) || it.equals(Manifest.permission.ACCESS_COARSE_LOCATION))){
                good.set(true);
            }
        });
        return good.get();
    }
    public static boolean notificationPermissionGranted(){
        PERMISSIONS.add(Manifest.permission.POST_NOTIFICATIONS);
        AtomicBoolean good = new AtomicBoolean(true);
        permissions = new String[PERMISSIONS.size()];
        for (int i = 0; i < PERMISSIONS.size(); i++){
            permissions[i] = PERMISSIONS.get(i);
        }
        Arrays.asList(permissions).forEach(it ->{
            if (ContextCompat.checkSelfPermission(application, it) == PackageManager.PERMISSION_DENIED
                    && (it.equals(Manifest.permission.POST_NOTIFICATIONS))){
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
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_PERMISSIONS);
    }

    public void setLocation(boolean checked) {
        isSave = checked;
        if (!checked){
            applyLocation();
        }
        else {
            if (locationPermissionGranted())
                applyLocation();
            PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    private void applyLocation(){
        MainActivity.isSaveLocation = isSave;
        SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.NAME_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loc", isSave);
        editor.apply();
        isApplied1 = true;
    }
    public void applyLocation(boolean isSave){
        MainActivity.isSaveLocation = isSave;
        SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.NAME_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loc", isSave);
        editor.apply();
        isApplied1 = true;
    }

    public void setNotifications(boolean checked) {
        isSend = checked;
        if (!checked){
            WorkManager.getInstance(activity).cancelAllWork();
            applyNotifications();
        }
        else {
            if (notificationPermissionGranted())
                applyNotifications();
            PERMISSIONS.add(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
    private void applyNotifications(){
        MainActivity.isSendNotifications = isSend;
        SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.NAME_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ntf", isSend);
        editor.apply();
        isApplied2 = true;
    }
    public void applyNotifications(boolean isSend){
        MainActivity.isSendNotifications = isSend;
        SharedPreferences sharedPreferences = application.getSharedPreferences(MainActivity.NAME_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ntf", isSend);
        editor.apply();
        isApplied2 = true;
    }

    public void launch() {
        if (!(isApplied1 && isApplied2)){
            askForPermissions();
        }
        else {
        }
    }
    public void apply(){
        applyNotifications();
        askForPermissions();
    }
}
