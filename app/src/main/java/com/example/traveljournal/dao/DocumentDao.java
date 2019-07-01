package com.example.traveljournal.dao;


import com.example.traveljournal.entity.Document;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DocumentDao {

    @Insert
    Long insert(Document document);

    @Delete
    void delete(Document document);

    @Update
    void update(Document document);

    @Query("SELECT * from document where trip_id=:trip_id")
    LiveData<List<Document>> getDocumentsForTrip(final Long trip_id);

    @Query("DELETE FROM document")
    void deleteAll();

    @Query("DELETE FROM document where id = :documentId")
    void deleteTripById(Long documentId);
}
