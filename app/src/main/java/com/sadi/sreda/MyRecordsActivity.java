package com.sadi.sreda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sadi.sreda.adapter.ExMyRecordsAdapter;
import com.sadi.sreda.adapter.MyRecordsAdapter;
import com.sadi.sreda.model.ExAttanRecordsInfo;
import com.sadi.sreda.model.MyRecordsInfo;
import com.sadi.sreda.utils.Api;
import com.sadi.sreda.utils.AppConstant;
import com.sadi.sreda.utils.PersistData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class MyRecordsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Context con;
    private ImageView imgBack;
    private RecyclerView recyclerViewRecords;
    private CircleImageView profile_image_record;
    private TextView tvName,tvDesignation,tvMonth;
    List<MyRecordsInfo> myRecordsList = new ArrayList<>();
    List<ExAttanRecordsInfo> exMyRecordsList = new ArrayList<>();
    private static int SPLASH_TIME_OUT = 3000;
    SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_records);
        con=this;
        initUi();

    }

    private void initUi() {
        swiperefresh = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(this);

        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDesignation = (TextView) findViewById(R.id.tvDesignation);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        profile_image_record = (CircleImageView) findViewById(R.id.profile_image_record);


        if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.path))){

            Glide.with(con)
                    .load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path))
                    .skipMemoryCache(true)
//                    .placeholder(R.drawable.man)
//                    .error(R.drawable.man)
                    .into(profile_image_record);

        }else if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.localpic))){
            Glide.with(con)
                    .load(PersistData.getStringData(con,AppConstant.localpic))
                    .override(100,100)
                    .into(profile_image_record);
        }else {
            profile_image_record.setImageResource(R.drawable.man);
        }

        tvName.setText("User: "+AppConstant.getUserdata(con).getUsername());
        tvDesignation.setText("Designation: "+AppConstant.getUserdata(con).getDesignations());

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        tvMonth.setText("Month: "+month_name);

        recyclerViewRecords = (RecyclerView)findViewById(R.id.recyclerViewRecords);



        //getRecords(AppConstant.getUserdata(con).getUser_id());
        getRecordsEx(AppConstant.getUserdata(con).getUser_id());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getRecords(String id) {
        swiperefresh.setRefreshing(true);
//        final ProgressDialog pd = new ProgressDialog(con);
//        pd.setCancelable(false);
//        pd.setCancelable(false);
//        pd.setMessage("Records loading...");
//        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_attten)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<MyRecordsInfo>> call = api.getAllRecords(id);

        call.enqueue(new Callback<List<MyRecordsInfo>>() {
            @Override
            public void onResponse(Call<List<MyRecordsInfo>> call, Response<List<MyRecordsInfo>> response) {

                //pd.dismiss();
                List<MyRecordsInfo> myRecordsInfos = new ArrayList<>();

                myRecordsInfos = response.body();

                myRecordsList.clear();

                for (int i = 0; i < myRecordsInfos.size(); i++) {
                        myRecordsList.add(myRecordsInfos.get(i));

                }

                MyRecordsAdapter mAdapter = new MyRecordsAdapter(myRecordsInfos,con);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewRecords.setLayoutManager(mLayoutManager);
                recyclerViewRecords.setItemAnimator(new DefaultItemAnimator());
                recyclerViewRecords.setAdapter(mAdapter);
                DividerItemDecoration   mDividerItemDecoration = new DividerItemDecoration(
                        con,
                        LinearLayoutManager.VERTICAL
                );
                recyclerViewRecords.addItemDecoration(mDividerItemDecoration);
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<MyRecordsInfo>> call, Throwable t) {
                swiperefresh.setRefreshing(false);
                //pd.dismiss();
            }
        });
    }

    private void getRecordsEx(String id) {
        swiperefresh.setRefreshing(true);
//        final ProgressDialog pd = new ProgressDialog(con);
//        pd.setCancelable(false);
//        pd.setCancelable(false);
//        pd.setMessage("Records loading...");
//        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_attan_ex)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<ExAttanRecordsInfo>> call = api.getAllRecordsEx(id);

        call.enqueue(new Callback<List<ExAttanRecordsInfo>>() {
            @Override
            public void onResponse(Call<List<ExAttanRecordsInfo>> call, Response<List<ExAttanRecordsInfo>> response) {

                //pd.dismiss();
                List<ExAttanRecordsInfo> myRecordsInfos = new ArrayList<>();

                myRecordsInfos = response.body();

                exMyRecordsList.clear();

                for (int i = 0; i < myRecordsInfos.size(); i++) {
                    exMyRecordsList.add(myRecordsInfos.get(i));

                }

                ExMyRecordsAdapter mAdapter = new ExMyRecordsAdapter(myRecordsInfos,con);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewRecords.setLayoutManager(mLayoutManager);
                recyclerViewRecords.setItemAnimator(new DefaultItemAnimator());
                recyclerViewRecords.setAdapter(mAdapter);
                DividerItemDecoration   mDividerItemDecoration = new DividerItemDecoration(
                        con,
                        LinearLayoutManager.VERTICAL
                );
                recyclerViewRecords.addItemDecoration(mDividerItemDecoration);
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<ExAttanRecordsInfo>> call, Throwable t) {
                swiperefresh.setRefreshing(false);
                //pd.dismiss();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.path))){

            Glide.with(con)
                    .load(AppConstant.photourl+PersistData.getStringData(con,AppConstant.path))
                    .skipMemoryCache(true)
//                    .placeholder(R.drawable.man)
//                    .error(R.drawable.man)
                    .into(profile_image_record);

        }else if(!TextUtils.isEmpty(PersistData.getStringData(con,AppConstant.localpic))){
            Glide.with(con)
                    .load(PersistData.getStringData(con,AppConstant.localpic))
                    .override(100,100)
                    .into(profile_image_record);
        }else {
            profile_image_record.setImageResource(R.drawable.man);
        }
    }

    @Override
    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swiperefresh.setRefreshing(false);
//            }
//
//        }, 3000);

        //getRecords(AppConstant.getUserdata(con).getUser_id());
        getRecordsEx(AppConstant.getUserdata(con).getUser_id());
    }
}
