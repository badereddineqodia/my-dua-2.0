package com.districthut.islam.models;

import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by Mian Brothers on 6/10/2017.
 */

public class SampleSearchModel implements Searchable {
    private String mTitle;

    public SampleSearchModel(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public SampleSearchModel setTitle(String title) {
        mTitle = title;
        return this;
    }
}