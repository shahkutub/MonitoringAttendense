package com.sadi.sreda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class ExceptionActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private EditText etDate,etClockIn,etClockOut,etClockInLocation,etComment;
    private Button btnSubmit;
    String format;
    private int CalendarHour, CalendarMinute;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private String date,clockin,clockOut,clockInlocaton,clockOutlocation,comment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.execetion);
        context = this;

        intUi();
    }

    private void intUi() {

        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etDate = (EditText) findViewById(R.id.etDate);
        etClockIn = (EditText) findViewById(R.id.etClockIn);
        etClockOut = (EditText) findViewById(R.id.etClockOut);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText( new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();
            }
        });

        etClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


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

                                etClockIn.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }

        });

        etClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


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

                                etClockOut.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }

        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date=etDate.getText().toString();
                clockin=etClockIn.getText().toString();
                clockOut=etClockOut.getText().toString();
                clockInlocaton=etClockIn.getText().toString();
                clockin=etClockIn.getText().toString();
                clockin=etClockIn.getText().toString();


            }
        });



    }
}
