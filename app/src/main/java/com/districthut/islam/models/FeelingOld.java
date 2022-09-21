package com.districthut.islam.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FeelingOld {
    @PrimaryKey
    private int id;
    private int image;
    private String feeling;

    public FeelingOld() {}

    public FeelingOld(int image, String feeling) {
        this.image = image;
        this.feeling = feeling;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
