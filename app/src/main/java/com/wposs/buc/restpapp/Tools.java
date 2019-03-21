package com.wposs.buc.restpapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Tools {

    public static void startView(AppCompatActivity activity , Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(activity , cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
