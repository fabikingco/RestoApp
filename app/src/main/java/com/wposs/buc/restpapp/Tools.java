package com.wposs.buc.restpapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static void startView(Activity activity , Class<?> cls, boolean finish){
        Intent intent = new Intent();
        intent.setClass(activity , cls);
        activity.startActivity(intent);
        if(finish)
            activity.finish();
    }

    public static String DateToStr(Date date, String formatString) {
        String str = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);// formatString
            str = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
