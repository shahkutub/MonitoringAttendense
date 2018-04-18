package com.sadi.sreda;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private ToggleButton toggleAlarm,toggleQuickAttan;
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

        rLClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(con,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                toggleAlarm.setChecked(false);
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
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }

        });


        rLClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(con,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                toggleAlarm.setChecked(false);
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

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }

        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toggleAlarm = (ToggleButton)findViewById(R.id.toggleAlarm);
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
                    if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.alarmClockOutHour)) && !TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.alarmClockInHour))){
                        PersistData.setStringData(con, AppConstant.alarmOnOff,"ON");
                        AppConstant.alarmClockIn(con);
                        AppConstant.alarmClockOut(con);
                    }else {
                        toggleAlarm.setChecked(false);
                        Toast.makeText(con, "Please set clock_in/out time!", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    PersistData.setStringData(con, AppConstant.alarmOnOff,"OFF");
                }

            }
        });


    }


}
