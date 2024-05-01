package com.example.app.days;

import static com.example.app.Pages.MainActivity.application;

import android.widget.Toast;

import com.example.app.posts.Post;

import java.util.ArrayList;
import java.util.Objects;

public class DayManager {
    private ArrayList<Day> days;

    public DayManager(ArrayList<Post> posts) {
        days = new ArrayList<>();
        ArrayList<Post> oneDayPosts = new ArrayList<>();
        for (Post post:
             posts) {
            if (oneDayPosts.size() != 0 && !Objects.equals(post.getDate(), oneDayPosts.get(0).getDate())){
                days.add(new Day(oneDayPosts.get(0).getDate(), oneDayPosts));
                oneDayPosts = new ArrayList<>();
            }
            oneDayPosts.add(post);
        }
        if (oneDayPosts.size() != 0){
            days.add(new Day(oneDayPosts.get(0).getDate(), oneDayPosts));
        }
    }
    public DayManager(int n, ArrayList<Day> days){
        ArrayList<Day> result = new ArrayList<>();
        if (n < 1)
            n = 7;
        if (days.size() - n < 0)
            n = days.size();
        for (int i = 0; i < n; i++){
            Toast.makeText(application, String.valueOf(i), Toast.LENGTH_SHORT).show();
            result.add(days.get(i));
        }
        this.days = result;

    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public String getLastNDays(int n) {
        DayManager dayManager = new DayManager(n, days);
        return dayManager.toString();
    }

    @Override
    public String toString() {
        String s = "";
        for (Day day :
                days) {
            s += day.toString();
        }
        return s;
    }
}
