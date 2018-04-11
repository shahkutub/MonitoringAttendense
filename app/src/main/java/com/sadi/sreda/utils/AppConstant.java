package com.sadi.sreda.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sadi.sreda.alarm.AlarmReceiver;
import com.sadi.sreda.model.LoginData;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sadi on 2/14/2018.
 */

public class AppConstant {

    public static String clockInOu="";
    public static String lat="";
    public static String lng="";
    public static String alarmOnOff="alarmOnOff";
    public static boolean isGallery=false;
    public static boolean isHq=false;
    public static int CAMERA_RUNTIME_PERMISSION=2,WRITEEXTERNAL_PERMISSION_RUNTIME=3,LOCATION_PERMISSION=4;
    public static String quickAttandance = "quickAttandance";


    public static void alarmFirst(Context con) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(con, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 1,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    public static void alarmSecond(Context con) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 37);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(con, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 2,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void saveUserdat(Context con, LoginData loginData) {
        SharedPreferences mPrefs = con.getSharedPreferences("loginData",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        prefsEditor.putString("loginData", json);
        prefsEditor.commit();

    }

    public static LoginData getUserdata(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("loginData",MODE_PRIVATE);
        LoginData loginData = new LoginData();
        Gson gson = new Gson();
        String json = mPrefs.getString("loginData", "");
        loginData = gson.fromJson(json, LoginData.class);
        return loginData;
    }
}
