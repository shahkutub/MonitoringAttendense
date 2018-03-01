package com.sadi.sreda.alarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.sadi.sreda.R;

import java.util.Calendar;

/**
 * Created by NanoSoft on 2/28/2018.
 */

public class MyTestService extends IntentService {


    private static Notification notification;
    public MyTestService() {
        super("MyTestService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        notification=new Notification();
        scheduleNotifications();
    }



    private void scheduleNotifications() {
        final int TEN_AM = 10;
        final int TWO_PM = 14;
        final int SIX_PM = 18;
        final int MINUTE = 0;
        long remainingMillis;

        long currentTimeMillis = System.currentTimeMillis();
        Calendar firstCalendar = Calendar.getInstance();

        //set the date and time of the calendar
        firstCalendar.set(firstCalendar.get(Calendar.YEAR), firstCalendar.get(Calendar.MONTH), firstCalendar.get(Calendar.DAY_OF_MONTH), 12, 52);
        remainingMillis = firstCalendar.getTimeInMillis() - currentTimeMillis;

       // Schedule the notification only if the scheduled hour hasn't already passed
        if (remainingMillis > 0)
            scheduleFirstNotification(notification, remainingMillis);

        //schedule the second notification
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.set(secondCalendar.get(Calendar.YEAR), secondCalendar.get(Calendar.MONTH), secondCalendar.get(Calendar.DAY_OF_MONTH), 12, 53);
        remainingMillis = secondCalendar.getTimeInMillis() - currentTimeMillis;
        if (remainingMillis > 0)
            scheduleSecondNotification(notification, remainingMillis);


        //schedule the third notification
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.set(thirdCalendar.get(Calendar.YEAR), thirdCalendar.get(Calendar.MONTH), thirdCalendar.get(Calendar.DAY_OF_MONTH), 12, 54);
        remainingMillis = thirdCalendar.getTimeInMillis() - currentTimeMillis;
        if (remainingMillis > 0)
            scheduleThirdNotification(notification, remainingMillis);
    }

    private void scheduleFirstNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, BuyerPushNotif.FirstPushNotif.class);
        //we need a unique identifier for each notification
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        //we need to set an action for each notification, and then define a class in the Manifest that uses this action
        //as a filter
        notificationIntent.setAction("first_notif_action");
        notificationIntent.putExtra(BuyerPushNotif.NOTIFICATION_ID, uniqueInt);
        notificationIntent.putExtra(BuyerPushNotif.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        alarmManager.set(AlarmManager.RTC, futureInMillis, pendingIntent);

//        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
//        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, futureInMillis,
//                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
    }

    private void scheduleSecondNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, BuyerPushNotif.SecondPushNotif.class);
        notificationIntent.setAction("second_notif_action");
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        notificationIntent.putExtra(BuyerPushNotif.NOTIFICATION_ID, uniqueInt);
        notificationIntent.putExtra(BuyerPushNotif.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        alarmManager.set(AlarmManager.RTC, futureInMillis, pendingIntent);

//        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
//        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, futureInMillis,
//                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
    }

    private void scheduleThirdNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, BuyerPushNotif.ThirdPushNotif.class);
        notificationIntent.setAction("third_notif_action");
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        notificationIntent.putExtra(BuyerPushNotif.NOTIFICATION_ID, uniqueInt);
        notificationIntent.putExtra(BuyerPushNotif.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        alarmManager.set(AlarmManager.RTC, futureInMillis, pendingIntent);

//        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
//        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, futureInMillis,
//                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
    }

    private Notification getNotification(String content,Context mContext) {
        NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);
        //open the main activity when the intent is clicked
        Intent resultIntent = new Intent(this, AlarmMainActivity.class);
        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("Notification title");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //use a custom notification sound
        builder.setSound(Uri.parse("android.resource://"
                + mContext.getPackageName() + "/"
                + R.raw.alarm_sound));
        builder.setAutoCancel(true);//clear the notification after it is clicked
        //builder.setDeleteIntent(getDeleteIntent(mContext));//set the behaviour when the notification is cleared
        builder.setContentIntent(mainActivityPendingIntent);
        return builder.build();
    }

    protected PendingIntent getDeleteIntent(Context mContext)
    {
        Intent intent = new Intent(mContext, BuyerPushNotif.ClearedPushNotif.class);
        intent.setAction("notification_cancelled");
        return PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
