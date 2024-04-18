package com.example.app.users;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Table")
public class User {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "key")
    private int key;

    @ColumnInfo(name = "User_name")
    private String name;

    //TODO post / post's id array


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
    }
}
