package com.example.app.Pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.users.UserManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "LP";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserManager userManager;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        userManager = new UserManager(getActivity().getApplication());

        Button registerButton = view.findViewById(R.id.RegisterButton);
        EditText loginEd = view.findViewById(R.id.loginEd)
                , passwordEd = view.findViewById(R.id.passwordEd)
                , nameTagEd = view.findViewById(R.id.nameTagEd);

        if (mParam1 != null) {
            loginEd.setText(mParam1.substring(0,mParam1.indexOf('|')));
            passwordEd.setText(mParam1.substring(mParam1.indexOf('|') + 1));
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    userManager.saveUserInCache(String.valueOf(loginEd.getText()).hashCode(),
                            String.valueOf(passwordEd.getText()).hashCode()
                            , String.valueOf(nameTagEd.getText()));
                    if (!userManager.createUser(userManager.getCurrentUser()))
                        Toast.makeText(getActivity(), R.string.this_login_already_exists, Toast.LENGTH_SHORT).show();
                    else{
                        MainActivity.user = userManager.getCurrentUser();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception exception){
                    Toast.makeText(getActivity(), R.string.this_login_already_exists, Toast.LENGTH_SHORT).show();
                }

            }
        });
        ImageButton returnButton = view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        return view;
    }
}