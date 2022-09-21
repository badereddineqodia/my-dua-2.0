package com.districthut.islam.models;

public class Place {
    private String business_status, name, location;
    private double longitude, latitude;

    private int total_user_ratings;
    private double rating;
    private boolean isOpen;

    public Place() {
    }

    public Place(String business_status, String name, String location, double longitude, double latitude,
                 int total_user_ratings, double rating, boolean isOpen) {
        this.business_status = business_status;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.total_user_ratings = total_user_ratings;
        this.rating = rating;
        this.isOpen = isOpen;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getTotal_user_ratings() {
        return total_user_ratings;
    }

    public void setTotal_user_ratings(int total_user_ratings) {
        this.total_user_ratings = total_user_ratings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
