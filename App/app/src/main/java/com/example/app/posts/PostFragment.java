package com.example.app.posts;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;

import java.util.ArrayList;

public class PostFragment extends Fragment {
    private ArrayList<Post> posts;
    private Application application;

    public PostFragment(ArrayList<Post> posts, Application application) {
        this.posts = posts;
        this.application = application;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DataThread dataThread = new DataThread();
        //dataThread.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.post_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.dayRV);
        PostAdapter postAdapter = new PostAdapter(posts, application);

        recyclerView.setAdapter(postAdapter);

        return view;
    }
    class DataThread extends Thread{
        ArrayList<Post> posts = new ArrayList<>();
        @Override
        public void run() {
            super.run();
        }
    }
}
