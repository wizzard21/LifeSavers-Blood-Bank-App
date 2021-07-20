package com.example.blood_bank_app.LifeSavers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank_app.LifeSavers.Utils.EndPoints;
import com.example.blood_bank_app.LifeSavers.Utils.VolleySingleton;
import com.example.blood_bank_app.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText number_editText,password_editText;
    private Button login_button;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number_editText=findViewById(R.id.editTextPhone);
        password_editText=findViewById(R.id.editTextTextPassword);
        login_button=findViewById(R.id.button_login);
        register=findViewById(R.id.textView_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_editText.setError(null);
                password_editText.setError(null);
                String number=number_editText.getText().toString();
                String password=password_editText.getText().toString();

                if(isValid(number,password)){
                    login(number,password);
                }
            }
        });

    }

    private void login(String number,String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Success")){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                            .putString("number", number).apply();
                    LoginActivity.this.finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobileno", number);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean isValid(String number,String password){
        if(number.isEmpty()){
            showMessage("Empty Mobile Number");
            number_editText.setError("Empty Mobile NUmber");
            return false;
        }
        else if(password.isEmpty()){
            showMessage("Empty password");
            password_editText.setError("Empty password");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}