package com.example.app.days;

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

    public ArrayList<Day> getDays() {
        return days;
    }

    public String getLastNDays(int n) {

        //TODO this method
        return "hey";
    }
}
