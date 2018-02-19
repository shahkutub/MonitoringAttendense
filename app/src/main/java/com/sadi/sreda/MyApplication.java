package com.sadi.sreda;

import android.app.Application;

import com.sadi.sreda.utils.TypefaceUtil;

/**
 * Created by Sadi on 2/18/2018.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/SolaimanLipi_reg.ttf");
    }
}
