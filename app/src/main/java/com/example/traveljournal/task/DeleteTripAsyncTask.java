package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.TripDao;

public class DeleteTripAsyncTask extends AsyncTask<Long,Void,Void> {

    private TripDao tripDao;

    public DeleteTripAsyncTask(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    protected Void doInBackground(Long... tripIds) {
        tripDao.deleteTripById(tripIds[0]);
        return null;
    }
}