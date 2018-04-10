package com.sadi.sreda;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    Context con;
    private ImageView imgBack;

    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;


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
