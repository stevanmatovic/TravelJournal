package com.example.traveljournal.viewmodel;

import android.app.Application;

import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.Document;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DocumentsViewModel extends AndroidViewModel {

    DataRepository repository;

    public DocumentsViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);

    }

    public LiveData<List<Document>> getDocumentsForTrip(Long tripId){
        return repository.getDocumentsForTrip(tripId);
    }

    public void insert(Document document) {
        repository.insert(document);
    }

    public void deleteById(Long tripId) {
        repository.deleteDocumentById(tripId);
    }
}
