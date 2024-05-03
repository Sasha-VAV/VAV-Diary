package com.example.app.Pages;

import static com.example.app.Pages.DiaryFragment.currentPost;
import static com.example.app.Pages.MainActivity.application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
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
        View view = inflater.inflate(R.layout.fragment_post2, container, false);

        TextView textDate = view.findViewById(R.id.textDate)
                , textTime = view.findViewById(R.id.textTime)
                , textHead = view.findViewById(R.id.header_title)
                , text = view.findViewById(R.id.text);
        textDate.setText(currentPost.getDate());
        textTime.setText(currentPost.getTime());
        textHead.setText(currentPost.getHead());
        text.setText(currentPost.getText());
        //ImageButton imageButton = findViewById(R.id.imgButton);


        TextView textLocation = view.findViewById(R.id.textLocation);
        textLocation.setText(currentPost.getL());

        ImageButton returnButton = view.findViewById(R.id.returnButton);
        textLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = currentPost.getL();
                String latitude = currentPost.getLatitude();
                String longitude = currentPost.getLongitude();
                String label = getString(R.string.you_was_here) + " " + currentPost.getDate() + " " + getString(R.string.at) + " " + currentPost.getTime() + "\n" + currentPost.getHead();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + label + ")"));
                try{
                    startActivity(intent);
                }
                catch (Exception ignored){
                    Toast.makeText(getActivity().getApplicationContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT ).show();
                    // Handle the case where Google Maps is not installed
                }
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
}