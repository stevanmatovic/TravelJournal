package com.example.traveljournal.viewmodel;

import android.app.Application;

import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Trip;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MemoriesViewModel extends AndroidViewModel {

    DataRepository repository;

    public MemoriesViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public LiveData<Trip> getTripById(Long id){
        return repository.getTripById(id);
    }

    public void updateMemoriesForTrip(Long tripId,String memories){
        repository.updateMemoriesForTrip(tripId,memories);
    }

}
