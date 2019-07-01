package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.TripDao;
import com.example.traveljournal.dto.UpdateMemoriesDto;

public class UpdateMemoriesAsyncTask extends AsyncTask<UpdateMemoriesDto, Void, Void> {

    private TripDao tripDao;

    public UpdateMemoriesAsyncTask(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    protected Void doInBackground(UpdateMemoriesDto... updateMemoriesDtos) {
        UpdateMemoriesDto dto = updateMemoriesDtos[0];
        tripDao.updateMemoriesForTrip(dto.getTripId(), dto.getMemories());
        return null;
    }
}
