package com.sadi.sreda;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.utils.Api;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.GoogleService;
import com.sadi.sreda.utils.LocationMgr;
import com.sadi.sreda.utils.OnFragmentInteractionListener;
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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    Context con;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout reLayClockOut,reLayClockIn,reLaySettings,reLayProfile,reLayAbout,reLayFaq,reLayRecords;
    private FrameLayout containerView;
    Animation shake;
    LocationMgr mgr;

    TextView tvTitle,tvClockIn,tvClockOut,tvDate,tvTime,tvDateOut,tvTimeOut,tvUserName,tvLogOut,tvGreetingsIn,tvGreetingsOut,
            tvOutTime,tvInTime;
    private RelativeLayout layClockOut,layClockIn;
    private CircleImageView profile_imageCheckIn;

    String location;
    List<LocationInfo> listLocation = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = this;

       // listLocation = AppConstant.getLocationList(con);
        requestPermission();
//        if(checkPermission()){
//
//
//        }else if(!checkPermission()){
//            requestPermission();
//        }
        initialization();
        statusCheck();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void statusCheck() {

        mgr = new LocationMgr(con,tvGreetingsIn,tvGreetingsOut);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mgr.mGoogleApiClient == null) {
                    mgr.buildGoogleApiClient();
                }

                Intent intent = new Intent(getApplicationContext(), GoogleService.class);
                startService(intent);
            }
        } else {

            requestPermission();
//            if (mgr.mGoogleApiClient == null) {
//                mgr.buildGoogleApiClient();
//            }
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED
                 && result2 == PackageManager.PERMISSION_GRANTED
                 && result3 == PackageManager.PERMISSION_GRANTED
                 && result4 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,
                ACCESS_COARSE_LOCATION,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readPhoneAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    //  Toast.makeText(con, ""+imei, Toast.LENGTH_SHORT).show();

                    if (locationAccepted && cameraAccepted && readPhoneAccepted) {
                        // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                        if (mgr.mGoogleApiClient == null) {
                            mgr.buildGoogleApiClient();
                        }
                    } else {

                        //Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_PHONE_STATE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
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










    @Override
    public void setContentFragment(Fragment fragment, boolean addToBackStack,String title) {
        if (fragment == null) {
            return;
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.containerView);

        if (currentFragment != null && fragment.getClass().isAssignableFrom(currentFragment.getClass())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }


    @Override
    public void addContentFragment(Fragment fragment, boolean addToBackStack,String title) {
        if (fragment == null) {
            return;
        }


        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.containerView);

        if (currentFragment != null && fragment.getClass().isAssignableFrom(currentFragment.getClass())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerView, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }

    }

    public void exitFromApp() {
        final CharSequence[] items = { "NO", "YES" };
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit from app?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        return;
                    case 1:
                        // onStopRecording();

                        finish();


                        break;
                    default:
                        return;
                }
            }
        });
        builder.show();
        builder.create();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if(buttonView.getVisibility()==View.GONE){
//                buttonView.setVisibility(View.VISIBLE);
//            }else {
                exitFromApp();
           // }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
