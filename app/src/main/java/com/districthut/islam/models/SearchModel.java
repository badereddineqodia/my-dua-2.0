package com.districthut.islam.models;

public class SearchModel {
    private int id;
    private String text, href, title, answer;

    public SearchModel() {
    }

    public SearchModel(int id, String text, String href, String title, String answer) {
        this.id = id;
        this.text = text;
        this.href = href;
        this.title = title;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
