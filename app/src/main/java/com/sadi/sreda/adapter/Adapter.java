package com.sadi.sreda.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sadi.sreda.R;

/**
 * Created by User on 10/9/2016.
 */

public class Adapter extends PagerAdapter {
    private LayoutInflater inflater;
    public Context con;
    Activity activity;
    //Vector<ClubImageInfo> clubImageInfos;
    int test[] = null;

    // constructor
    public Adapter(Context con, int test[], Activity activity) {
        this.con = con;
        this.test = test;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return test.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView image;

        // AppConstant.fitnesspost= position;

        inflater = (LayoutInflater) con
                .getSystemService(con.LAYOUT_INFLATER_SERVICE);
        final View viewLayout = inflater.inflate(R.layout.pager_row,
                container, false);
        ImageView imageviewSlider=(ImageView) viewLayout.findViewById(R.id.imageviewSlider);
        imageviewSlider.setImageResource(test[position]);

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);

    }

}



