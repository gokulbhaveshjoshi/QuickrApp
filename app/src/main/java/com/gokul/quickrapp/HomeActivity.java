package com.gokul.quickrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.gokul.quickrapp.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUI();
    }

    private void setUI() {

        tabLayout= findViewById(R.id.tab);
        viewPager= findViewById(R.id.vpFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFrag(new Buy(), "BUY");
//        adapter.addFrag(new Buy(), "SELL");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("Home"));
//        tabLayout.addTab(tabLayout.newTab().setText("Upload"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}