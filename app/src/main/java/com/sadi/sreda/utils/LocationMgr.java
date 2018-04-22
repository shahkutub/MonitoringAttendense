package com.sadi.sreda.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.model.MyRecordsInfo;

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
 * Created by NanoSoft on 2/15/2018.
 */

public class LocationMgr implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    Context context;
    // private MyTextView tvLatLng;
    LocationRequest mLocationRequest;
    public GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private double currentLat;
    private double currentLong;
    private TextView tvGreetingsIn,tvGreetingsOut;
    String  officeName;
    String dateCheck_in,dateCheck_out;

    List<MyRecordsInfo> myRecordsInfos = new ArrayList<>();

    public LocationMgr(Context context,TextView tvGreetingsIn,TextView tvGreetingsOut) {
        this.context = context;
        this.tvGreetingsIn = tvGreetingsIn;
        this.tvGreetingsOut = tvGreetingsOut;
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


//            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                    mGoogleApiClient);
//            if (mLastLocation != null) {
//                //place marker at current position
//                //mGoogleMap.clear();
//                latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Current Position");
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                mCurrLocationMarker = mMap.addMarker(markerOptions);
//
//               // addMarkersToMap();
//
//            }

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000); //5 seconds
            mLocationRequest.setFastestInterval(3000); //3 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        PersistData.setStringData(context, AppConstant.lat,""+location.getLatitude());
        PersistData.setStringData(context, AppConstant.lng,""+location.getLongitude());
        //Toast.makeText(context, "Lat: "+location.getLatitude()+" Lng: "+location.getLongitude(), Toast.LENGTH_SHORT).show();
        //tvLatLng.setText(location.getLatitude()+"\n"+location.getLongitude());

        //getRecords(AppConstant.getUserdata(context).getUser_id());

        getLocation(""+location.getLatitude(),""+location.getLongitude());


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

                           // Toast.makeText(context, distanceInMeters+" Meters", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(context, myRecordsInfos.get(i).getLocation_name(), Toast.LENGTH_SHORT).show();
                            tvGreetingsIn.setText(AppConstant.getUserdata(context).getUser_name()+",you are currently at "+myRecordsInfos.get(i).getLocation_name());
                            tvGreetingsOut.setText(AppConstant.getUserdata(context).getUser_name()+",you are currently at "+myRecordsInfos.get(i).getLocation_name());
                            //PersistData.setStringData(context,AppConstant.officname,myRecordsInfos.get(i).getLocation_name().toString());

                            AppConstant.locationName = myRecordsInfos.get(i).getLocation_name();
                            officeName = myRecordsInfos.get(i).getLocation_name();
                           // AppConstant.officname=myRecordsInfos.get(i).getLocation_name();
//                            AppConstant.isHq = true;
//                            if(PersistData.getStringData(context, AppConstant.quickAttandance).equalsIgnoreCase("Yes")){
//                                //Toast.makeText(context, "Data send", Toast.LENGTH_SHORT).show();
//
//                            }


                        }else {
                            //AppConstant.locationName = "";
                        }
                }

                AppConstant.locationInfoList = myRecordsInfos;
                //AppConstant.saveLocationdat(con,myRecordsInfos);

                Calendar calendar = Calendar.getInstance();

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int mint = calendar.get(Calendar.MINUTE);



                    if(PersistData.getStringData(context,AppConstant.quickAttandance).equalsIgnoreCase("Yes")){

                        if(!TextUtils.isEmpty(officeName)) {

                            if (PersistData.getStringData(context, AppConstant.checkInOrOut).equalsIgnoreCase("in")) {
                                sendCheckOut(AppConstant.getUserdata(context).getUser_id(), AppConstant.getUserdata(context).getUsername(),
                                        officeName, getCurrentTimeStamp());

                            } else if (PersistData.getStringData(context, AppConstant.checkInOrOut).equalsIgnoreCase("out")) {
                                sendCheckIn(AppConstant.getUserdata(context).getUser_id(), AppConstant.getUserdata(context).getUsername(),
                                        officeName, getCurrentTimeStamp());
                            }
                        }


                        //                        if(hour<12){
//                            sendCheckIn(AppConstant.getUserdata(context).getUser_id(),AppConstant.getUserdata(context).getUsername(), officeName,getCurrentTimeStamp());
//                        }
//
//                        if(hour>18){
//                            sendCheckOut(AppConstant.getUserdata(context).getUser_id(),AppConstant.getUserdata(context).getUsername(), officeName,getCurrentTimeStamp());
//                        }
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
        Call<LoinResponse> userCall = api.storeCheckOut(paramObject.toString());
        userCall.enqueue(new Callback<LoinResponse>() {
            @Override
            public void onResponse(Call<LoinResponse> call, Response<LoinResponse> response) {

                // progressShow.setVisibility(View.GONE);

                LoinResponse loinResponse =  new LoinResponse();

                loinResponse = response.body();

                if (loinResponse.getStatus()==1){
                    Toast.makeText(context, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    PersistData.setStringData(context,AppConstant.checkInOrOut,"out");
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

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity)context),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(((Activity)context),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(((Activity)context),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted. Do the
//                    // contacts-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(context,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        // mMap.setMyLocationEnabled(true);
//                    }
//
//                } else {
//
//                    // Permission denied, Disable the functionality that depends on this permission.
//                    Toast.makeText(context, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other permissions this app might request.
//            // You can add here other case statements according to your requirement.
//        }
//    }

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


        final LocationManager manager = (LocationManager) ((Activity)context).getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

}
