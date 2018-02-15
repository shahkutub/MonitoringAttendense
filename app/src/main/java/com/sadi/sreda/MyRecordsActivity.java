package com.sadi.sreda;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by NanoSoft on 11/20/2017.
 */

public class MyRecordsActivity extends AppCompatActivity {
    Context con;
    private ImageView imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_records);
        con=this;
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
