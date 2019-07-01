package com.example.traveljournal.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
        (foreignKeys = {
                @ForeignKey(
                        entity = Trip.class,
                        parentColumns = "id",
                        childColumns = "trip_id",
                        onDelete = ForeignKey.CASCADE
                )
        }, indices = {
                @Index(value = "trip_id")
        })
public class Document {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String path;

    private String name;

    @ColumnInfo(name = "trip_id")
    public Long tripId;

    public Document(String path,String name, Long tripId) {
        this.name = name;
        this.path = path;
        this.tripId = tripId;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
