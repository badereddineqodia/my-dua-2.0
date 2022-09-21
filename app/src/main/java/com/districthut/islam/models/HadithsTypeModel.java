package com.districthut.islam.models;

public class HadithsTypeModel {

    int rowCounter;
    int chapterNo;
    String type;
    String totalChapter;

    public int getRowCounter() {
        return rowCounter;
    }

    public void setRowCounter(int rowCounter) {
        this.rowCounter = rowCounter;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalChapter() {
        return totalChapter;
    }

    public void setTotalChapter(String totalChapter) {
        this.totalChapter = totalChapter;
    }

    public HadithsTypeModel(int rowCounter, int chapterNo, String type, String totalChapter) {
        this.rowCounter = rowCounter;
        this.chapterNo = chapterNo;
        this.type = type;
        this.totalChapter = totalChapter;
    }



}
