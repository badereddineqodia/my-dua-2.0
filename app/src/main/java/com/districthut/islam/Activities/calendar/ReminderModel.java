package com.districthut.islam.Activities.calendar;

import androidx.room.PrimaryKey;


public class ReminderModel{

    @PrimaryKey
    private long id;

    private int year, dayOfMonth, monthOfYear, hourOfDay, minutes;
    private String message;

    public ReminderModel() {
    }

    public ReminderModel(long id, int year, int dayOfMonth, int monthOfYear, int hourOfDay, int minutes, String message) {
        this.id = id;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.hourOfDay = hourOfDay;
        this.minutes = minutes;
        this.message = message;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
