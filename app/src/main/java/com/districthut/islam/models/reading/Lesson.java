package com.districthut.islam.models.reading;


public class Lesson{


    private String name;

    private String time;


    private String code;

    private String topic;
    private float rating;
    private int notifications;

    public Lesson() {
    }

    public Lesson(String name, String time, String code, String topic, float rating, int notifications) {
        this.name = name;
        this.time = time;
        this.code = code;
        this.topic = topic;
        this.rating = rating;
        this.notifications = notifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
