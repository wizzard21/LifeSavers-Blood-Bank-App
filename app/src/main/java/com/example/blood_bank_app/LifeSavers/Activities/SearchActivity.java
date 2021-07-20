package com.example.blood_bank_app.LifeSavers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank_app.LifeSavers.Utils.EndPoints;
import com.example.blood_bank_app.LifeSavers.Utils.VolleySingleton;
import com.example.blood_bank_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String[] BLOODGRPS = new String[]{"A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"};
        AutoCompleteTextView tv_bldgrp = findViewById(R.id.tv_bldgrp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BLOODGRPS);
        tv_bldgrp.setAdapter(adapter);


        final EditText et_blood_group, et_city;
        //et_blood_group = findViewById(R.id.et_blood_group);
        et_city = findViewById(R.id.et_city);
        Button submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String blood_group = tv_bldgrp.getText().toString();
                String city = et_city.getText().toString();
                if(isValid(blood_group, city)){
                    get_search_results(blood_group, city);
                }
            }
        });
    }


    private void get_search_results(final String blood_group, final String city) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, EndPoints.search_donors, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //json response
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("blood_group", blood_group);
                intent.putExtra("json", response);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", Objects.requireNonNull(error.getMessage()));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("city", city);
                params.put("blood_group", blood_group);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isValid(String blood_group, String city){
        List<String> valid_blood_groups = new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");
        if(!valid_blood_groups.contains(blood_group)){
            showMsg("Blood group invalid choose from " + valid_blood_groups);
            return false;
        }else if(city.isEmpty()){
            showMsg("Enter city");
            return false;
        }
        return true;
    }


    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}

/*
<EditText
            android:id="@+id/et_blood_group"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:layout_marginStart="20dp"
            android:hint="eg: A+"
            android:textColor="@android:color/black"
            android:textSize="24sp" />
 */