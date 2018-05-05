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
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.sreda.R;
import com.sadi.sreda.model.ExAttanRecordsInfo;
import com.sadi.sreda.model.MyRecordsInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ExMyRecordsAdapter extends RecyclerView.Adapter<ExMyRecordsAdapter.MovieViewHolder> {

    private List<ExAttanRecordsInfo> listMyRecords;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus,tvRecordClockIn,tvRecordClockOut;
        public MovieViewHolder(View v) {
            super(v);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            tvRecordClockIn = (TextView) v.findViewById(R.id.tvRecordClockIn);
            tvRecordClockOut = (TextView) v.findViewById(R.id.tvRecordClockOut);

        }
    }

    public ExMyRecordsAdapter(List<ExAttanRecordsInfo> listMyRecords, Context context) {
        this.listMyRecords = listMyRecords;
        //this.ordersPdf = ordersPdf;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_records, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        final ExAttanRecordsInfo recordsInfo = listMyRecords.get(position);

        String dateCheck_in = recordsInfo.getCheck_in_time();
        String[] partsin = dateCheck_in.split(" ");

        String dateCheck_out = recordsInfo.getCheck_out_time();
        String[] partsOut = dateCheck_out.split(" ");


            holder.tvRecordClockIn.setText(partsin[0]+" "+partsin[1]);
            holder.tvRecordClockOut.setText(partsOut[0]+" "+partsOut[1]);
            holder.tvStatus.setText(recordsInfo.getType());

//            if (recordsInfo.getStatus().equalsIgnoreCase("1")){
//                holder.tvStatus.setText("Waiting");
//            }else if (recordsInfo.getStatus().equalsIgnoreCase("2")){
//                holder.tvStatus.setText("Approved");
//            }else {
//                holder.tvStatus.setText("Rejected");
//            }

    }

    @Override
    public int getItemCount() {
        return listMyRecords.size();
    }




    public void filterList(ArrayList<ExAttanRecordsInfo> filterdNames) {
        this.listMyRecords = filterdNames;
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