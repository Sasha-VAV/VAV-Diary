package com.example.app.Pages;

import static com.example.app.Pages.MainActivity.user;

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

import com.example.app.R;
import com.example.app.posts.Post;
import com.example.app.posts.PostManager;
import com.example.app.users.UserManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private PostManager postManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        UserManager userManager = new UserManager(getActivity().getApplication());
        userManager.setUser(user);

        postManager = new PostManager(getActivity().getApplication(), user);


        Button sendButton = view.findViewById(R.id.sendButton);
        EditText edHeader = view.findViewById(R.id.header_title);
        EditText edText = view.findViewById(R.id.text);
        EditText edTag = view.findViewById(R.id.edTag);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sH = edHeader.getText().toString()
                        , sTe = edText.getText().toString()
                        , sTa = edTag.getText().toString();
                if (sH.equals(""))
                    sH = getString(R.string.no_name);
                Post post = new Post(user.getId(), sH, sTa, sTe);
                postManager.createPost(post);
                user = userManager.refreshUser();

                DiaryFragment.currentPost = post;
                Navigation.findNavController(view).navigate(R.id.postFragment2);
            }
        });
        ImageButton toProfileSettingsButton = view.findViewById(R.id.postSettings)
                , toActionsButton = view.findViewById(R.id.importantActions);
        toProfileSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_postSettingsFragment);
            }
        });

        toActionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_actionsFragment2);
            }
        });


        //------------------------
        // Binds
        Button clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edHeader.setText("");
                edText.setText("");
                edTag.setText("#");
            }
        });

        return view;
    }
}