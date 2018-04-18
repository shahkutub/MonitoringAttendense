package com.sadi.sreda;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sadi.sreda.adapter.MyRecordsAdapter;
import com.sadi.sreda.model.MyRecordsInfo;
import com.sadi.sreda.utils.Api;
import com.sadi.sreda.utils.AppConstant;

import java.util.ArrayList;
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

public class MyRecordsActivity extends AppCompatActivity {
    Context con;
    private ImageView imgBack;
    private RecyclerView recyclerViewRecords;
    private CircleImageView profile_image_record;
    private TextView tvName,tvDesignation,tvMonth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_records);
        con=this;
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        recyclerViewRecords = (RecyclerView)findViewById(R.id.recyclerViewRecords);

        getRecords(AppConstant.getUserdata(con).getUser_id());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getRecords(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL_attten)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<MyRecordsInfo>> call = api.getAllRecords(id);

        call.enqueue(new Callback<List<MyRecordsInfo>>() {
            @Override
            public void onResponse(Call<List<MyRecordsInfo>> call, Response<List<MyRecordsInfo>> response) {

                List<MyRecordsInfo> myRecordsInfos = new ArrayList<>();

                myRecordsInfos = response.body();
                MyRecordsAdapter mAdapter = new MyRecordsAdapter(myRecordsInfos,con);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewRecords.setLayoutManager(mLayoutManager);
                recyclerViewRecords.setItemAnimator(new DefaultItemAnimator());
                recyclerViewRecords.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<MyRecordsInfo>> call, Throwable t) {

            }
        });
    }

}
