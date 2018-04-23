package com.sadi.sreda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sadi.sreda.service.LocationMonitoringServiceBack;
import com.sadi.sreda.utils.PersistentUser;


/**
 * Created by Sadi on 2/11/2018.
 */

public class SplashScreen extends AppCompatActivity{

    private static int SPLASH_TIME_OUT = 3000;
    Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        con = this;
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if(PersistentUser.isLogged(con)){
                    startService(new Intent(con,LocationMonitoringServiceBack.class));
                    stopService(new Intent(con,LocationMonitoringServiceBack.class));
                    startActivity(new Intent(con,MainActivity.class));
                    finish();
                }else {
                    stopService(new Intent(con,LocationMonitoringServiceBack.class));
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
