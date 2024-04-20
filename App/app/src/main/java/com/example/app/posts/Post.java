package com.example.app.posts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Post {
    private int id;
    private int author;

    private String allFields;

    private String head;
    private String tag;
    private String text;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private ZonedDateTime zonedDateTime;
    private LocalDateTime localDateTime;
    private String dt;
    private ArrayList<String> fullPost;
    public Post(int id, int author, String allFields){
        this.id = id;
        this.author = author;
        this.allFields = allFields;



        this.dt = allFields.substring(allFields.indexOf("/.dt/") + 1, allFields.indexOf("/.endDt/"));
        this.head = allFields.substring(allFields.indexOf("/.head/") + 1, allFields.indexOf("/.endHead/"));
        this.tag = allFields.substring(allFields.indexOf("/.tag/") + 1, allFields.indexOf("/.endTag/"));
        this.text = allFields.substring(allFields.indexOf("/.text/") + 1, allFields.indexOf("/.endText/"));

        fullPost = new ArrayList<>();
        fullPost.add(dt.substring(0, dt.indexOf(" ")));
        fullPost.add(dt.substring(dt.indexOf(" ") + 1));
        fullPost.add(head);
        fullPost.add(tag);
        fullPost.add(text);
    }

    public Post(int id, int author, String head, String tag, String text) {
        this.id = id;
        this.author = author;
        this.head = head;
        this.tag = tag;
        this.text = text;
        zonedDateTime = ZonedDateTime.now();
        localDateTime = LocalDateTime.now();
        dt = dtf.format(localDateTime);
        fullPost = new ArrayList<>();
        fullPost.add(dt.substring(0, dt.indexOf(" ")));
        fullPost.add(dt.substring(dt.indexOf(" ") + 1));
        fullPost.add(head);
        fullPost.add(tag);
        fullPost.add(text);

        allFields = "";
        allFields += fullPost.get(0) + " " + fullPost.get(1);
        allFields += "/.head/" + fullPost.get(2)+"/.endHead/";
        allFields += "/.tag/" + fullPost.get(3)+ "/.endTag/";
        allFields += "/.text/" + fullPost.get(4)+ "/.endText/";
    }

    public void fillAllFields(){
        String allFields = this.allFields;

        this.dt = allFields.substring(allFields.indexOf("/.dt/") + 1, allFields.indexOf("/.endDt/"));
        this.head = allFields.substring(allFields.indexOf("/.head/") + 1, allFields.indexOf("/.endHead/"));
        this.tag = allFields.substring(allFields.indexOf("/.tag/") + 1, allFields.indexOf("/.endTag/"));
        this.text = allFields.substring(allFields.indexOf("/.text/") + 1, allFields.indexOf("/.endText/"));

        fullPost = new ArrayList<>();
        fullPost.add(dt.substring(0, dt.indexOf(" ")));
        fullPost.add(dt.substring(dt.indexOf(" ") + 1));
        fullPost.add(head);
        fullPost.add(tag);
        fullPost.add(text);
    }

    public ArrayList<String> getFullPost(int author) {
        if (this.author == author)
            return fullPost;
        return null;
    }

    public PostForDB toPostForDB(){
        return new PostForDB(id, author, allFields);
    }
}
