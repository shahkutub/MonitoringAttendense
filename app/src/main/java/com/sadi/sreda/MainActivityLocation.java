package com.sadi.sreda;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.service.LocationMonitoringService;
import com.sadi.sreda.utils.Api;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.LocationMgr;
import com.sadi.sreda.utils.PersistData;
import com.sadi.sreda.utils.PersistentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivityLocation extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    private boolean mAlreadyStartedService = false;
    private TextView mMsgView;

    Context con;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout reLayClockOut,reLayClockIn,reLaySettings,reLayProfile,reLayAbout,reLayFaq,reLayRecords,reLayException;
    private FrameLayout containerView;
    Animation shake;
    LocationMgr mgr;
    String  officeName;
    TextView tvTitle,tvClockIn,tvClockOut,tvDate,tvTime,tvDateOut,tvTimeOut,tvUserName,tvLogOut,tvGreetingsIn,tvGreetingsOut,
            tvOutTime,tvInTime;
    private RelativeLayout layClockOut,layClockIn;
    private CircleImageView profile_imageCheckIn,profile_imageOut;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mMsgView = (TextView) findViewById(R.id.msgView);
        con=this;
        statusCheck();
        initialization();


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

                if(myRecordsInfos!=null){
                    if(myRecordsInfos.size()>0){
                        for (int i = 0; i <myRecordsInfos.size() ; i++) {

                            float[] results = new float[1];
                            Location.distanceBetween(Double.parseDouble(myRecordsInfos.get(i).getLatitude()),
                                    Double.parseDouble(myRecordsInfos.get(i).getLongitude()),
                                    Double.parseDouble(lat), Double.parseDouble(lng),results);
                            float distanceInMeters = results[0];

                            if( distanceInMeters < 100){

                                // Toast.makeText(context, distanceInMeters+" Meters", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(context, myRecordsInfos.get(i).getLocation_name(), Toast.LENGTH_SHORT).show();
                                tvGreetingsIn.setText(AppConstant.getUserdata(con).getUser_name()+",you are currently at "+myRecordsInfos.get(i).getLocation_name());
                                tvGreetingsOut.setText(AppConstant.getUserdata(con).getUser_name()+",you are currently at "+myRecordsInfos.get(i).getLocation_name());
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
                    }
                }



                AppConstant.locationInfoList = myRecordsInfos;
                //AppConstant.saveLocationdat(con,myRecordsInfos);

                Calendar calendar = Calendar.getInstance();

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int mint = calendar.get(Calendar.MINUTE);



                if(PersistData.getStringData(con,AppConstant.quickAttandance).equalsIgnoreCase("Yes")){

                    if(!TextUtils.isEmpty(officeName)) {

                        if (PersistData.getStringData(con, AppConstant.checkInOrOut).equalsIgnoreCase("in")) {
                            sendCheckOut(AppConstant.getUserdata(con).getUser_id(), AppConstant.getUserdata(con).getUsername(),
                                    officeName, getCurrentTimeStamp());

                        } else if (PersistData.getStringData(con, AppConstant.checkInOrOut).equalsIgnoreCase("out")) {
                            sendCheckIn(AppConstant.getUserdata(con).getUser_id(), AppConstant.getUserdata(con).getUsername(),
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


    private void initialization() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        containerView = (FrameLayout) findViewById(R.id.containerView);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        mDrawerToggle = new ActionBarDrawerToggle(MainActivityLocation.this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // linPieView.setVisibility(View.VISIBLE);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //setContentFragment(new FragementClockIn(), false,"RE Generation Summery Report");

        reLayClockIn = (RelativeLayout)findViewById(R.id.reLayClockIn);
        reLayClockOut = (RelativeLayout)findViewById(R.id.reLayClockOut);
        reLayRecords = (RelativeLayout)findViewById(R.id.reLayRecords);
        reLayException = (RelativeLayout)findViewById(R.id.reLayException);
        reLayFaq = (RelativeLayout)findViewById(R.id.reLayFaq);
        reLayAbout = (RelativeLayout)findViewById(R.id.reLayAbout);
        reLayProfile = (RelativeLayout)findViewById(R.id.reLayProfile);
        reLaySettings = (RelativeLayout)findViewById(R.id.reLaySettings);


        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvTime = (TextView)findViewById(R.id.tvTime);
        tvDateOut = (TextView)findViewById(R.id.tvDateOut);
        tvTimeOut = (TextView)findViewById(R.id.tvTimeOut);
        tvUserName = (TextView)findViewById(R.id.tvUserName);
        tvLogOut = (TextView)findViewById(R.id.tvLogOut);
        tvGreetingsIn = (TextView)findViewById(R.id.tvGreetingsIn);
        tvGreetingsOut = (TextView)findViewById(R.id.tvGreetingsOut);

//        final ProgressDialog pd = new ProgressDialog(con);
//        if(TextUtils.isEmpty(tvGreetingsIn.getText())){
//            pd.setCancelable(false);
//            pd.setCancelable(false);
//            pd.setProgress(100);
//            pd.setMessage("Getting location...");
//            pd.show();
//        }
//
//
//        if(pd.getProgress()==100){
//            pd.dismiss();
//        }

        tvOutTime = (TextView)findViewById(R.id.tvOutTime);
        tvInTime = (TextView)findViewById(R.id.tvInTime);
        profile_imageCheckIn = (CircleImageView)findViewById(R.id.profile_imageCheckIn);
        profile_imageOut = (CircleImageView)findViewById(R.id.out_profile_image);

        if(!TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.path))){
//            Picasso.with(con).load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path)).into(profile_imageCheckIn);

            Glide.with(con)
                    .load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path))
                    .placeholder(R.drawable.man)
                    .error(R.drawable.man)
                    .into(profile_imageCheckIn);

        }

//        else {
//            profile_imageCheckIn.setImageBitmap(AppConstant.StringToBitMap(PersistData.getStringData(con,AppConstant.bitmap)));
//        }

        if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.path))){
//            Picasso.with(con).load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path)).into(profile_imageOut);
            Glide.with(con)
                    .load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path))
                    .placeholder(R.drawable.man)
                    .error(R.drawable.man)
                    .into(profile_imageOut);
        }

