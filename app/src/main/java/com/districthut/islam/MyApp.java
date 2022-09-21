package com.districthut.islam;

import android.app.Application;

import com.districthut.islam.utils.AppManager;





public class MyApp extends Application  {

    private static final String ONESIGNAL_APP_ID = "129044a1-17cd-4cff-8009-7925edfccf14";

    @Override
    public void onCreate() {
        super.onCreate();


        AppManager.init(this);

//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();





    }


}
