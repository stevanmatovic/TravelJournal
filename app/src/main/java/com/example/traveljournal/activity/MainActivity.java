package com.example.traveljournal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.traveljournal.R;
import com.example.traveljournal.adapter.TripListAdapter;
import com.example.traveljournal.entity.Trip;
import com.example.traveljournal.viewmodel.AllTripsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AllTripsViewModel tripsViewModel;
    private RecyclerView recyclerView;
    private TripListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripsViewModel = ViewModelProviders.of(this).get(AllTripsViewModel.class);
        recyclerView = findViewById(R.id.all_trips_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerForContextMenu(recyclerView);

        adapter = new TripListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        tripsViewModel.getAllTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                adapter.setTrips(trips);
            }

        });

        FloatingActionButton fab = findViewById(R.id.fab_new_trip);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTripActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = adapter.getContextMenuPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        Long tripId = adapter.getItemId(position);
        tripsViewModel.deleteById(tripId);
        return super.onContextItemSelected(item);
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            TripListAdapter.TripViewHolder vh = (TripListAdapter.TripViewHolder) v.getTag();
            Long tripId = vh.getTripId();

            Intent i = new Intent(MainActivity.this,TripActivity.class);
            i.putExtra("tripId",tripId);
            startActivity(i);
        }
    };


}
