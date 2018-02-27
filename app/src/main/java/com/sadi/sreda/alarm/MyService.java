package com.sadi.sreda.alarm;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.sadi.sreda.R;

import java.util.Calendar;

/**
 * Created by NanoSoft on 2/27/2018.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    private boolean isRunning  = false;
    private Looper looper;
    private MyServiceHandler myServiceHandler;
    private static Notification notification;
    @Override
    public void onCreate() {
        notification=new Notification();
        HandlerThread handlerthread = new HandlerThread("MyThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerthread.start();
        looper = handlerthread.getLooper();
        myServiceHandler = new MyServiceHandler(looper);
        isRunning = true;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = myServiceHandler.obtainMessage();
        msg.arg1 = startId;
        myServiceHandler.sendMessage(msg);
        Toast.makeText(this, "MyService Started.", Toast.LENGTH_SHORT).show();
        //If service is killed while starting, it restarts.
        scheduleNotifications();
        SimpleWakefulReceiver.completeWakefulIntent(intent);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        isRunning = false;
        Toast.makeText(this, "MyService Completed or Stopped.", Toast.LENGTH_SHORT).show();
    }
    private final class MyServiceHandler extends Handler {
        public MyServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Log.i(TAG, "MyService running...");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                    if(!isRunning){
                        break;
                    }
                }
            }
            //stops the service for the start id.
            stopSelfResult(msg.arg1);
        }
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
        firstCalendar.set(firstCalendar.get(Calendar.YEAR), firstCalendar.get(Calendar.MONTH), firstCalendar.get(Calendar.DAY_OF_MONTH), 17, 59);
        remainingMillis = firstCalendar.getTimeInMillis() - currentTimeMillis;

        //Schedule the notification only if the scheduled hour hasn't already passed
//        if (remainingMillis > 0)
//            scheduleFirstNotification(notification, remainingMillis);

        //schedule the second notification
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.set(secondCalendar.get(Calendar.YEAR), secondCalendar.get(Calendar.MONTH), secondCalendar.get(Calendar.DAY_OF_MONTH), 13, 5);
        remainingMillis = secondCalendar.getTimeInMillis() - currentTimeMillis;
        if (remainingMillis > 0)
            scheduleSecondNotification(notification, remainingMillis);


        //schedule the third notification
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.set(thirdCalendar.get(Calendar.YEAR), thirdCalendar.get(Calendar.MONTH), thirdCalendar.get(Calendar.DAY_OF_MONTH), 13, 6);
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
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
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
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
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
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
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
        builder.setDeleteIntent(getDeleteIntent(mContext));//set the behaviour when the notification is cleared
        builder.setContentIntent(mainActivityPendingIntent);
        return builder.build();
    }

    protected PendingIntent getDeleteIntent(Context mContext)
    {
        Intent intent = new Intent(mContext, BuyerPushNotif.ClearedPushNotif.class);
        intent.setAction("notification_cancelled");
        return PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void cancelAllAlarmNotifications() {
        //the first notification
        Intent firstNotificationIntent = new Intent(this, BuyerPushNotif.FirstPushNotif.class);
        firstNotificationIntent.setAction("first_notif_action");
        PendingIntent firstPendingIntent = PendingIntent.getBroadcast(this, 0, firstNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //the second notification
        Intent secondNotificationIntent = new Intent(this, BuyerPushNotif.SecondPushNotif.class);
        secondNotificationIntent.setAction("second_notif_action");
        PendingIntent secondPendingIntent = PendingIntent.getBroadcast(this, 0, secondNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //the third notification
        Intent thirdNotificationIntent = new Intent(this, BuyerPushNotif.FirstPushNotif.class);
        thirdNotificationIntent.setAction("third_notif_action");
        PendingIntent thirdPendingIntent = PendingIntent.getBroadcast(this, 0, thirdNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //cancel all notifications
        alarmManager.cancel(firstPendingIntent);
        alarmManager.cancel(secondPendingIntent);
        alarmManager.cancel(thirdPendingIntent);
    }

}
