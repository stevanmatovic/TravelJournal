package com.example.traveljournal.dao;

import com.example.traveljournal.entity.Text;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TextDao {

    @Insert
    Long insert(Text text);

    @Delete
    void delete(Text text);

    @Update
    void update(Text text);

    @Query("SELECT * from text")
    LiveData<List<Text>> getAll();

    @Query("DELETE from text")
    void deleteAll();

    @Query("SELECT * from text where trip_id=:trip_id")
    LiveData<List<Text>> getTextsForTrip(final Long trip_id);

    @Query("SELECT * from text where id = :textId")
    Text findById(Long textId);
}
