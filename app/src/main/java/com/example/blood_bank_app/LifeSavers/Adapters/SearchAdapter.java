package com.example.blood_bank_app.LifeSavers.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood_bank_app.LifeSavers.DataModels.Donor;
import com.example.blood_bank_app.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private List<Donor> dataSet;
    private Context context;

    public SearchAdapter(
            List<Donor> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,
                                 final int position) {
        holder.name.setText(dataSet.get(position).getName());
        holder.number.setText(dataSet.get(position).getMobileno());
        holder.city.setText(dataSet.get(position).getCity());
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,city,number;
        ImageView imageView,callButton;
        ViewHolder(final View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.textView_number);
            name=itemView.findViewById(R.id.textView_name);
            city=itemView.findViewById(R.id.textView_city);
            imageView = itemView.findViewById(R.id.image);
            callButton =  itemView.findViewById(R.id.call_button);

            callButton.setOnClickListener(new View.OnClickListener() {
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
        void onItemClick(Donor donor);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}

