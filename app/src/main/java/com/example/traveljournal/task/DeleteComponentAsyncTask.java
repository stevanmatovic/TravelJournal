package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dao.TextDao;
import com.example.traveljournal.dao.VisualDao;
import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListWithItems;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Visual;

public class DeleteComponentAsyncTask extends AsyncTask<Component, Void, Void> {

    private TextDao textDao;
    private VisualDao visualDao;
    private CheckListWithItemsDao checklistDao;

    public DeleteComponentAsyncTask(TextDao textDao, VisualDao visualDao, CheckListWithItemsDao checklistDao) {
        this.textDao = textDao;
        this.visualDao = visualDao;
        this.checklistDao = checklistDao;
    }

    @Override
    protected Void doInBackground(Component... components) {
        switch (components[0].getType()){
            case Component.TEXT:
                textDao.delete((Text)components[0]);
                break;
            case Component.VISUAL:
                visualDao.delete((Visual)components[0]);
                break;
            case Component.CHECKLIST:
                if(components[0] instanceof CheckList)
                    checklistDao.delete((CheckList)components[0]);
                else if(components[0] instanceof CheckListWithItems){
                    CheckListWithItems checkListWithItems = (CheckListWithItems) components[0];
                    checklistDao.delete(checkListWithItems.getCheckList());

                }
                break;
        }
        return null;
    }
}