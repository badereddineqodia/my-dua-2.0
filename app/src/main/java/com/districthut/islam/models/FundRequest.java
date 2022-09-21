package com.districthut.islam.models;

public class FundRequest {
    private String cause, description, goal, image, link, location;

    public FundRequest() {
    }

    public FundRequest(String cause, String description, String goal, String image, String link, String location) {
        this.cause = cause;
        this.description = description;
        this.goal = goal;
        this.image = image;
        this.link = link;
        this.location = location;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
