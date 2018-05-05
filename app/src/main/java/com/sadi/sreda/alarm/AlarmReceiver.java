package com.sadi.sreda.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.sadi.sreda.MainActivity;
import com.sadi.sreda.R;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

import java.util.Calendar;

/**
 * Created by Sadi on 3/2/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int mint = calendar.get(Calendar.MINUTE);

        if((!TextUtils.isEmpty(PersistData.getStringData(context,AppConstant.alarmClockInHour)))&&
                (!TextUtils.isEmpty(PersistData.getStringData(context,AppConstant.alarmClockInMin))) ){

            if(hour==Integer.parseInt(PersistData.getStringData(context,AppConstant.alarmClockInHour))&& mint==Integer.parseInt(PersistData.getStringData(context,AppConstant.alarmClockInMin))){

                notificationOne(context,intent,"Alarm Clock In", "Events to Clock In");
            }
        }


        if((!TextUtils.isEmpty(PersistData.getStringData(context,AppConstant.alarmClockOutHour)))&&
                (!TextUtils.isEmpty(PersistData.getStringData(context,AppConstant.alarmClockOutMin))) ){

            if(hour==Integer.parseInt(PersistData.getStringData(context,AppConstant.alarmClockOutHour))&& mint==Integer.parseInt(PersistData.getStringData(context,AppConstant.alarmClockOutMin))){
                notificationTow(context,intent,"Alarm Clock Out", "Events to Clock Out");

            }
        }




    }


    private void notificationOne(Context context, Intent intent,String title, String content) {

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/raw/alarm_sound");

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content).setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(1, mNotifyBuilder.build());

        AppConstant.alarmClockInNext(context);
    }

    private void notificationTow(Context context, Intent intent, String title, String content) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Uri alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/raw/alarm_sound");

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content).setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(2, mNotifyBuilder.build());
        AppConstant.alarmClockOutNext(context);
    }
}