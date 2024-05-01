package com.example.app.Diary;

import static com.example.app.Pages.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.Pages.MainActivity;
import com.example.app.PostActivity;
import com.example.app.Profile.ProfileActivity;
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

public class DiaryActivity extends AppCompatActivity {
    private PostManager postManager;
    private DayManager dayManager;
    private ArrayList<Day> days;
    private ArrayList<Post> posts;
    public static Post currentPost;
    public static boolean postIsClicked;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private SearchInfo searchInfo;
    private DayFragment dayFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_activity);
        ImageButton toHomeButton = findViewById(R.id.toHome)
                , toDiaryButton = findViewById(R.id.toDiary)
                , toProfileButton = findViewById(R.id.toProfile);

        postManager = new PostManager(getApplication(), user);
        TextView tx = findViewById(R.id.tx);
        tx.setEnabled(false);
        EditText ed = findViewById(R.id.ed);
        dayManager = new DayManager(postManager.getUserPosts(-1));
        days = dayManager.getDays();

        posts = postManager.getUserPosts(-1);
        searchInfo = new SearchInfo(posts);
        dayFragment = new DayFragment(days, getApplication());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!dayFragment.isAdded())
            ft.add(R.id.fr1, dayFragment);
        ft.commit();
        Runnable onPostListener = new Runnable() {
            @Override
            public void run() {
                while (true){
                    while (!postIsClicked){

                    }
                    postIsClicked = false;
                    Intent intent = new Intent(DiaryActivity.this, PostActivity.class);
                    startActivity(intent);
                }
            }
        };
        executorService.execute(onPostListener);


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
                    dayFragment = new DayFragment(days, getApplication());
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fr1, dayFragment);
                    ft.commit();
                }
                catch (Exception e){
                    tx.setText(e.toString());
                }
            }
        });








        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        toDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
        toProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
