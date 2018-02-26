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
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sadi.sreda.MainActivity;
import com.sadi.sreda.R;

import java.util.Random;

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

    public static class ThirdPushNotif extends BroadcastReceiver {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
           // Notification notification = intent.getParcelableExtra(NOTIFICATION);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
             Notification notification = new Notification.Builder(context)
        .setContentTitle("New mail from ")
        .setContentText("")
        .setSmallIcon(R.mipmap.ic_launcher)
        .build();

            notificationManager.notify(id, notification);
        }
    }





    public static class ClearedPushNotif extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            final String NOTIF_CANCELLED = "notification_cancelled";
            //Log.i(LOG_TAG,"Action is: " + action);
            if(action.equals(NOTIF_CANCELLED))
            {
                //do whatever you want when the notification is cleared by the user
            }
        }
    }

}
