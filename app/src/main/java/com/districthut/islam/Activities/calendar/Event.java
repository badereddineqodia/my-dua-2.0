package com.districthut.islam.Activities.calendar;

public class Event {
    private String eventName, eventDate, eventGeorgianDate;

    public Event() {    }

    public Event(String eventName, String eventDate, String eventGeorgianDate) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventGeorgianDate = eventGeorgianDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventGeorgianDate() {
        return eventGeorgianDate;
    }

    public void setEventGeorgianDate(String eventGeorgianDate) {
        this.eventGeorgianDate = eventGeorgianDate;
    }
}
