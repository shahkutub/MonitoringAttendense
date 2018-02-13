package com.sadi.sreda.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.sreda.R;
import com.sadi.sreda.adapter.ReGenerationAdapter;
import com.sadi.sreda.adapter.ReGenerationInfo;
import com.sadi.sreda.utils.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.*;


/**
 * Created by User on 7/20/2016.
 */
public class FragementClockIn extends BaseFragment {
    Context con;
    TextView tvTitle,tvClockIn,tvClockOut;
    private RelativeLayout layClockOut,layClockIn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        con = getActivity();
        Toast.makeText(con, "onCreateView", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.clock_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        con = getActivity();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        Toast.makeText(con, "onActivityCreated", Toast.LENGTH_SHORT).show();

        initUi();
    }

    private void initUi() {

        tvTitle = (TextView)getView().findViewById(R.id.tvTitle);
        tvClockIn = (TextView)getView().findViewById(R.id.tvClockIn);
        tvClockOut = (TextView)getView().findViewById(R.id.tvClockOut);
        layClockOut = (RelativeLayout)getView().findViewById(R.id.layClockOut);
        layClockIn = (RelativeLayout)getView().findViewById(R.id.layClockIn);
        tvClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layClockOut.setVisibility(View.VISIBLE);
                layClockIn.setVisibility(View.GONE);
            }
        });

        layClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layClockOut.setVisibility(View.GONE);
                layClockIn.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(con, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(con, "onDestroyView", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(con, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(con, "onStart", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(con, "onStop", Toast.LENGTH_SHORT).show();
    }


}
