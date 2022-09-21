package com.districthut.islam;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    public static final String PREF_IS_ALLOW_LOCATION="pre_is_allow_location";
    public static final String PREF_LOCATION_LONGITUDE="pre_location_longitude";
    public static final String PREF_LOCATION_LATITUDE="pre_location_latitude";


    private static final String TAG="";
    private final String PrefName="com.mianasad.myislam.preference.UserPreferences";

    private static UserPreference ourInstance=null;
    private static Context context=null;
    private SharedPreferences pref=null;

    public static UserPreference getInstance(Context pContext){
        try {
            context=pContext.getApplicationContext();
        }
        catch (Exception e) {

        }
        if (ourInstance==null){
            ourInstance=new UserPreference();
        }
        return ourInstance;
    }

    private UserPreference(){
        pref=context.getSharedPreferences(PrefName,Context.MODE_PRIVATE);
    }

    public void saveString(final String pRef,final String value) {
        try {
            SharedPreferences.Editor editor=pref.edit();
            editor.putString(pRef,value);
            editor.apply();
        }
        catch (Exception e) {

        }
    }

    public  String getString(final String pRef,String defaultValue){
        return pref.getString(pRef,defaultValue);
    }

    public void saveBoolean(final String pRef,final Boolean value) {
        try {
            SharedPreferences.Editor editor=pref.edit();
            editor.putBoolean(pRef,value);
            editor.apply();
        }
        catch (Exception e) {

        }
    }

    public  Boolean getBoolean(final String pRef,Boolean defaultValue){
        return pref.getBoolean(pRef,defaultValue);
    }
}
