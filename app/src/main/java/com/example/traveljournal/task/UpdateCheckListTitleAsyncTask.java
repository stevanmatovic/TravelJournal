package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dto.UpdateCheckListTitleDto;

public class UpdateCheckListTitleAsyncTask extends AsyncTask<UpdateCheckListTitleDto,Void,Void> {

    private CheckListWithItemsDao dao;

    public UpdateCheckListTitleAsyncTask(CheckListWithItemsDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(UpdateCheckListTitleDto... updateCheckListTitleDtos) {
        UpdateCheckListTitleDto dto = updateCheckListTitleDtos[0];
        dao.updateTitle(dto.getId(),dto.getTitle());
        return null;
    }
}