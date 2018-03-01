package com.sadi.sreda.alarm;

/**
 * Created by NanoSoft on 2/26/2018.
 */
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import java.util.Calendar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.sadi.sreda.R;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

public class AlarmMainActivity extends AppCompatActivity {
    private static Context mContext;
    private static Notification notification;

    Context con;
    private ImageView imgBack;
    private PendingIntent pendingIntent,pendingIntent2;
    Intent alarmIntent;
    Intent alarmIntent2;
    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;

    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;
    private static final int ALARM_REQUEST_CODE2 = 134;
    private ToggleButton toggleAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mContext = getApplicationContext();
        con = this;
        notification=new Notification();
        //scheduleNotifications();
        initUi();
//        Intent intent = new Intent(AlarmMainActivity.this, MyTestService.class);
//        startService(intent);
    }


    private void initUi() {

        imgBack = (ImageView)findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toggleAlarm = (ToggleButton)findViewById(R.id.toggleAlarm);
        if(PersistData.getStringData(con, AppConstant.alarmOnOff).equalsIgnoreCase("ON")){
            toggleAlarm.setChecked(true);
//            Intent intent = new Intent(AlarmMainActivity.this, MyTestService.class);
//            startService(intent);

        }else if(PersistData.getStringData(con,AppConstant.alarmOnOff).equalsIgnoreCase("OFF")){
            toggleAlarm.setChecked(false);
        }
        toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"ON");
                    Intent intent = new Intent(AlarmMainActivity.this, MyTestService.class);
                    startService(intent);
                    //scheduleNotifications();
                }
                else {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"OFF");
                    Intent intent = new Intent(AlarmMainActivity.this, MyTestService.class);
                    stopService(intent);
                    //scheduleNotifications();
                }

            }
        });


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
        secondCalendar.set(secondCalendar.get(Calendar.YEAR), secondCalendar.get(Calendar.MONTH), secondCalendar.get(Calendar.DAY_OF_MONTH), 11, 19);
        remainingMillis = secondCalendar.getTimeInMillis() - currentTimeMillis;
        if (remainingMillis > 0)
            scheduleSecondNotification(notification, remainingMillis);


        //schedule the third notification
        Calendar thirdCalendar = Calendar.getInstance();
        thirdCalendar.set(thirdCalendar.get(Calendar.YEAR), thirdCalendar.get(Calendar.MONTH), thirdCalendar.get(Calendar.DAY_OF_MONTH), 11, 21);
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

        //alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,futureInMillis,);
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

    private Notification getNotification(String content) {
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
        builder.setDeleteIntent(getDeleteIntent());//set the behaviour when the notification is cleared
        builder.setContentIntent(mainActivityPendingIntent);
        return builder.build();
    }

    protected PendingIntent getDeleteIntent()
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