package com.example.app.posts;

public class Post {
    private int id = -1;
    private String head;
    private String tag;
    private String text;

    public Post(String head, String tag, String text) {
        this.head = head;
        this.tag = tag;
        this.text = text;
    }
    public int sendPost(int key){
        return id;
    }
}
