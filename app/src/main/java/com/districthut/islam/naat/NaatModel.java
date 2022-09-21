package com.districthut.islam.naat;

/**
 * Created by Mian Brothers on 11/12/2017.
 */

public class NaatModel {
    private String title;
    private String url;
    private String naatKhawa;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setNaatKhawa(String naatKhawa){
        this.naatKhawa = naatKhawa;
    }

    public String getNaatKhawa(){
        return this.naatKhawa;
    }
}
