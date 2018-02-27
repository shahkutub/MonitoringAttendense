package com.sadi.sreda.alarm;

/**
 * Created by NanoSoft on 2/26/2018.
 */

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.sadi.sreda.R;
import java.util.Calendar;


public class BuyerPushNotif {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public static class FirstPushNotif extends BroadcastReceiver {
        @SuppressLint({"NewApi", "LocalSuppress"})
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            intent.getParcelableExtra(NOTIFICATION);
            Notification notification;
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
             notification = new Notification.Builder(context)
                    .setContentTitle("New mail from ")
                    .setContentText("")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            notificationManager.notify(id, notification);
        }
    }

    public static class SecondPushNotif extends BroadcastReceiver {
        @SuppressLint({"NewApi", "LocalSuppress"})
        @Override
        public void onReceive(Context context, Intent intent) {
//            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//            intent.getParcelableExtra(NOTIFICATION);
//            Notification notification;
//            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
//            notification = new Notification.Builder(context)
//                    .setContentTitle("New mail from ")
//                    .setContentText("")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .build();
//
//            notificationManager.notify(id, notification);

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);

            notificationManager.notify(id, getNotification("",context));
        }
    }

    public static class ThirdPushNotif extends BroadcastReceiver {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            notificationManager.notify(id, getNotification("",context));
        }
    }

    private static Notification getNotification(String content, Context context) {
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(context);
        //open the main activity when the intent is clicked
        Intent resultIntent = new Intent(context, AlarmReceiver.class);
        resultIntent.getParcelableExtra(NOTIFICATION);

        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("Notification title");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //use a custom notification sound
        builder.setSound(Uri.parse("android.resource://"
                + context.getPackageName() + "/"
                + R.raw.alarm_sound));
        builder.setAutoCancel(true);//clear the notification after it is clicked
        builder.setDeleteIntent(getDeleteIntent(context));//set the behaviour when the notification is cleared
        builder.setContentIntent(mainActivityPendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, mainActivityPendingIntent);
        return builder.build();
    }

    protected static PendingIntent getDeleteIntent(Context context)
    {
        Intent intent = new Intent(context, BuyerPushNotif.ClearedPushNotif.class);
        intent.setAction("notification_cancelled");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    public static class ClearedPushNotif extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            final String NOTIF_CANCELLED = "notification_cancelled";
            //Log.i(LOG_TAG,"Action is: " + action);
            if(action.equals(NOTIF_CANCELLED))
            {
                new AlarmSoundService().onDestroy();
            }
        }
    }

}
