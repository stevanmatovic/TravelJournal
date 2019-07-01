package com.example.traveljournal.dao;

import com.example.traveljournal.entity.Trip;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TripDao {

    @Insert
    Long insert(Trip trip);

    @Delete
    void delete(Trip trip);

    @Update
    void update(Trip trip);

    @Query("SELECT * from trip")
    LiveData<List<Trip>> getAll();

    @Query("DELETE from trip")
    void deleteAll();

    @Query("SELECT * FROM trip WHERE id=:id ")
    LiveData<Trip> get(Long id);

    @Query("UPDATE trip SET memoriesText = :memories WHERE id = :tripId")
    int updateMemoriesForTrip(long tripId, String memories);

    @Query("DELETE from trip where id = :tripId")
    void deleteTripById(Long tripId);
}
