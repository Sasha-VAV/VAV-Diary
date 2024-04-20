package com.example.app.users;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.app.posts.Post;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Entity(tableName = "User_Table")
public class User {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "key")
    private int key;

    @ColumnInfo(name = "User_name")
    private String name;

    public void setKey(int key) {
        this.key = key;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

    @ColumnInfo(name = "User_posts")
    private String postIds;

    public String getPostIds(){return postIds;};
    public int getKey() {
        return key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(int id, int key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.postIds = "";
    }
    public void insertPostId(int id){
        this.postIds += "{"+id +"}";
    }
    /*public ArrayList<Integer> getPostIds(){
        ArrayList<Integer> result = new ArrayList<>();
        String s = postIds;
        while (s.indexOf("}") > 0){
            result.add(Integer.valueOf(s.substring(1, s.indexOf("}"))));
            s = s.substring(s.indexOf("}") + 1);
        }
        return result;*
    }*/


}
