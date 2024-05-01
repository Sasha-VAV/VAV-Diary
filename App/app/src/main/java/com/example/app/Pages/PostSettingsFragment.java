package com.example.app.Pages;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app.R;

import java.util.ArrayList;
import java.util.Arrays;
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


    private static ArrayList<String> PERMISSIONS = new ArrayList<>();
    private static String[] permissions;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_settings, container, false);

        CheckBox checkBox = view.findViewById(R.id.saveLocation);
        checkBox.setChecked(MainActivity.isSaveLocation);
        Button saveButton = view.findViewById(R.id.sendButton);
        ImageButton returnButton = view.findViewById(R.id.returnButton);
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
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.NAME_SP, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loc", true);
                    editor.apply();
                }
                Navigation.findNavController(view).navigateUp();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        return view;
    }

    private ActivityResultLauncher<String[]> requestLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                AtomicBoolean permissionGranted = new AtomicBoolean(true);
                permissions.forEach((key, value) -> {
                    if (Arrays.asList(PERMISSIONS).contains(key) && !value){
                        Toast.makeText(getActivity(),
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
            if (ContextCompat.checkSelfPermission(getActivity(), it) == PackageManager.PERMISSION_DENIED
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