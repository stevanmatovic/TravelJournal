package com.example.traveljournal.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.traveljournal.R;
import com.example.traveljournal.adapter.ComponentGridAdapter;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.viewmodel.PlanViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class PlanActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private PlanViewModel planViewModel;
    private ComponentGridAdapter adapter;

    private boolean isFABOpen = false;
    FloatingActionButton fabText;
    FloatingActionButton fabVisual;
    FloatingActionButton fabChecklist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        planViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);

        adapter = new ComponentGridAdapter(new ArrayList<Component>());
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //TODO set plan id
        planViewModel.getComponentsForTrip(1L).observe(this, new Observer<List<Component>>() {
            @Override
            public void onChanged(List<Component> components) {
                adapter.setComponentList(components);
            }
        });

    }


}
