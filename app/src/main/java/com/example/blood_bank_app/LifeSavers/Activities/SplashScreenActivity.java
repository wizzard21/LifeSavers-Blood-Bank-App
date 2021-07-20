package com.example.blood_bank_app.LifeSavers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blood_bank_app.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To hide Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        //Link SplashScreen to MainActivity
        Handler handler=new Handler();

        handler.postDelayed(new Runnable(){
            public void run(){
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                finish();
            }
        }, 3000);


        //Animation
        ImageView image=findViewById(R.id.imageView10);
        TextView textView=findViewById(R.id.textView2);

        Animation animate_down= AnimationUtils.loadAnimation(this,R.anim.slide_down);
        Animation animate_up=AnimationUtils.loadAnimation(this,R.anim.slide_up);

        image.startAnimation(animate_down);
        textView.startAnimation(animate_up);
    }
}