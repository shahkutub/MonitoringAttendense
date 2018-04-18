package com.sadi.sreda.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sadi.sreda.alarm.AlarmReceiver;
import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoginData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sadi on 2/14/2018.
 */

public class AppConstant {

    public static String clockInOu="";
    public static String lat="";
    public static String lng="";
    public static String officname="officname";
    public static String alarmOnOff="alarmOnOff";
    public static boolean isGallery=false;
    public static boolean isHq=false;
    public static int CAMERA_RUNTIME_PERMISSION=2,WRITEEXTERNAL_PERMISSION_RUNTIME=3,LOCATION_PERMISSION=4;
    public static String quickAttandance = "quickAttandance";
    public static String alarmClockIn = "alarmClockIn";
    public static String alarmClockout = "alarmClockout";

    public static String alarmClockOutHour = "alarmClockOutHour";
    public static String alarmClockOutMin = "alarmClockOutMin";

    public static String alarmClockInHour = "alarmClockInHour";
    public static String alarmClockInMin = "alarmClockInMin";


    public static List<LocationInfo> locationInfoList = new ArrayList<>();


    public static void alarmClockIn(Context con) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockInHour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockInMin)));
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(con, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 1,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


//        Intent myIntent = new Intent(con , AlarmReceiver.class);
//        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getService(con, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 8);
//        calendar.set(Calendar.MINUTE, 45);
//        calendar.set(Calendar.SECOND, 00);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);

    }
    public static void alarmClockOut(Context con) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockOutHour)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(PersistData.getStringData(con,AppConstant.alarmClockOutMin)));
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(con, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(con, 2,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) con.getSystemService(con.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

//        Intent myIntent = new Intent(con , AlarmReceiver.class);
//        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getService(con, 2, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 17);
//        calendar.set(Calendar.MINUTE, 25);
//        calendar.set(Calendar.SECOND, 00);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
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



    public static void saveLocationdat(Context con, List<LocationInfo> locationInfos) {
        SharedPreferences mPrefs = con.getSharedPreferences("location",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(locationInfos);
        prefsEditor.putString("location", json);
        prefsEditor.commit();

    }


    public static List<LocationInfo> getLocationList(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("location",MODE_PRIVATE);
        List<LocationInfo> locationInfos = new ArrayList<>();
        Gson gson = new Gson();
        String json = mPrefs.getString("location", "");
        locationInfos = (List<LocationInfo>) gson.fromJson(json, LocationInfo.class);
        return locationInfos;
    }

}
