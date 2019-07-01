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
public class CheckList implements Component {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    @ColumnInfo(name = "trip_id")
    public Long tripId;

    private String title;

    private Date creationDate;

    public CheckList(String title,Long tripId) {
        this.tripId = tripId;
        this.title = title;
        this.creationDate = new Date();
    }

    @Override
    public int getType() {
        return Component.CHECKLIST;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @NonNull
    @Override
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
