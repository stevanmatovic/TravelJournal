package com.example.traveljournal.viewmodel;

import android.app.Application;

import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PlanViewModel extends AndroidViewModel {

    DataRepository repository;

    public PlanViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public LiveData<List<Component>> getComponentsForTrip(Long id){
        return repository.getComponentsForTrip(id);
    }

    public void deleteComponent(Component component){
        repository.deleteComponent(component);
    }
}
