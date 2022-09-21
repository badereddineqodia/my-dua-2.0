package com.districthut.islam.models;

public class GenericDataModel {
    private String title, detail, image, created_at;

    public GenericDataModel(String title, String detail, String image, String created_at) {
        this.title = title;
        this.detail = detail;
        this.image = image;
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
