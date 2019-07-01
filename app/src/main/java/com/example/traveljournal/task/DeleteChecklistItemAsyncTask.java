package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.entity.CheckListItem;

public class DeleteChecklistItemAsyncTask extends AsyncTask<CheckListItem,Void,Void> {

    private CheckListWithItemsDao dao;

    public DeleteChecklistItemAsyncTask(CheckListWithItemsDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(CheckListItem... checkListItems) {
        dao.deleteItem(checkListItems[0]);
        return null;
    }
}