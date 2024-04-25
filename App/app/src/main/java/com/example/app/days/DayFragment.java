package com.example.app.days;

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
import com.example.app.posts.PostAdapter;

import java.util.ArrayList;

public class DayFragment extends Fragment {
    private ArrayList<Day> days;
    private Application application;

    public DayFragment(ArrayList<Day> days, Application application) {
        this.days = days;
        this.application = application;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.day_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.allRV);
        DayAdapter dayAdapter = new DayAdapter(days, application);

        recyclerView.setAdapter(dayAdapter);

        return view;
    }
}
