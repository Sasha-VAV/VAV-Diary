package com.example.app.days;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Pages.DiaryFragment;
import com.example.app.R;
import com.example.app.posts.PostAdapter;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    private ArrayList<Day> days = new ArrayList<>();
    private Application application;

    public DayAdapter(ArrayList<Day> days, Application application) {
        this.days = days;
        this.application = application;
    }

    @NonNull
    @Override
    public DayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_day, parent, false);

        return new DayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayHolder holder, int position) {
        Day day = days.get(position);
        holder.textDate.setText(day.getDate());
        holder.recyclerView.setAdapter(new PostAdapter(day.getPosts(), application));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class DayHolder extends RecyclerView.ViewHolder{
        TextView textDate;
        RecyclerView recyclerView;
        public DayHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            recyclerView = itemView.findViewById(R.id.posts);
        }
    }
}
