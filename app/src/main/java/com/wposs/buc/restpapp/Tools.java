package com.wposs.buc.restpapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class Tools {

    public static void startView(Context activity , Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(activity , cls);
        activity.startActivity(intent);
        //activity.finish();
    }
}
