package com.example.traveljournal.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.traveljournal.R;
import com.example.traveljournal.entity.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    private List<Trip> trips;
    private View.OnClickListener onItemClickListener;
    int contextMenuPosition;


    public class TripViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView name;
        public TextView dates;
        private Long tripId;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trip_card_title);
            dates = itemView.findViewById(R.id.trip_card_dates);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            itemView.setOnCreateContextMenuListener(this);
        }

        public Long getTripId() {
            return tripId;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add("Delete");
        }
    }

    public TripListAdapter() {
        this.trips = new ArrayList<>();
    }

    public int getContextMenuPosition() {
        return contextMenuPosition;
    }

    public void setContextMenuPosition(int contextMenuPosition) {
        this.contextMenuPosition = contextMenuPosition;
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list_item,parent,false);
        TripViewHolder vh = new TripViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TripViewHolder holder, int position) {
        Trip t = trips.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        holder.dates.setText(dateFormat.format(t.getStartDate()) + " - " + dateFormat.format(t.getEndDate()));
        holder.name.setText(t.getName());
        holder.tripId = t.getId();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setContextMenuPosition(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public void onViewRecycled(@NonNull TripViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(trips == null)
            return 0;
        return trips.size();
    }

    public void setTrips(List<Trip> trips){
        this.trips = trips;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return trips.get(position).getId();
    }
}
