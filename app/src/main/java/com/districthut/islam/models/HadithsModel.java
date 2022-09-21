package com.districthut.islam.models;

public class HadithsModel {
    String name;
    String alphabet;
    String bookName;
    int totalHadiths;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getTotalHadiths() {
        return totalHadiths;
    }

    public void setTotalHadiths(int totalHadiths) {
        this.totalHadiths = totalHadiths;
    }

    public HadithsModel(String name, String alphabet, String bookName, int totalHadiths) {
        this.name = name;
        this.alphabet = alphabet;
        this.bookName = bookName;
        this.totalHadiths = totalHadiths;
    }


}
