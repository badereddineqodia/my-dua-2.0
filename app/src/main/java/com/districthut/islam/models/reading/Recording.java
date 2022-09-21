package com.districthut.islam.models.reading;


public class Recording  {


    private String id;

    private String lessonId, userId, user_type, recording_file, createdAt;

    private int status;

    public Recording() {
    }

    public Recording(String id, String lessonId, String userId, String user_type, String recording_file, int status, String createdAt) {
        this.id = id;
        this.lessonId = lessonId;
        this.userId = userId;
        this.user_type = user_type;
        this.recording_file = recording_file;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getRecording_file() {
        return recording_file;
    }

    public void setRecording_file(String recording_file) {
        this.recording_file = recording_file;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
