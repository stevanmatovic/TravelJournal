package com.example.traveljournal.dao;

import com.example.traveljournal.entity.Visual;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface VisualDao {

    @Insert
    Long insert(Visual visual);

    @Delete
    void delete(Visual visual);

    @Update
    void update(Visual visual);

    @Query("SELECT * from visual")
    LiveData<List<Visual>> getAll();

    @Query("DELETE from visual")
    void deleteAll();

    @Query("SELECT * from visual where trip_id=:trip_id")
    LiveData<List<Visual>> getVisualsForTrip(final Long trip_id);

    @Query("SELECT * from visual where id = :id")
    Visual findById(Long id);
}
