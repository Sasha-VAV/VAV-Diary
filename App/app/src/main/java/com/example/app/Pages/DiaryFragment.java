package com.example.app.Pages;

import static com.example.app.Pages.MainActivity.user;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.days.Day;
import com.example.app.days.DayFragment;
import com.example.app.days.DayManager;
import com.example.app.posts.Post;
import com.example.app.posts.PostManager;
import com.example.app.searchEngineAndStats.SearchInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PostManager postManager;
    private DayManager dayManager;
    private ArrayList<Day> days;
    private ArrayList<Post> posts;
    public static Post currentPost;
    public static boolean postIsClicked;
    public static View view;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private SearchInfo searchInfo;
    private DayFragment dayFragment;
    private TextView tx;

    public DiaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiaryFragment newInstance(String param1, String param2) {
        DiaryFragment fragment = new DiaryFragment();
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
        view = inflater.inflate(R.layout.fragment_diary, container, false);

        postManager = new PostManager(getActivity().getApplication(), user);
        tx = view.findViewById(R.id.tx);
        dayManager = new DayManager(postManager.getUserPosts(-1));
        days = dayManager.getDays();

        posts = postManager.getUserPosts(-1);
        searchInfo = new SearchInfo(posts);
        dayFragment = new DayFragment(days, getActivity().getApplication());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!dayFragment.isAdded())
            ft.add(R.id.fr1, dayFragment);
        ft.commit();


        /*Runnable onPostListener = new Runnable() {
            @Override
            public void run() {
                while (true){
                    while (!postIsClicked){

                    }
                    postIsClicked = false;
                    if (currentPost!=null){
                        navController = Navigation.findNavController(requireView());
                        navController.navigate(R.id.action_diaryFragment_to_postFragment2);
                    }
                }
            }
        };
        executorService.execute(onPostListener);*/

        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try{
                    dayManager = new DayManager(searchInfo.findWithText(query.toString()));
                    days = dayManager.getDays();
                    dayFragment = new DayFragment(days, getActivity().getApplication());
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fr1, dayFragment);
                    ft.commit();
                    return true;
                }
                catch (Exception e){
                    tx.setText(e.toString());
                    return false;
                }

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        /*
        EditText ed = view.findViewById(R.id.ed);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Post post = postManager.findPostById(Integer.parseInt(ed.getText().toString()));
                    tx.setText(post.toString());
                }
                catch (Exception ignored){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    dayManager = new DayManager(searchInfo.findWithText(s.toString()));
                    days = dayManager.getDays();
                    dayFragment = new DayFragment(days, getActivity().getApplication());
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fr1, dayFragment);
                    ft.commit();
                }
                catch (Exception e){
                    tx.setText(e.toString());
                }
            }
        });*/
        return view;
    }
}