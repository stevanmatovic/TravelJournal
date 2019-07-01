package com.example.traveljournal.dao;

import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.CheckListWithItems;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface CheckListWithItemsDao {

    @Transaction
    @Query("SELECT * from  checklist")
    LiveData<List<CheckListWithItems>> getAll();

    @Insert
    Long insert(CheckList checkList);

    @Delete
    void delete(CheckList checkList);

    @Query("DELETE from checklist where id = :checkListId")
    void deleteById(Long checkListId);

    @Update
    void update(CheckList checkList);

    @Query("UPDATE CheckList SET title = :title WHERE id = :checklistId")
    void updateTitle(Long checklistId, String title);

    @Insert
    void insertItems(List<CheckListItem> items);

    @Query("DELETE from CheckList")
    void deleteAll();

    @Query("DELETE from CheckListItem")
    void deleteAllItems();

    @Transaction
    @Query("SELECT * from checklist where trip_id=:trip_id")
    LiveData<List<CheckListWithItems>> getCheckListsForTrip(final Long trip_id);

    @Query("DELETE FROM CheckListItem where checklist_id =:checklistId")
    void deleteItemsForChecklist(Long checklistId);

    @Query("SELECT * from checklist where id = :id")
    LiveData<CheckListWithItems> findById(Long id);

    @Insert
    void insert(CheckListItem item);

    @Delete
    void deleteItem(CheckListItem checkListItem);

    @Update
    void update(CheckListItem[] checkListItems);

    @Query("UPDATE CheckListItem SET checked = :checked WHERE id = :checklistItemId")
    void updateChecked(Long checklistItemId, boolean checked);
}
