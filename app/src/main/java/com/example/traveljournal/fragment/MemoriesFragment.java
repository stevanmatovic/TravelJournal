package com.example.traveljournal.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Scroller;

import com.example.traveljournal.R;
import com.example.traveljournal.activity.TripActivity;
import com.example.traveljournal.entity.Trip;
import com.example.traveljournal.viewmodel.MemoriesViewModel;


public class MemoriesFragment extends Fragment {

    private Long tripId;
    private MemoriesViewModel memoriesViewModel;
    private EditText memoriesET;
    public MemoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TripActivity parent = (TripActivity) getActivity();
        tripId = parent.getTripId();

        memoriesViewModel = ViewModelProviders.of(getActivity()).get(MemoriesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memories, container, false);
        this.memoriesET = view.findViewById(R.id.memories_edit_text);
        memoriesET.setScroller(new Scroller(getActivity()));
        memoriesET.setVerticalScrollBarEnabled(true);
        memoriesET.setMovementMethod(new ScrollingMovementMethod());

        memoriesViewModel.getTripById(tripId).observe(getActivity(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                if (trip != null && MemoriesFragment.this.memoriesET != null){

                    MemoriesFragment.this.memoriesET.setText(trip.getMemoriesText());
                }
            }
        });

        memoriesET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    memoriesViewModel.updateMemoriesForTrip(tripId,memoriesET.getText().toString());
                }
            }
        });

        return view;
    }

}
