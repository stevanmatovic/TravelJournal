package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dto.UpdateCheckedDto;

public class UpdateCheckedAsyncTask extends AsyncTask<UpdateCheckedDto,Void,Void> {

    private CheckListWithItemsDao dao;

    public UpdateCheckedAsyncTask(CheckListWithItemsDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(UpdateCheckedDto... updateCheckedDtos) {
        UpdateCheckedDto dto = updateCheckedDtos[0];
        dao.updateChecked(dto.getId(),dto.isChecked());
        return null;
    }
}
