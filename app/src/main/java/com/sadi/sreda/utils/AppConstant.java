package com.sadi.sreda.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;

import com.google.gson.Gson;
import com.sadi.sreda.alarm.AlarmReceiver;
import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoginData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sadi on 2/14/2018.
 */

public class AppConstant {

    public static String clockInOu="";
    public static String lat="";
    public static String lng="";
    public static String localpic="localpic";
    //public static String officname="officname";
    public static String locationName="";

    public static String alarmInOnOff="alarmInOnOff";
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
    public static String checkInOrOut = "checkInOrOut";

    public static List<LocationInfo> locationInfoList = new ArrayList<>();
    public static String path ="path";
    public static String photourl ="http://css-bd.com/attendance-system/uploads/users/";
    public static String bitmap = "bitmap";

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

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

    public static void alarmClockInNext(Context con) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

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

    public static void alarmClockOutNext(Context con) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
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

    private void getImageReto(){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://wwwns.akamai.com/media_resources/globe_emea.png")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }

        });

    }


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
