package com.districthut.islam.models;

/**
 * Created by Mian Brothers on 12/3/2017.
 */

public class NamesModel {
    private String name;
    private String transliteration;
    private String english_meaning;
    private String urdu_meaning;

    private boolean expanded;

    public NamesModel() {
    }

    public NamesModel(String name, String transliteration, String english_meaning, String urdu_meaning) {
        this.name = name;
        this.transliteration = transliteration;
        this.english_meaning = english_meaning;
        this.urdu_meaning = urdu_meaning;
        this.expanded=false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setTransliteration(String transliteration) {
        this.transliteration = transliteration;
    }

    public void setEnglish_meaning(String english_meaning){
        this.english_meaning = english_meaning;
    }

    public void setUrdu_meaning(String urdu_meaning){
        this.urdu_meaning = urdu_meaning;
    }

    public String getName() { return this.name; }
    public String getTransliteration() { return this.transliteration; }
    public String getEnglish_meaning() { return this.english_meaning; }
    public String getUrdu_meaning() { return this.urdu_meaning; }
}
