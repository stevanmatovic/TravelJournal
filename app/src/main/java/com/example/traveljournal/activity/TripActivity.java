package com.example.traveljournal.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.traveljournal.R;
import com.example.traveljournal.adapter.TripFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class TripActivity extends AppCompatActivity {

    Long tripId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Bundle bundle = getIntent().getExtras();
        tripId = bundle.getLong("tripId");

        ViewPager viewPager = findViewById(R.id.viewpager);
        TripFragmentPagerAdapter adapter = new TripFragmentPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Long getTripId() {
        return tripId;
    }
}
