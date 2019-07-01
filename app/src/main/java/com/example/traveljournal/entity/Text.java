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
public class Text implements Component {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String text;

    @ColumnInfo(name = "trip_id")
    public Long tripId;

    private String title;

    private Date creationDate;

    public Text(String title, String text, Long tripId) {
        this.title = title;
        this.text = text;
        this.tripId = tripId;
        this.creationDate = new Date();
    }

    @Override
    public int getType() {
        return Component.TEXT;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
