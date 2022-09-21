package com.districthut.islam.models;

public class Feeling {
    private int image;
    private String feeling;

    public Feeling() {}

    public Feeling(int image, String feeling) {
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
}
