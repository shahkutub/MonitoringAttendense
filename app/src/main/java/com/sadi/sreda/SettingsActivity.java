package com.sadi.sreda;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sadi.sreda.alarm.AlarmNotificationService;
import com.sadi.sreda.alarm.AlarmReceiver;
import com.sadi.sreda.alarm.AlarmSoundService;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

import java.util.Calendar;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    Context con;
    private ImageView imgBack;
    //Pending intent instance
    private PendingIntent pendingIntent,pendingIntent2;
    Intent alarmIntent;
    Intent alarmIntent2;
    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;

    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;
    private static final int ALARM_REQUEST_CODE2 = 134;
    private ToggleButton toggleAlarm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
        con=this;
        initUi();
    }

    private void initUi() {


        //tuturial
        //https://gist.github.com/Narcis11/3ac00f45b7c9ee68e0ce

        /* Retrieve a PendingIntent that will perform a broadcast */
        alarmIntent = new Intent(con, AlarmReceiver.class);
        //alarmIntent2 = new Intent(con, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(con, ALARM_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        //pendingIntent2 = PendingIntent.getBroadcast(con, ALARM_REQUEST_CODE2, alarmIntent2, PendingIntent.FLAG_ONE_SHOT);


        imgBack = (ImageView)findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toggleAlarm = (ToggleButton)findViewById(R.id.toggleAlarm);
        if(PersistData.getStringData(con,AppConstant.alarmOnOff).equalsIgnoreCase("ON")){
            toggleAlarm.setChecked(true);
            startAlarm();
            //startAlarm2();
        }else if(PersistData.getStringData(con,AppConstant.alarmOnOff).equalsIgnoreCase("OFF")){
            toggleAlarm.setChecked(false);
        }
        toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"ON");
                    startAlarm();
                    //startAlarm2();
                }
                else
                {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"OFF");
                    stopAlarmManager();
                }

            }
        });


    }

    private void startAlarm(){
        //Intent myIntent = new Intent(MainActivity.this , AlarmNotificationService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
        Toast.makeText(this, "Alarm Set for " + "11" + " seconds.", Toast.LENGTH_SHORT).show();

    }


    private void startAlarm2(){
        //Intent myIntent = new Intent(MainActivity.this , AlarmNotificationService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 18);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent2);
        Toast.makeText(this, "Alarm Set for " + "11" + " seconds.", Toast.LENGTH_SHORT).show();

    }
    //Stop/Cancel alarm manager
    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent
       // manager.cancel(pendingIntent2);//cancel the alarm manager of the pending intent


        //Stop the Media Player Service to stop sound
        stopService(new Intent(con, AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Toast.makeText(this, "Alarm Canceled/Stop by User.", Toast.LENGTH_SHORT).show();
    }


}
