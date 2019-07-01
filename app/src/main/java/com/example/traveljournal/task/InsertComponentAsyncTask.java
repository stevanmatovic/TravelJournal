package com.example.traveljournal.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dao.TextDao;
import com.example.traveljournal.dao.VisualDao;
import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.CheckListWithItems;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Visual;

import java.util.ArrayList;
import java.util.List;

public class InsertComponentAsyncTask extends AsyncTask<Component, Void, Long> {

    private TextDao textDao;
    private VisualDao visualDao;
    private CheckListWithItemsDao checklistDao;

    public InsertComponentAsyncTask(TextDao textDao, VisualDao visualDao, CheckListWithItemsDao checklistDao) {
        this.textDao = textDao;
        this.visualDao = visualDao;
        this.checklistDao = checklistDao;
    }

    @Override
    protected Long doInBackground(Component... components) {
        switch (components[0].getType()){
            case Component.TEXT:
                return textDao.insert((Text)components[0]);
            case Component.VISUAL:
                return visualDao.insert((Visual)components[0]);
            case Component.CHECKLIST:
                if(components[0] instanceof CheckList)
                    return checklistDao.insert((CheckList)components[0]);
                else if(components[0] instanceof CheckListWithItems){
                    CheckListWithItems checkListWithItems = (CheckListWithItems)components[0];
                    Long checklistId = checklistDao.insert(checkListWithItems.getCheckList());
                    List<CheckListItem> itemsToAdd = new ArrayList<>();
                    for(CheckListItem item: checkListWithItems.getItems()){
                        item.setId(null);
                        if(TextUtils.isEmpty(item.getName()))
                            continue;
                        item.setCheckListId(checklistId);
                        itemsToAdd.add(item);
                    }
                    checklistDao.insertItems(itemsToAdd);
                    return checklistId;
                }
        }
        return null;
    }
}

