package com.sadi.sreda;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.utils.AlertMessage;
import com.sadi.sreda.utils.Api;
import com.sadi.sreda.utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class ExceptionActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private EditText etInDate,etOutDate,etClockIn,etClockOut,etClockInLocation,etClockOutLocation,etComment;
    private Button btnSubmit;
    String format;
    private int CalendarHour, CalendarMinute;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private String inDate,outDate,clockIn,clockOut,clockOutLocation,clockInLocation,comment;
    private String timeClockIn,timeClockOut,clockInDateTime,clockOutDateTime;
    Spinner spnCheckInLocation,spnCheckOutLocation;
    List<String> listClockInLocation = new ArrayList<String>();
    List<String> listClockOutLocation = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.execetion);
        context = this;
        getLocation();
        intUi();
    }

    private void intUi() {


//            Date time = new Date(getNetworkTime());



        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        etInDate = (EditText) findViewById(R.id.etInDate);
        etOutDate = (EditText) findViewById(R.id.etOutDate);
        etClockIn = (EditText) findViewById(R.id.etClockIn);
        etClockOut = (EditText) findViewById(R.id.etClockOut);
        etClockInLocation = (EditText) findViewById(R.id.etClockInLocation);
        etClockOutLocation = (EditText) findViewById(R.id.etClockOutLocation);
        etComment = (EditText) findViewById(R.id.etComment);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        spnCheckInLocation = (Spinner)findViewById(R.id.spnCheckInLocation);
        spnCheckOutLocation = (Spinner)findViewById(R.id.spnCheckOutLocation);

        listClockInLocation.add(0,"Select clock in location");
        listClockOutLocation.add(0,"Select clock Out location");

        ArrayAdapter<String> adapterIn = new ArrayAdapter<String>(this,
                R.layout.spinner_item,listClockInLocation);

        ArrayAdapter<String> adapterOut = new ArrayAdapter<String>(this,
                R.layout.spinner_item,listClockOutLocation);
        spnCheckInLocation.setAdapter(adapterIn);
        spnCheckOutLocation.setAdapter(adapterOut);

        spnCheckInLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!spnCheckInLocation.getSelectedItem().toString().equalsIgnoreCase("Select clock in location")){
                    clockInLocation=spnCheckInLocation.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCheckOutLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!spnCheckOutLocation.getSelectedItem().toString().equalsIgnoreCase("Select clock out location")){
                    clockOutLocation=spnCheckOutLocation.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etInDate.setText( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        etOutDate.setText( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        etClockIn.setText( new SimpleDateFormat("hh:mm a").format(new Date()));
        etClockOut.setText( new SimpleDateFormat("hh:mm a").format(new Date()));

        timeClockIn = new SimpleDateFormat("HH:mm:ss").format(new Date());
        timeClockOut = new SimpleDateFormat("HH:mm:ss").format(new Date());



        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etInDate.setText( new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        final DatePickerDialog endTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etOutDate.setText( new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        etInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();
            }
        });

        etOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime.show();
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


                                timeClockIn = hourOfDay+":"+minute+":00";

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
                                timeClockOut = hourOfDay+":"+minute+":00";

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

                inDate=etInDate.getText().toString();
                outDate=etOutDate.getText().toString();
//                clockIn=etClockIn.getText().toString();
//                clockOut=etClockOut.getText().toString();
//                clockInLocation=etClockInLocation.getText().toString();
//                clockOutLocation=etClockOutLocation.getText().toString();
                comment=etComment.getText().toString();

                if(TextUtils.isEmpty(inDate)){
                    AlertMessage.showMessage(context,"Alert","Enter clock in date");
                }else if(TextUtils.isEmpty(timeClockIn)){
                    AlertMessage.showMessage(context,"Alert","Enter clock in time!");
                }else if(TextUtils.isEmpty(outDate)){
                    AlertMessage.showMessage(context,"Alert","Enter clock out date!");
                }else if(TextUtils.isEmpty(timeClockOut)){
                    AlertMessage.showMessage(context,"Alert","Enter clock out time!");
                }else if(TextUtils.isEmpty(clockInLocation)){
                    AlertMessage.showMessage(context,"Alert","Select clock in location!");
                }else if(TextUtils.isEmpty(clockOutLocation)){
                    AlertMessage.showMessage(context,"Alert","Select clock out location!");
                }else if(TextUtils.isEmpty(comment)){
                    AlertMessage.showMessage(context,"Alert","Enter comment!");
                }else {
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    clockInDateTime = inDate+" "+timeClockIn;
                    clockOutDateTime = outDate+" "+timeClockOut;


                    sendData(inDate,clockInDateTime,clockOutDateTime,clockInLocation,clockOutLocation,comment);
                }

            }
        });



    }

    private void getLocation() {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
            pd.setCancelable(false);
            pd.setProgress(100);
            pd.setMessage("Getting location...");
            pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<LocationInfo>> call = api.getofficeLocation();

        call.enqueue(new Callback<List<LocationInfo>>() {
            @Override
            public void onResponse(Call<List<LocationInfo>> call, Response<List<LocationInfo>> response) {

                pd.dismiss();
                List<LocationInfo> myRecordsInfos = new ArrayList<>();

                myRecordsInfos = response.body();

                for (int i = 0; i <myRecordsInfos.size() ; i++) {

                    listClockInLocation.add(myRecordsInfos.get(i).getLocation_name());
                    listClockOutLocation.add(myRecordsInfos.get(i).getLocation_name());

                }

            }

            @Override
            public void onFailure(Call<List<LocationInfo>> call, Throwable t) {

                pd.dismiss();
            }
        });
    }


    private void sendData(String date, String clockin, String clockOut, String clockInLocation,
                          String clockOutLocation, String comment) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setCancelable(false);
        pd.setMessage("loading..");
        pd.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("user_id", AppConstant.getUserdata(context).getUser_id());
            paramObject.put("username", AppConstant.getUserdata(context).getUsername());
            paramObject.put("check_in_location", clockInLocation);
            paramObject.put("check_in_time", clockin);
            paramObject.put("check_out_location", clockOutLocation);
            paramObject.put("check_out_time", clockOut);
            paramObject.put("comments", comment);

//            {"user_id":"12",
//                    "username":"sadi",
//                    "check_in_location":"Sadi home",
//                    "check_in_time":"2018-04-19 22:18",
//                    "check_out_location":"Sadi home",
//                    "check_out_time":"2018-04-19 22:18",
//                    "comments":"test"
//            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<LoinResponse> userCall = api.storeException(paramObject.toString());
        userCall.enqueue(new Callback<LoinResponse>() {
            @Override
            public void onResponse(Call<LoinResponse> call, Response<LoinResponse> response) {

                pd.dismiss();

                LoinResponse loinResponse =  new LoinResponse();

                loinResponse = response.body();

                if (loinResponse.getStatus()==1){
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    //PersistData.setStringData(context, AppConstant.checkInOrOut,"out");
                    finish();
                }else {
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoinResponse> call, Throwable t) {
                pd.dismiss();
                //progressShow.setVisibility(View.GONE);
            }
        });

    }
}
