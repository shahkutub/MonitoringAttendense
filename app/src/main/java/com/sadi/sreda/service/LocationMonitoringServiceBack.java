package com.sadi.sreda.service;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.utils.Api;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.Constants;
import com.sadi.sreda.utils.PersistData;

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
 * Created by devdeeds.com on 27-09-2017.
 */

public class LocationMonitoringServiceBack extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    String  officeName;

    private static final String TAG = LocationMonitoringService.class.getSimpleName();
    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();


    public static final String ACTION_LOCATION_BROADCAST = LocationMonitoringService.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mLocationRequest.setInterval(Constants.LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(Constants.FASTEST_LOCATION_INTERVAL);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }


    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");


        if (location != null) {
            Log.d(TAG, "== location != null");
            //Toast.makeText(this, ""+location.getLongitude(), Toast.LENGTH_SHORT).show();
            //Send result to activities
            //sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            getLocation(""+location.getLatitude(),""+location.getLongitude());

        }

    }



    private void getLocation(final String lat, final String lng) {

//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setCancelable(false);
//            pd.setCancelable(false);
//            pd.setProgress(100);
//            pd.setMessage("Getting location...");
//            pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<LocationInfo>> call = api.getofficeLocation();

        call.enqueue(new Callback<List<LocationInfo>>() {
            @Override
            public void onResponse(Call<List<LocationInfo>> call, Response<List<LocationInfo>> response) {

                List<LocationInfo> myRecordsInfos = new ArrayList<>();

                myRecordsInfos = response.body();

                for (int i = 0; i <myRecordsInfos.size() ; i++) {

                    float[] results = new float[1];
                    Location.distanceBetween(Double.parseDouble(myRecordsInfos.get(i).getLatitude()),
                            Double.parseDouble(myRecordsInfos.get(i).getLongitude()),
                            Double.parseDouble(lat), Double.parseDouble(lng),results);
                    float distanceInMeters = results[0];

                    if( distanceInMeters < 100){


                        AppConstant.locationName = myRecordsInfos.get(i).getLocation_name();
                        officeName = myRecordsInfos.get(i).getLocation_name();

                        Toast.makeText(context, ""+officeName, Toast.LENGTH_SHORT).show();


                        AppConstant.locationInfoList = myRecordsInfos;
                        //AppConstant.saveLocationdat(con,myRecordsInfos);

                        Calendar calendar = Calendar.getInstance();

                        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                        String mint = String.valueOf(calendar.get(Calendar.MINUTE));



                        if(PersistData.getStringData(context,AppConstant.quickAttandance).equalsIgnoreCase("Yes")){

                            if(!TextUtils.isEmpty(officeName)) {

                                if(PersistData.getStringData(context,AppConstant.alarmClockInHour).equalsIgnoreCase(hour)&&
                                        mint.equalsIgnoreCase(PersistData.getStringData(context,AppConstant.alarmClockInMin))){
                                    sendCheckIn(AppConstant.getUserdata(context).getUser_id(), AppConstant.getUserdata(getApplicationContext()).getUsername(),
                                            officeName, getCurrentTimeStamp());
                                }

                                if(PersistData.getStringData(context,AppConstant.alarmClockOutHour).equalsIgnoreCase(hour)&&
                                        mint.equalsIgnoreCase(PersistData.getStringData(context,AppConstant.alarmClockOutMin))){
                                    sendCheckOut(AppConstant.getUserdata(context).getUser_id(),AppConstant.getUserdata(context).getUsername(),
                                            officeName,getCurrentTimeStamp());
                                }
                            }
                        }



                    }
                }



            }

            @Override
            public void onFailure(Call<List<LocationInfo>> call, Throwable t) {

            }
        });
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void sendCheckIn(String userId, String userName, String checkInLocation, String checkInDateTime) {

//        final ProgressDialog pd = new ProgressDialog(context);
//        pd.setCancelable(false);
//        pd.setCancelable(false);
//        pd.setMessage("loading..");
//        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("user_id", userId);
            paramObject.put("username", userName);
            paramObject.put("check_in_location", checkInLocation);
            paramObject.put("check_in_time", checkInDateTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<LoinResponse> userCall = api.storeCheckIn(paramObject.toString());
        userCall.enqueue(new Callback<LoinResponse>() {
            @Override
            public void onResponse(Call<LoinResponse> call, Response<LoinResponse> response) {

                // progressShow.setVisibility(View.GONE);

                LoinResponse loinResponse =  new LoinResponse();

                loinResponse = response.body();

                if (loinResponse.getStatus()==1){
                    stopSelf();
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    PersistData.setStringData(context,AppConstant.checkInOrOut,"in");
                }else {
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoinResponse> call, Throwable t) {
                //progressShow.setVisibility(View.GONE);
            }
        });
    }

    private void sendCheckOut(String userId, String userName, String checkInLocation, String checkInDateTime) {

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
            paramObject.put("user_id", userId);
            //paramObject.put("username", userName);
            paramObject.put("check_out_location", checkInLocation);
            paramObject.put("check_out_time", checkInDateTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<LoinResponse> userCall = api.storeCheckOut(paramObject.toString());
        userCall.enqueue(new Callback<LoinResponse>() {
            @Override
            public void onResponse(Call<LoinResponse> call, Response<LoinResponse> response) {

                // progressShow.setVisibility(View.GONE);

                pd.dismiss();
                LoinResponse loinResponse =  new LoinResponse();

                loinResponse = response.body();

                if (loinResponse.getStatus()==1){
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    stopSelf();
                    PersistData.setStringData(context,AppConstant.checkInOrOut,"out");

                }else {
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoinResponse> call, Throwable t) {
                //progressShow.setVisibility(View.GONE);
                pd.dismiss();
            }
        });
    }


    private void sendMessageToUI(String lat, String lng) {

        Log.d(TAG, "Sending info...");

        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_LATITUDE, lat);
        intent.putExtra(EXTRA_LONGITUDE, lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");

    }


}