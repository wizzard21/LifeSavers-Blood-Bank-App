package com.example.blood_bank_app.LifeSavers.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blood_bank_app.LifeSavers.Activities.MainActivity;
import com.example.blood_bank_app.LifeSavers.Activities.SearchResultActivity;
import com.example.blood_bank_app.LifeSavers.DataModels.Donor;
import com.example.blood_bank_app.LifeSavers.DataModels.RequestDataModel;
import com.example.blood_bank_app.R;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private RequestAdapter.OnItemClickListener listener;
    String number;
    private List<RequestDataModel> dataSet;
    private Context context;

    public RequestAdapter(List<RequestDataModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.name.setText(dataSet.get(position).getName());
        holder.blood_group.setText(dataSet.get(position).getBloodgrp());
        holder.city.setText(dataSet.get(position).getCity());
        holder.message.setText(dataSet.get(position).getMessage());
        Glide.with(holder.profile_image.getContext()).load(dataSet.get(position).getUrl()).into(holder.profile_image);
        number=dataSet.get(position).getPhno();

        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = "***Need Blood***\n"+"Name: "+dataSet.get(position).getName()+"\n"+"Blood group: "+dataSet.get(position).getBloodgrp()+"\n"+"City: "+dataSet.get(position).getCity()+"\n"+"Mobile No: "+dataSet.get(position).getPhno()+"\n"+"Message: "+dataSet.get(position).getMessage();
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Need Blodd");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView message,name,blood_group,city;
        ImageView profile_image,call_button,share_button;
        ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView_name);
            blood_group=itemView.findViewById(R.id.textView_blood_group);
            city=itemView.findViewById(R.id.textView_city);
            message=itemView.findViewById(R.id.textView_message);
            profile_image=itemView.findViewById(R.id.profile_image);
            call_button=itemView.findViewById(R.id.call_button);
            share_button=itemView.findViewById(R.id.share_button);

            call_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(dataSet.get(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(RequestDataModel requestDataModel);
    }

    public void setOnItemClickListener(RequestAdapter.OnItemClickListener listener){
        this.listener=listener;
    }


}