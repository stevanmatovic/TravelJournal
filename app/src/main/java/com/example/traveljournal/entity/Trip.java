package com.example.traveljournal.entity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Trip {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private String imagePath;

    private String memoriesText;

    public Trip() {
    }

    public Trip(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMemoriesText() {
        return memoriesText;
    }

    public void setMemoriesText(String memoriesText) {
        this.memoriesText = memoriesText;
    }
}
