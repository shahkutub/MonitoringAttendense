package com.sadi.sreda;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.sreda.fragement.FragementClockIn;
import com.sadi.sreda.fragement.FragementClockOut;
import com.sadi.sreda.utils.Appconstant;
import com.sadi.sreda.utils.OnFragmentInteractionListener;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    Context con;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Activity activity;
    private Handler handler;
    private boolean isTimerRunning;
    Runnable Update;
    Timer swipeTimer;
    public static int[] imageRSC = {R.drawable.banar,R.drawable.banar};

    private Fragment backFragement;
    ActionBarDrawerToggle mDrawerToggle;
    TextView tvClockIn,tvClockOut;
    private RelativeLayout reLayClockOut,reLayClockIn,reLaySettings,reLayProfile,reLayAbout,reLayFaq,reLayRecords;

    private ImageView imgMenu;
    private FrameLayout containerView;
    Animation shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = this;
        //Toast.makeText(con, "onCreate", Toast.LENGTH_SHORT).show();
        initialization();
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

        setContentFragment(new FragementClockIn(), false,"RE Generation Summery Report");

        reLayClockIn = (RelativeLayout)findViewById(R.id.reLayClockIn);
        reLayClockOut = (RelativeLayout)findViewById(R.id.reLayClockOut);
        reLayRecords = (RelativeLayout)findViewById(R.id.reLayRecords);
        reLayFaq = (RelativeLayout)findViewById(R.id.reLayFaq);
        reLayAbout = (RelativeLayout)findViewById(R.id.reLayAbout);
        reLayProfile = (RelativeLayout)findViewById(R.id.reLayProfile);
        reLaySettings = (RelativeLayout)findViewById(R.id.reLaySettings);

        reLayClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appconstant.clockInOu = "in";
                mDrawerLayout.closeDrawer(Gravity.START);
                setContentFragment(new FragementClockIn(), false,"RE Generation Summery Report");


            }
        });

        reLayClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appconstant.clockInOu = "out";
                mDrawerLayout.closeDrawer(Gravity.START);
                setContentFragment(new FragementClockOut(), false,"RE Generation Summery Report");


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
