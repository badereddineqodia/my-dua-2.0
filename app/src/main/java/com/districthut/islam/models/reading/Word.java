package com.districthut.islam.models.reading;


public class Word  {
    private String surah, word, html;


    private String id;

    public Word() {
    }

    public Word(String id, String surah, String word, String html) {
        this.id = id;
        this.surah = surah;
        this.word = word;
        this.html = html;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurah() {
        return surah;
    }

    public void setSurah(String surah) {
        this.surah = surah;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
