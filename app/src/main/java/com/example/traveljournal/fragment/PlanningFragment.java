package com.example.traveljournal.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.traveljournal.R;
import com.example.traveljournal.activity.NewChecklistActivity;
import com.example.traveljournal.activity.NewTextActivity;
import com.example.traveljournal.activity.NewVisualActivity;
import com.example.traveljournal.activity.TripActivity;
import com.example.traveljournal.adapter.ComponentGridAdapter;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Trip;
import com.example.traveljournal.viewmodel.PlanViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class PlanningFragment extends Fragment {

    private PlanViewModel planViewModel;
    private ComponentGridAdapter adapter;
    private Long tripId;
    private ListView listView;

    private boolean isFABOpen = false;
    FloatingActionButton fabText;
    FloatingActionButton fabVisual;
    FloatingActionButton fabChecklist;

    public PlanningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TripActivity parent = (TripActivity) getActivity();
        tripId = parent.getTripId();

        planViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);

        adapter = new ComponentGridAdapter(new ArrayList<Component>());
        planViewModel.getComponentsForTrip(tripId).observe(this, new Observer<List<Component>>() {
            @Override
            public void onChanged(List<Component> components) {
                adapter.setComponentList(components);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planning, container, false);
        listView = view.findViewById(R.id.list_view);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(onComponentClickListener);
        listView.setAdapter(adapter);


        setFABListeners(view);
        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getTitle().toString()){
            case "Delete":
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Component clicked = (Component) adapter.getItem(info.position);
                planViewModel.deleteComponent(clicked);
                return true;
            default:
                return super.onContextItemSelected(item);


        }
    }

    private AdapterView.OnItemClickListener onComponentClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Component clicked = (Component) adapter.getItem(position);
            Intent intent;
            switch (clicked.getType()){
                case Component.TEXT:
                    intent = new Intent(getContext(), NewTextActivity.class);
                    intent.putExtra("tripId",tripId);
                    intent.putExtra("textId",clicked.getId());
                    startActivity(intent);
                    break;
                case Component.VISUAL:
                    intent = new Intent(getContext(), NewVisualActivity.class);
                    intent.putExtra("tripId",tripId);
                    intent.putExtra("visualId",clicked.getId());
                    startActivity(intent);
                    break;
                case Component.CHECKLIST:
                    intent = new Intent(getContext(), NewChecklistActivity.class);
                    intent.putExtra("tripId",tripId);
                    intent.putExtra("checklistId",clicked.getId());
                    startActivity(intent);
                    break;
            }
        }
    };


    private void setFABListeners(View view) {

        FloatingActionButton fab = view.findViewById(R.id.fab_new_component);
        fabText = view.findViewById(R.id.fab_new_text);
        fabVisual = view.findViewById(R.id.fab_new_visual);
        fabChecklist = view.findViewById(R.id.fab_new_checklist);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFABOpen){
                    closeFABMenu();
                }else{
                    openFABMenu();
                }
            }
        });
        fabText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewTextActivity.class);
                intent.putExtra("tripId",tripId);
                startActivity(intent);
            }
        });
        fabVisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewVisualActivity.class);
                intent.putExtra("tripId",tripId);
                startActivity(intent);
            }
        });
        fabChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewChecklistActivity.class);
                intent.putExtra("tripId",tripId);
                startActivity(intent);
            }
        });
    }

    private void openFABMenu(){
        isFABOpen=true;
        fabText.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabChecklist.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fabVisual.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabText.animate().translationY(0);
        fabChecklist.animate().translationY(0);
        fabVisual.animate().translationY(0);
    }

}
