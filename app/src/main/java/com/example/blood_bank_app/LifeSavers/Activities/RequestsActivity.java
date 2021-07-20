package com.example.blood_bank_app.LifeSavers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank_app.LifeSavers.Adapters.RequestAdapter;
import com.example.blood_bank_app.LifeSavers.Adapters.SearchAdapter;
import com.example.blood_bank_app.LifeSavers.DataModels.Donor;
import com.example.blood_bank_app.LifeSavers.DataModels.RequestDataModel;
import com.example.blood_bank_app.LifeSavers.Utils.EndPoints;
import com.example.blood_bank_app.LifeSavers.Utils.VolleySingleton;
import com.example.blood_bank_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestsActivity extends AppCompatActivity {
    String number;
    public static final int PERMISSION_REQUEST_CODE=1;
    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Toolbar toolbar = findViewById(R.id.toolbar_requests);

        requestDataModels = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_Requests);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        requestAdapter = new RequestAdapter(requestDataModels, this);
        recyclerView.setAdapter(requestAdapter);
        populatePage();
        requestAdapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RequestDataModel requestDataModel) {
                number=requestDataModel.getPhno();
                makeCall();
            }
        });
    }

    private void populatePage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.get_requests_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<RequestDataModel>>(){}.getType();
                List<RequestDataModel> dataModels=gson.fromJson(response,type);
                requestDataModels.addAll(dataModels);
                requestAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RequestsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        } else {
            requestPermission();
        }
    }

    private  void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(RequestsActivity.this,Manifest.permission.CALL_PHONE)){}
        else {
            ActivityCompat.requestPermissions(RequestsActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                }
                break;
        }
    }
}