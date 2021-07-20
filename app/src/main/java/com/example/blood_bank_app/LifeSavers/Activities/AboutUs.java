package com.example.blood_bank_app.LifeSavers.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.example.blood_bank_app.LifeSavers.MyAdapter;
import com.example.blood_bank_app.LifeSavers.MyModel;
import com.example.blood_bank_app.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AboutUs extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    //actionbar


    //Ui Views
    private ViewPager viewPager;
    private ArrayList<MyModel> modelArrayList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //init action bar


        //init UI views
        viewPager = findViewById(R.id.viewPager);
        loadCards();

        //set viewpager change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //ill just change the title of actionbar
                String title = modelArrayList.get(position).getTitle();


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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
                        drawerLayout = findViewById(R.id.drawerLayout);
                        drawerLayout.closeDrawers();
                        break;

                    case  R.id.nav_home:
                        Intent intent2 = new Intent(AboutUs.this, MainActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.feedback:
                        Intent intent21 = new Intent(AboutUs.this, Feedback.class);
                        startActivity(intent21);
                        break;
                    case R.id.logout:
                        Intent intent34= new Intent(AboutUs.this, LoginActivity.class);
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

    private void loadCards() {
        //init list
        modelArrayList = new ArrayList<>();

        //add items to list
        modelArrayList.add(new MyModel(
                "Ajay Raut",
                "Backend Developer, Database Manager",
                "21344",
                R.drawable.ajay,
                "https://www.linkedin.com/in/ajay-raut-1b324018b/",
                "https://instagram.com/ajay_raut_21"));

        modelArrayList.add(new MyModel(
                "Yash Rajput",
                "Front-end, Backend Developer, etc.",
                "21361",
                R.drawable.yash2,
                "https://www.linkedin.com/in/yash-rajput-8b642b18b",
                "https://instagram.com/yash.rajput__"));
        modelArrayList.add(new MyModel(
                "Sourav Kotkar",
                "Backend Developer, Database Manager",
                "21355",
                R.drawable.sourav,
                "https://www.linkedin.com/in/sourav-kotkar",
                "https://instagram.com/souravkotkar"));
        modelArrayList.add(new MyModel(
                "Shubham Chaudhary",
                "Front-end Developer and Tester",
                "21353",
                R.drawable.shubham,
                "https://www.linkedin.com/in/shubham-chaudhary-8684961a4",
                "https://instagram.com/shubham.c"));
        modelArrayList.add(new MyModel(
                "Samyak Samdariya",
                "UI/UX Designer, Front-end Developer",
                "21346",
                R.drawable.samyak,"https://www.linkedin.com/in/samyak-samdariya-24024a195/",
                "https://instagram.com/_samyak_29"));

        // seting up adapter..
        myAdapter = new MyAdapter(this, modelArrayList);
        // set adapter to view pager
        viewPager.setAdapter(myAdapter);
        //set default padding from left/right
        viewPager.setPadding(100, 0 , 100 , 0);

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

