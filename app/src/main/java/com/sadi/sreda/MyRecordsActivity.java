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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        List<MyRecordsInfo> myRecordsInfos = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            myRecordsInfos.add(i,new MyRecordsInfo());
        }


        MyRecordsAdapter mAdapter = new MyRecordsAdapter(myRecordsInfos,con);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewRecords.setLayoutManager(mLayoutManager);
        recyclerViewRecords.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRecords.setAdapter(mAdapter);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
