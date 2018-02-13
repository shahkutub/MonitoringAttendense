package com.sadi.sreda.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.sreda.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ReGenerationAdapter extends RecyclerView.Adapter<ReGenerationAdapter.MovieViewHolder> {

    private List<ReGenerationInfo> orderHistoryDatas;
    //private List<OrderData> ordersPdf;
    private int rowLayout;
    private Context context;
    TextView tvGrandTotal;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout allOrderLayout;
        TextView tvProjectName,tvTechnology,tvCapacity,tvLocation,tvFinance,tvCompDate,
        tvPresentStatus;
        public MovieViewHolder(View v) {
            super(v);
            tvProjectName = (TextView) v.findViewById(R.id.tvProjectName);
            tvTechnology = (TextView) v.findViewById(R.id.tvTechnology);
            tvCapacity = (TextView) v.findViewById(R.id.tvCapacity);
            tvLocation = (TextView) v.findViewById(R.id.tvLocation);
            tvFinance = (TextView) v.findViewById(R.id.tvFinance);
            tvCompDate = (TextView) v.findViewById(R.id.tvCompDate);
            tvPresentStatus = (TextView) v.findViewById(R.id.tvPresentStatus);
        }
    }



    public ReGenerationAdapter(List<ReGenerationInfo> orderHistoryDatas, int rowLayout, Context context) {
        this.orderHistoryDatas = orderHistoryDatas;
        //this.ordersPdf = ordersPdf;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        final ReGenerationInfo orderHistoryData = orderHistoryDatas.get(position);


        if(position>0){
            holder.tvProjectName.setText(orderHistoryData.getProjectName());
            holder.tvTechnology.setText(orderHistoryData.getTechnologyType());
            holder.tvCapacity.setText(orderHistoryData.getCapacity());
            holder.tvLocation.setText(orderHistoryData.getLocation());
            holder.tvFinance.setText(orderHistoryData.getFinance());
            holder.tvCompDate.setText(orderHistoryData.getCompletionDate());
            holder.tvPresentStatus.setText(orderHistoryData.getPresentStatus());

        }


//        holder.allOrderLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPdf(orderHistoryData.getCustomerName()+".pdf", "Sale Report");
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return orderHistoryDatas.size();
    }




    public void filterList(ArrayList<ReGenerationInfo> filterdNames) {
        this.orderHistoryDatas = filterdNames;
        notifyDataSetChanged();
    }

    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        //pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}