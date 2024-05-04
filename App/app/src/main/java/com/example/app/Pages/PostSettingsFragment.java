package com.example.app.Pages;

import static com.example.app.Pages.MainActivity.permissionManager;
import static com.example.app.Permissions.PermissionManager.REQUEST_CODE_PERMISSIONS;
import static com.example.app.Permissions.PermissionManager.locationPermissionGranted;
import static com.example.app.Permissions.PermissionManager.notificationPermissionGranted;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app.R;

import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AtomicBoolean isEnded = new AtomicBoolean(false);


    public PostSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment postSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostSettingsFragment newInstance(String param1, String param2) {
        PostSettingsFragment fragment = new PostSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_settings, container, false);
        permissionManager.setActivity(getActivity());

        CheckBox locationCheckbox = view.findViewById(R.id.saveLocation);
        CheckBox notificationCheckbox = view.findViewById(R.id.sendNotifications);
        locationCheckbox.setChecked(MainActivity.isSaveLocation);
        notificationCheckbox.setChecked(MainActivity.isSendNotifications);
        Button saveButton = view.findViewById(R.id.sendButton);
        ImageButton returnButton = view.findViewById(R.id.returnButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View v) {
                permissionManager.setLocation(locationCheckbox.isChecked());
                permissionManager.setNotifications(notificationCheckbox.isChecked());
                isEnded.set(true);
                permissionManager.launch();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
                if (isEnded.get()){
                    permissionManager.setLocation(locationCheckbox.isChecked());
                    permissionManager.setNotifications(notificationCheckbox.isChecked());
                    if (!notificationCheckbox.isChecked())
                        Toast.makeText(getContext(), R.string.we_wont_send_notifications_again, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS){
            boolean allGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted){
                permissionManager.apply();
                isEnded.set(true);
            }
            else {
                permissionManager.applyLocation(locationPermissionGranted());
                permissionManager.applyNotifications(notificationPermissionGranted());
            }
        }
    }
}