//        else {
//            profile_imageOut.setImageBitmap(AppConstant.StringToBitMap(PersistData.getStringData(con,AppConstant.bitmap)));
//        }



        tvUserName.setText(AppConstant.getUserdata(con).getUsername());

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(con,"Alert!","Do you want to logout!");
            }
        });

        final Date date = Calendar.getInstance().getTime();
        final DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        final String today = formatter.format(date);
        tvDate.setText(today);
        tvDateOut.setText(today);

        SimpleDateFormat simpleDateFormat;
        final String time;
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        time = simpleDateFormat.format(date.getTime());
        tvTime.setText(time);
        tvTimeOut.setText(time);


        tvClockIn = (TextView)findViewById(R.id.tvClockIn);
        tvClockOut = (TextView)findViewById(R.id.tvClockOut);
        layClockOut = (RelativeLayout)findViewById(R.id.layClockOut);
        layClockIn = (RelativeLayout)findViewById(R.id.layClockIn);

        if(PersistData.getStringData(con,AppConstant.checkInOrOut).equalsIgnoreCase("in")){
            layClockOut.setVisibility(View.VISIBLE);
            layClockIn.setVisibility(View.GONE);
        }else {
            layClockOut.setVisibility(View.GONE);
            layClockIn.setVisibility(View.VISIBLE);
        }
        //if(!AppConstant.isHq){
        //Toast.makeText(con, PersistData.getStringData(con,AppConstant.officname), Toast.LENGTH_SHORT).show();

        //}

        tvClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DateFormat df1 = new SimpleDateFormat("EEE, dd MMM yyyy");
                DateFormat df = new SimpleDateFormat("");

                try {
                    Date d1 = df.parse( today+" "+"8:30 AM");
                    Date d2 = df.parse(today+" "+time);

                    if(d1.getTime()<d2.getTime()){
//                        layClockOut.setVisibility(View.VISIBLE);
//                        layClockIn.setVisibility(View.GONE);
                        // sendCheckIn("1","admins","badda",getCurrentTimeStamp());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(!TextUtils.isEmpty(AppConstant.locationName)){
                    sendCheckIn(AppConstant.getUserdata(con).getUser_id(),AppConstant.getUserdata(con).getUsername(),
                            AppConstant.locationName,getCurrentTimeStamp());
                }else {
                    Toast.makeText(con, "Location not found!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm a");

                try {
                    Date d1 = df.parse( today+" "+"6:00 PM");
                    Date d2 = df.parse(today+" "+time);

                    if(d1.getTime()<d2.getTime()){

//                        layClockOut.setVisibility(View.GONE);
//                        layClockIn.setVisibility(View.VISIBLE);


                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(!TextUtils.isEmpty(AppConstant.locationName)){
                    sendCheckOut(AppConstant.getUserdata(con).getUser_id(),AppConstant.getUserdata(con).getUsername(),
                            AppConstant.locationName,getCurrentTimeStamp());
                }else {
                    Toast.makeText(con, "Location not found!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        reLayClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppConstant.clockInOu = "in";
                mDrawerLayout.closeDrawer(Gravity.START);
                // setContentFragment(new FragementClockIn(), false,"RE Generation Summery Report");


            }
        });

        reLayClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppConstant.clockInOu = "out";
                mDrawerLayout.closeDrawer(Gravity.START);
                //setContentFragment(new FragementClockOut(), false,"RE Generation Summery Report");


            }
        });

        reLayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,ProfileSettingsActivity.class));
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });

        reLaySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,SettingsActivity.class));
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });

        reLayRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,MyRecordsActivity.class));
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });

        reLayException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,ExceptionActivity.class));
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });


        reLayAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.closeDrawer(Gravity.START);
                Toast.makeText(con, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

        reLayFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.closeDrawer(Gravity.START);
                Toast.makeText(con, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void sendCheckIn(String userId, String userName, String checkInLocation, String checkInDateTime) {

        final ProgressDialog pd = new ProgressDialog(con);
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

                pd.dismiss();
                LoinResponse loinResponse =  new LoinResponse();

                loinResponse = response.body();

                if (loinResponse.getStatus()==1){
                    Toast.makeText(con, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    layClockOut.setVisibility(View.VISIBLE);
                    layClockIn.setVisibility(View.GONE);
                    PersistData.setStringData(con,AppConstant.checkInOrOut,"in");
                }else {
                    Toast.makeText(con, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoinResponse> call, Throwable t) {
                //progressShow.setVisibility(View.GONE);
                pd.dismiss();
            }
        });
    }



    private void sendCheckOut(String userId, String userName, String checkInLocation, String checkInDateTime) {

        final ProgressDialog pd = new ProgressDialog(con);
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
                    Toast.makeText(con, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    layClockOut.setVisibility(View.GONE);
                    layClockIn.setVisibility(View.VISIBLE);
                    PersistData.setStringData(con,AppConstant.checkInOrOut,"out");

                }else {
                    Toast.makeText(con, loinResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoinResponse> call, Throwable t) {
                //progressShow.setVisibility(View.GONE);
                pd.dismiss();
            }
        });
    }

    private void showMessage(final Context c,final String title, final String message) {
        final android.app.AlertDialog.Builder aBuilder = new android.app.AlertDialog.Builder(c);
        aBuilder.setTitle(title);
        aBuilder.setIcon(R.mipmap.ic_launcher);
        aBuilder.setMessage(message);

        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                PersistentUser.logOut(con);
                finish();
                dialog.dismiss();
            }
        });

        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
            }

        });
        aBuilder.show();
    }

    @Override
    public void onResume() {
        super.onResume();

                if(!TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.path))){
//            Picasso.with(con).load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path)).into(profile_imageCheckIn);

                    Glide.with(con)
                            .load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path))
                            .placeholder(R.drawable.man)
                            .error(R.drawable.man)
                            .into(profile_imageCheckIn);

                }

