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

        setAllFields();
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
        setAllFields();
    }
    public Post(int author, String head, String tag, String text) {
        this.id = -1;
        this.author = author;
        this.head = head;
        this.tag = tag;
        this.text = text;
        zonedDateTime = ZonedDateTime.now();
        localDateTime = LocalDateTime.now();
        setAllFields();
    }
    public void setAllFields(){
        dt = dtf.format(localDateTime);
        fullPost = new ArrayList<>();
        fullPost.add(dt.substring(0, dt.indexOf(" ")));
        fullPost.add(dt.substring(dt.indexOf(" ") + 1));
        fullPost.add(head);
        fullPost.add(tag);
        fullPost.add(text);

        allFields = "";
        allFields += "/.dt/" + fullPost.get(0) + " " + fullPost.get(1) + "/.endDt/";
        allFields += "/.head/" + fullPost.get(2)+"/.endHead/";
        allFields += "/.tag/" + fullPost.get(3)+ "/.endTag/";
        allFields += "/.text/" + fullPost.get(4)+ "/.endText/";
    }
    public void fillAllFields(){
        String allFields = this.allFields;

        this.dt = allFields.substring(allFields.indexOf("/.dt/") + 5, allFields.indexOf("/.endDt/"));
        this.head = allFields.substring(allFields.indexOf("/.head/") + 7, allFields.indexOf("/.endHead/"));
        this.tag = allFields.substring(allFields.indexOf("/.tag/") + 6, allFields.indexOf("/.endTag/"));
        this.text = allFields.substring(allFields.indexOf("/.text/") + 7, allFields.indexOf("/.endText/"));

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
        return new PostForDB(author, allFields);
    }
    public Post(PostForDB postForDB){
        int id = postForDB.getId();
        int author = postForDB.getAuthor();
        String allFields = postForDB.getAllFields();
        this.id = id;
        this.author = author;
        this.allFields = allFields;
        fillAllFields();
    }

    @Override
    public String toString() {
//        return "Post{" +
//                "id=" + id +
//                ", author=" + author +
//                ", allFields='" + allFields + '\'' +
//                ", head='" + head + '\'' +
//                ", tag='" + tag + '\'' +
//                ", text='" + text + '\'' +
//                ", dtf=" + dtf +
//                ", zonedDateTime=" + zonedDateTime +
//                ", localDateTime=" + localDateTime +
//                ", dt='" + dt + '\'' +
//                ", fullPost=" + fullPost +
//                '}';
        return fullPost.toString();
    }

    public String getHead() {
        return head;
    }

    public String getTag() {
        return tag;
    }

    public String getText() {
        return text;
    }

    public String getDt() {
        return dt;
    }
    public String getDate() {
        return dt.substring(0,dt.indexOf(" "));
    }
    public String getTime() {
        return dt.substring((dt.indexOf(" ") + 1));
    }
}
