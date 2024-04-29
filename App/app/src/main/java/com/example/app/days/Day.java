package com.example.app.days;

import com.example.app.posts.Post;

import java.util.ArrayList;

public class Day {
    private String date;
    private ArrayList<Post> posts;

    public Day(String date, ArrayList<Post> posts) {
        this.date = date;
        this.posts = posts;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        String s = "\n---------------------------\n" + date;
        for (Post post :
                posts) {
            s += post.toString();
        }
        return  s;
    }
}
