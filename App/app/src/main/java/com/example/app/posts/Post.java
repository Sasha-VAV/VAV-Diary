package com.example.app.posts;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.app.MainActivity;
import com.example.app.PostSettingsActivity;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.crypto.spec.GCMParameterSpec;


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
    private String keywords = "";
    private String photoURL = "Is not working rn, it was a bad idea";
    private String l = "";
    private Location location;
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

    public String getL() {
        if (l.indexOf("|") > 0){
            //TODO FIX FROM HERE
            String s = "Latitude: " + l.substring(0, l.indexOf("|")) + "\nLongitude: " + l.substring(l.indexOf("|") + 1);
            return s;
        }
        else
            return "No location data";
    }

    public void setAllFields(){
        if (MainActivity.isSaveLocation){
            LocationManager locationManager = (LocationManager) MainActivity.application.getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            l = location.getLatitude() + "|" + location.getLongitude();
        }

        dt = dtf.format(localDateTime);
        fullPost = new ArrayList<>();
        fullPost.add(dt.substring(0, dt.indexOf(" ")));
        fullPost.add(dt.substring(dt.indexOf(" ") + 1));
        fullPost.add(head);
        fullPost.add(tag);
        fullPost.add(text);
        fullPost.add(photoURL);
        fullPost.add(l);

        allFields = "";
        allFields += "/.dt/" + fullPost.get(0) + " " + fullPost.get(1) + "/.endDt/";
        allFields += "/.head/" + fullPost.get(2)+"/.endHead/";
        allFields += "/.tag/" + fullPost.get(3)+ "/.endTag/";
        allFields += "/.text/" + fullPost.get(4)+ "/.endText/";
        allFields += "/.photoURL/" + fullPost.get(5)+ "/.endPhotoURL/";
        allFields += "/.location/" + fullPost.get(6)+ "/.endLocation/";
        createKeywords();
    }
    public void fillAllFields(){
        String allFields = this.allFields;

        this.dt = allFields.substring(allFields.indexOf("/.dt/") + 5, allFields.indexOf("/.endDt/"));
        this.head = allFields.substring(allFields.indexOf("/.head/") + 7, allFields.indexOf("/.endHead/"));
        this.tag = allFields.substring(allFields.indexOf("/.tag/") + 6, allFields.indexOf("/.endTag/"));
        this.text = allFields.substring(allFields.indexOf("/.text/") + 7, allFields.indexOf("/.endText/"));
        if (allFields.indexOf("/.photoURL") == -1 || allFields.indexOf("/.endPhotoURL") == -1)
            this.photoURL = "";
        else
            this.photoURL = allFields.substring(allFields.indexOf("/.photoURL/") + 7, allFields.indexOf("/.endPhotoURL/"));
        if (allFields.indexOf("/.location") == -1 || allFields.indexOf("/.endLocation") == -1)
            this.l = "";
        else
            this.l = allFields.substring(allFields.indexOf("/.location/") + 11, allFields.indexOf("/.endLocation/"));

        fullPost = new ArrayList<>();
        fullPost.add(dt.substring(0, dt.indexOf(" ")));
        fullPost.add(dt.substring(dt.indexOf(" ") + 1));
        fullPost.add(head);
        fullPost.add(tag);
        fullPost.add(text);
        fullPost.add(photoURL);
        fullPost.add(l);

        createKeywords();
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
    public void createKeywords(){
        keywords = head + tag + text + dt;
        keywords = keywords.toLowerCase();
    }

    public String getKeywords() {
        return keywords;
    }
    public boolean hasKeywords(String text){
        return keywords.contains(text);
    }
}
