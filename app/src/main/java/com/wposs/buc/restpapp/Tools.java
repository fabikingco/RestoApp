package com.wposs.buc.restpapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class Tools {

    public static void startView(Activity activity , Class<?> cls, boolean finish){
        Intent intent = new Intent();
        intent.setClass(activity , cls);
        activity.startActivity(intent);
        if(finish)
            activity.finish();
    }
}
