package com.example.blood_bank_app.LifeSavers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.blood_bank_app.R;
import com.google.android.material.navigation.NavigationView;

public class Profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.menu_tv);
        String number = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("number", "Not Logged In");
        text.setText(number);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.aboutus:
                        Intent intent2 = new Intent(Profile.this, AboutUs.class);
                        startActivity(intent2);
                        break;

                    case  R.id.nav_home:
                        Intent intent22 = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent22);
                        break;
                    case R.id.feedback:
                        Intent intent23 = new Intent(Profile.this, Feedback.class);
                        startActivity(intent23);
                        break;
                    case R.id.logout:
                        Intent intent34= new Intent(Profile.this, LoginActivity.class);
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                .putString("number", "Not Logged In").apply();
                        String number = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                .getString("number", "Not Logged In");
                        text.setText(number);
                        //text.setText("Not Logged In");
                        startActivity(intent34);
                        break;


                    case  R.id.nav_Policy:{

                        Intent browserIntent  = new Intent(Intent.ACTION_VIEW , Uri.parse("https://yashrajputishu.wixsite.com/bloodbank"));
                        startActivity(browserIntent);

                    }
                    break;
                    case  R.id.nav_share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                }
                return false;
            }
        });
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("");
        actionBarDrawerToggle.syncState();

    }
}