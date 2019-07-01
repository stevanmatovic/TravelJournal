package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.entity.CheckListItem;

public class InsertChecklistItemAsyncTask extends AsyncTask<CheckListItem, Void, Void> {
    private CheckListWithItemsDao checkListWithItemsDao;

    public InsertChecklistItemAsyncTask(CheckListWithItemsDao checkListWithItemsDao) {
        this.checkListWithItemsDao = checkListWithItemsDao;
    }

    @Override
    protected Void  doInBackground(CheckListItem... items) {
        checkListWithItemsDao.insert(items[0]);
        return null;
    }
}