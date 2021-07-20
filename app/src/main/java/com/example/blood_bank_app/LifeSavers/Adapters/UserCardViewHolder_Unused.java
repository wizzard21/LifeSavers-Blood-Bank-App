package com.example.blood_bank_app.LifeSavers.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.blood_bank_app.R;

public class UserCardViewHolder_Unused extends RecyclerView.ViewHolder {

    public NetworkImageView userImage;
    public TextView userName;
    public TextView userBloodGrp;
    public TextView userContact;

    public UserCardViewHolder_Unused(@NonNull View userView) {
        super(userView);
        userImage = itemView.findViewById(R.id.user_image);
        userName = itemView.findViewById(R.id.donor_name);
        userBloodGrp = itemView.findViewById(R.id.donor_blood_grp);
        userContact = itemView.findViewById(R.id.donor_contact);
    }
}
