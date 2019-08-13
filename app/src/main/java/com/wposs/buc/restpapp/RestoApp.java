package com.wposs.buc.restpapp;

import android.app.Application;

public class RestoApp extends Application {

    private static RestoApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized RestoApp getInstance() {
        return mInstance;
    }
}
