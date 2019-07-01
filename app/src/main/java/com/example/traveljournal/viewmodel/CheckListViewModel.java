package com.example.traveljournal.viewmodel;

import android.app.Application;

import com.example.traveljournal.database.DataRepository;
import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.CheckListWithItems;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CheckListViewModel extends AndroidViewModel {

    private DataRepository repository;

    public CheckListViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public LiveData<CheckListWithItems> getCheckListWithItemsForId(Long id){
        return repository.getChecklistById(id);
    }

    public void insert(CheckListItem item){
        repository.insert(item);
    }

    public void updateTitle(Long checklistId, String toString) {
        repository.updateCheckListTitle(checklistId,toString);
    }

    public Long insert(CheckList checkList) {
        return repository.insertCheckListSync(checkList);
    }

    public void deleteItem(CheckListItem clicked) {
        repository.deleteChecklistItem(clicked);
    }

    public void setChecked(Long id, boolean checked) {
        repository.updateCheckedForId(id,checked);
    }
}
