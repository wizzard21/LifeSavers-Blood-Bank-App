package com.example.blood_bank_app.LifeSavers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank_app.LifeSavers.Utils.EndPoints;
import com.example.blood_bank_app.LifeSavers.Utils.VolleySingleton;
import com.example.blood_bank_app.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEt, mobilenoEt, addressEt, cityEt, pincodeEt, emailEt, dobEt, diseasesEt, passwordEt, confirmpasswordEt;
    private AutoCompleteTextView stateTv, bloodgrpTv;
    //private RadioButton maleRb, femaleRb, otherRb;
    private RadioGroup genderRg;
    private CheckBox diseasesCb;
    private Button registerButton;
    private TextInputLayout diseasesTil;

    private DatePickerDialog datePicker;

    //private Calendar calendar;
    //private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String[] STATES = new String[]{
                "Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
                "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa",
                "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka",
                "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya",
                "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
                "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"
        };
        String[] BLOODGRPS = new String[]{
                "A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"
        };

        AutoCompleteTextView editText1 = findViewById(R.id.state);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STATES);
        editText1.setAdapter(adapter1);

        AutoCompleteTextView editText2 = findViewById(R.id.bloodgrp);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BLOODGRPS);
        editText2.setAdapter(adapter2);

        nameEt = findViewById(R.id.name);
        mobilenoEt = findViewById(R.id.mobileno);
        addressEt = findViewById(R.id.address);
        cityEt = findViewById(R.id.city);
        stateTv = findViewById(R.id.state);
        pincodeEt = findViewById(R.id.pincode);
        emailEt = findViewById(R.id.email);
        dobEt = findViewById(R.id.dob);
        bloodgrpTv = findViewById(R.id.bloodgrp);
        genderRg = findViewById(R.id.gender);
        diseasesCb = findViewById(R.id.diseases);
        diseasesTil = findViewById(R.id.textInputLayout12);
        diseasesEt = findViewById(R.id.diseases_et);
        passwordEt = findViewById(R.id.password);
        confirmpasswordEt = findViewById(R.id.confirmpassword);
        registerButton = findViewById(R.id.register_button);

        // Date Picker
        dobEt.setOnClickListener(dpClickListener);

        diseasesCb.setOnClickListener(cbClickListener);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, mobileno, address, city, state, pincode, email, dob, bloodgrp, gender, diseases, password, confirmpassword;
                int gender_index;
                boolean diseases_flag;

                name = nameEt.getText().toString();
                mobileno = mobilenoEt.getText().toString();
                address = addressEt.getText().toString();
                city = cityEt.getText().toString();
                state = stateTv.getText().toString();
                pincode = pincodeEt.getText().toString();
                email = emailEt.getText().toString();
                dob = dobEt.getText().toString();
                bloodgrp = bloodgrpTv.getText().toString();

                gender_index = genderRg.getCheckedRadioButtonId();
                if (gender_index==R.id.male) gender="M";
                else if (gender_index==R.id.female) gender="F";
                else gender="O";

                if (diseasesCb.isChecked()) diseases = "Cancer";
                else diseases = "";

                password = passwordEt.getText().toString();
                confirmpassword = confirmpasswordEt.getText().toString();

                showMessage(name+"\n"+mobileno+"\n"+address+"\n"+city+"\n"+state+"\n"+pincode+"\n"+email+
                        "\n"+dob+"\n"+bloodgrp+"\n"+gender_index+"\n"+gender+"\n"+diseases+"\n"+password+"\n"+confirmpassword);

                if (isValid(name, mobileno, address, city, state, pincode, email, dob, bloodgrp, gender, diseases, password, confirmpassword)){
                    register(name, mobileno, address, city, state, pincode, email, dob, bloodgrp, gender, diseases, password, confirmpassword);
                }
            }
        });
    }

    View.OnClickListener dpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            // Date Picker Listener
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    // Cases to display date as perfect yyyy/mm/dd format
                    if(month+1 < 10)
                    {
                        if(day < 10)
                            dobEt.setText(year + "-0" + (month+1) + "-0" + day);
                        else
                            dobEt.setText(year + "-0" + (month+1) + "-" + day);
                    }
                    else
                    {
                        if(day < 10)
                            dobEt.setText(year + "-" + (month+1) + "-0" + day);
                        else
                            dobEt.setText(year + "-" + (month+1) + "-"
                                    + day);
                    }
                }
            };

            // Date Picker Listener
            datePicker = new DatePickerDialog(RegisterActivity.this, listener, year, month, day);
            datePicker.show();
        }
    };

    View.OnClickListener cbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(diseasesCb.isChecked())
                diseasesTil.setVisibility(View.VISIBLE);
            else
            {
                diseasesTil.setVisibility(View.GONE);
                diseasesEt.setText("");
            }
        }
    };

    private boolean isValid(String name, String mobileno, String address, String city, String state,
                            String pincode, String email, String dob, String bloodgrp, String gender,
                            String diseases, String password, String confirmpassword){
        if (name.isEmpty()){
            showMessage("name is required");
            return false;
        }
        else if (mobileno.isEmpty()){
            showMessage("mobileno is required");
            return false;
        }
        else if (address.isEmpty()){
            showMessage("address is required");
            return false;
        }
        else if (city.isEmpty()){
            showMessage("city is required");
            return false;
        }
        else if (state.isEmpty()){
            showMessage("state is required");
            return false;
        }
        else if (pincode.isEmpty()){
            showMessage("pincode is required");
            return false;
        }
        else if (dob.isEmpty()){
            showMessage("dob is required");
            return false;
        }
        else if (bloodgrp.isEmpty()){
            showMessage("bloodgrp is required");
            return false;
        }
        else if (gender.isEmpty()){
            showMessage("gender is required");
            return false;
        }
        else if (password.isEmpty()){
            showMessage("password is required");
            return false;
        }
        else if (confirmpassword.isEmpty()){
            showMessage("confirmpassword is required");
            return false;
        }
        else if (confirmpassword.isEmpty()){
            showMessage("confirmpassword is required");
            return false;
        }
        else if (!password.equals(confirmpassword)){
            showMessage("Password does not match");
            return false;
        }
        return true;
    }

    private void register(String name, String mobileno, String address, String city, String state,
                          String pincode, String email, String dob, String bloodgrp, String gender,
                          String diseases, String password, String confirmpassword) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")){
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("mobileno", mobileno);
                params.put("address", address);
                params.put("city", city);
                params.put("state", state);
                params.put("pincode", pincode);
                params.put("email", email);
                params.put("dob", dob);
                params.put("bloodgrp", bloodgrp);
                params.put("gender", gender);
                params.put("diseases", diseases);
                params.put("password", password);
                params.put("confirmpassword", confirmpassword);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}