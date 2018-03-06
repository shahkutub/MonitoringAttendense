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
import android.widget.ToggleButton;

import com.sadi.sreda.alarm.AlarmReceiver;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

import java.util.Calendar;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    Context con;
    private ImageView imgBack;

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

        }else if(PersistData.getStringData(con,AppConstant.alarmOnOff).equalsIgnoreCase("OFF")){
            toggleAlarm.setChecked(false);
        }
        toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"ON");
                    AppConstant.alarmFirst(con);
                    AppConstant.alarmSecond(con);

                }
                else
                {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"OFF");
                }

            }
        });


    }




}
