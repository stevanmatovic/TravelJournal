package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dao.TextDao;
import com.example.traveljournal.dao.VisualDao;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Visual;

public class UpdateComponentAsyncTask extends AsyncTask<Component, Void, Void> {

    private TextDao textDao;
    private VisualDao visualDao;
    private CheckListWithItemsDao checklistDao;

    public UpdateComponentAsyncTask(TextDao textDao, VisualDao visualDao, CheckListWithItemsDao checklistDao) {
        this.textDao = textDao;
        this.visualDao = visualDao;
        this.checklistDao = checklistDao;
    }

    @Override
    protected Void doInBackground(Component... components) {
        switch (components[0].getType()){
            case Component.TEXT:
                textDao.update((Text)components[0]);
                break;
            case Component.VISUAL:
                visualDao.update((Visual)components[0]);
                break;
        }
        return null;
    }
}