package com.github.recycleviewhttp;

import android.graphics.Bitmap;

public class User {

    String id;
    String name;
    String stats;
    String picture;

    public User(String id, String name, String stats, String picture){
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }
}
