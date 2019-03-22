package com.wposs.buc.restpapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Tools {

    public static void startView(AppCompatActivity activity , Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(activity , cls);
        activity.startActivity(intent);
        //activity.finish();
    }
}
