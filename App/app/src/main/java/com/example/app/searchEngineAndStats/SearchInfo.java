package com.example.app.searchEngineAndStats;

import static com.example.app.Pages.MainActivity.application;

import com.example.app.R;
import com.example.app.posts.Post;

import java.util.ArrayList;
import java.util.Objects;

public class SearchInfo {
    private ArrayList<Post> posts;
    private ArrayList<String> stats;

    public ArrayList<String> getStats() {
        return stats;
    }

    public SearchInfo(String ids) {
        stats = new ArrayList<>();
        int k = 0;
        while (ids.indexOf("{")!=-1){
            k++;
            ids = ids.substring(ids.indexOf("{") + 1);
        }
        stats.add(application.getString(R.string.number_of_events) + ": " + k);
    }

    public SearchInfo(ArrayList<Post> posts) {
        this.posts = posts;
    }
    public ArrayList<Post> findWithText(String text){
        if (Objects.equals(text, ""))
            return posts;
        text = text.toLowerCase();
        ArrayList<Post> result = new ArrayList<>();
        for (Post post:
             posts) {
            if (post.hasKeywords(text))
                result.add(post);
        }
        return result;
    }
    //TODO nav butt + icons + server. AND MAYBE edit post / del post  + camera
}