//        else {
//            profile_imageCheckIn.setImageBitmap(AppConstant.StringToBitMap(PersistData.getStringData(con,AppConstant.bitmap)));
//        }

                if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.path))){
//            Picasso.with(con).load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path)).into(profile_imageOut);
                    Glide.with(con)
                            .load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path))
                            .placeholder(R.drawable.man)
                            .error(R.drawable.man)
                            .into(profile_imageOut);
                }

//        else {
//            profile_imageOut.setImageBitmap(AppConstant.StringToBitMap(PersistData.getStringData(con,AppConstant.bitmap)));
//        }






        startStep1();

        startService(new Intent(con,LocationMonitoringService.class));
        LocalBroadcastManager.getInstance(con).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);

                        if (latitude != null && longitude != null) {
                            getLocation(latitude,""+longitude);
                            // Toast.makeText(con, latitude, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );

    }


    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
          //  Toast.makeText(getApplicationContext(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityLocation.this);
//        builder.setTitle(R.string.title_alert_no_intenet);
//        builder.setMessage(R.string.msg_alert_no_internet);
//
//        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {

                            //Now make sure about location permission.
                            if (checkPermissions()) {

                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService && mMsgView != null) {

            //mMsgView.setText(R.string.msg_location_service_started);

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();

//                LocationMonitoringService locationMonitoringService = new LocationMonitoringService();
//                locationMonitoringService.buildGoogleApiClient();
            }
            return false;
        }
        return true;
    }


    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivityLocation.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivityLocation.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                //startStep3();
                //statusCheck();
            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    @Override
    public void onDestroy() {


        //Stop location sharing service to app server.........

//        stopService(new Intent(this, LocationMonitoringService.class));
//        mAlreadyStartedService = false;
//        //Ends................................................
//
//
        super.onDestroy();
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }
        private void buildAlertMessageNoGps() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }




}
