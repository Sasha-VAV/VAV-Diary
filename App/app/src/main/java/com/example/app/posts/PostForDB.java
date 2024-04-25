package com.example.app.posts;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Entity(tableName = "Post_Table")

public class PostForDB {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "Post_author")
    private int author;

    @ColumnInfo (name = "Post_all_fields")
    private String allFields;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllFields() {
        return allFields;
    }

    public void setAllFields(String allFields) {
        this.allFields = allFields;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public PostForDB(int author, String allFields) {
        this.author = author;
        this.allFields = allFields;
    }




}
