package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.TripDao;
import com.example.traveljournal.entity.Trip;

public class InsertTripAsyncTask extends AsyncTask<Trip, Void, Void> {
    private TripDao tripDao;

    public InsertTripAsyncTask(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    protected Void  doInBackground(Trip... trips) {
        tripDao.insert(trips[0]);
        return null;
    }
}
