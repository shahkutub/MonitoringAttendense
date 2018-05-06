package com.sadi.sreda;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

import java.util.Calendar;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    Context con;
    private ImageView imgBack;

    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;
    private int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private RelativeLayout rLClockIn,rLClockOut;
    private TextView tvClockIn,tvClockOut;
    private ToggleButton toggleAlarmIn,toggleAlarmOut,toggleQuickAttan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        con=this;
        initUi();
    }

    private void initUi() {



        imgBack = (ImageView)findViewById(R.id.imgBack);
        rLClockIn = (RelativeLayout) findViewById(R.id.rLClockIn);
        rLClockOut = (RelativeLayout)findViewById(R.id.rLClockOut);
        tvClockIn = (TextView) findViewById(R.id.tvClockIn);
        tvClockOut = (TextView) findViewById(R.id.tvClockOut);

        tvClockIn.setText(PersistData.getStringData(con,AppConstant.alarmClockIn));
        tvClockOut.setText(PersistData.getStringData(con,AppConstant.alarmClockout));



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toggleAlarmIn = (ToggleButton)findViewById(R.id.toggleAlarmIn);
        toggleAlarmOut = (ToggleButton)findViewById(R.id.toggleAlarmOut);
        toggleQuickAttan = (ToggleButton)findViewById(R.id.toggleQuickAttan);

        if(PersistData.getStringData(con,AppConstant.quickAttandance).equalsIgnoreCase("Yes")){
            toggleQuickAttan.setChecked(true);
        }else if(PersistData.getStringData(con,AppConstant.quickAttandance).equalsIgnoreCase("No")){
            toggleQuickAttan.setChecked(false);
        }

        toggleQuickAttan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked){
                    PersistData.setStringData(con,AppConstant.quickAttandance,"Yes");
                }else {
                    PersistData.setStringData(con,AppConstant.quickAttandance,"No");
                }
            }
        });


        if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.alarmClockInHour))){
            toggleAlarmIn.setChecked(true);

        }else if(TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.alarmClockInHour))){
            toggleAlarmIn.setChecked(false);
        }


        if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.alarmClockOutHour))){
            toggleAlarmOut.setChecked(true);

        }else if(TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.alarmClockOutHour))){
            toggleAlarmOut.setChecked(false);
        }


        toggleAlarmIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {

                    calendar = Calendar.getInstance();
                    CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                    CalendarMinute = calendar.get(Calendar.MINUTE);


                    timepickerdialog = new TimePickerDialog(con,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    //toggleAlarmIn.setChecked(true);
                                    PersistData.setStringData(con,AppConstant.alarmClockInHour,""+hourOfDay);
                                    PersistData.setStringData(con,AppConstant.alarmClockInMin,""+minute);

                                    if (hourOfDay == 0) {

                                        hourOfDay += 12;

                                        format = " AM";
                                    }
                                    else if (hourOfDay == 12) {

                                        format = " PM";

                                    }
                                    else if (hourOfDay > 12) {

                                        hourOfDay -= 12;

                                        format = " PM";

                                    }
                                    else {

                                        format = " AM";
                                    }

                                    tvClockIn.setText(hourOfDay + ":" + minute + format);
                                    PersistData.setStringData(con,AppConstant.alarmClockIn,hourOfDay + ":" + minute + format);
                                    AppConstant.alarmClockIn(con);
                                }
                            }, CalendarHour, CalendarMinute, false);
                    timepickerdialog.show();



                }else{
                    tvClockIn.setText("");
                    PersistData.setStringData(con,AppConstant.alarmClockInHour,"0");
                    PersistData.setStringData(con,AppConstant.alarmClockInMin,"0");
                }



            }
        });


        toggleAlarmOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    calendar = Calendar.getInstance();
                    CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                    CalendarMinute = calendar.get(Calendar.MINUTE);


                    timepickerdialog = new TimePickerDialog(con,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    //toggleAlarmOut.setChecked(true);
                                    PersistData.setStringData(con,AppConstant.alarmClockOutHour,""+hourOfDay);
                                    PersistData.setStringData(con,AppConstant.alarmClockOutMin,""+minute);

                                    if (hourOfDay == 0) {

                                        hourOfDay += 12;

                                        format = " AM";
                                    }
                                    else if (hourOfDay == 12) {

                                        format = " PM";

                                    }
                                    else if (hourOfDay > 12) {

                                        hourOfDay -= 12;

                                        format = " PM";

                                    }
                                    else {

                                        format = " AM";
                                    }

                                    tvClockOut.setText(hourOfDay + ":" + minute + format);
                                    PersistData.setStringData(con,AppConstant.alarmClockout,hourOfDay + ":" + minute + format);
                                    AppConstant.alarmClockOut(con);
                                }
                            }, CalendarHour, CalendarMinute, false);
                    timepickerdialog.show();

                }else {
                    tvClockOut.setText("");
                    PersistData.setStringData(con,AppConstant.alarmClockOutHour,"0");
                    PersistData.setStringData(con,AppConstant.alarmClockOutMin,"0");
                }

            }
        });


    }


}
