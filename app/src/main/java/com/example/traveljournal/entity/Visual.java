package com.example.traveljournal.entity;

import java.util.Date;

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
public class Visual implements Component {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String path;

    @ColumnInfo(name = "trip_id")
    private Long tripId;

    private Date creationDate;

    private String title;


    public Visual(String title, String path, Long tripId) {
        this.path = path;
        this.title = title;
        this.tripId = tripId;
        this.creationDate = new Date();
    }

    @Override
    public int getType() {
        return Component.VISUAL;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
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

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
