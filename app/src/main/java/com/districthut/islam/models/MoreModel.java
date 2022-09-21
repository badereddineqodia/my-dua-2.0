package com.districthut.islam.models;

public class MoreModel {
    private int image;
    private String name;
    private String code;

    public MoreModel() {
    }

    public MoreModel(int image, String name, String code) {
        this.image = image;
        this.name = name;
        this.code = code;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
