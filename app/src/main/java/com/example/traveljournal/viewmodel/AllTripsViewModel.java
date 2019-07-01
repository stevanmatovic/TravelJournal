package com.example.traveljournal.viewmodel;

import android.app.Application;

import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Trip;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AllTripsViewModel extends AndroidViewModel {

    DataRepository repository;
    private LiveData<List<Trip>> allTrips;

    public AllTripsViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allTrips = repository.getAllTrips();
    }

    public LiveData<List<Trip>> getAllTrips(){
        return allTrips;
    }


    public void deleteById(Long tripId) {
        repository.deleteTripById(tripId);
    }
}
