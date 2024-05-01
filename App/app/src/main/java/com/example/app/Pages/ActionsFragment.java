package com.example.app.Pages;

import static com.example.app.Pages.MainActivity.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.example.app.days.DayManager;
import com.example.app.posts.PostManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DayManager dayManager;
    private PostManager postManager;

    public ActionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment actionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionsFragment newInstance(String param1, String param2) {
        ActionsFragment fragment = new ActionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_actions, container, false);

        EditText nDays = view.findViewById(R.id.nDays);
        //Button sendOnServerButton = view.findViewById(R.id.sendButton);
        Button copyData = view.findViewById(R.id.copyButton);

        postManager = new PostManager(getActivity().getApplication(), user);
        dayManager = new DayManager(postManager.getUserPosts(-1));

        copyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n;
                if (nDays.getText().toString().equals(""))
                    n = 7;
                else
                    n = Integer.parseInt(nDays.getText().toString());
                ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Last " + n + " days", dayManager.getLastNDays(n));
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getActivity().getApplicationContext(), "Скопировано!", Toast.LENGTH_SHORT).show();

                //MAYBE TODO SHARE WITH
            }
        });
        ImageButton returnButton = view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        /*sendOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO BACKEND
            }
        });*/

        return view;
    }
}