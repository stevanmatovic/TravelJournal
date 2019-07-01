package com.example.traveljournal.entity;

import java.util.Date;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CheckListWithItems implements Component{

    @Embedded
    public CheckList checkList;

    @Relation(parentColumn = "id", entityColumn = "checklist_id")
    public List<CheckListItem> items;

    @Override
    public int getType() {
        return Component.CHECKLIST;
    }

    @Override
    public String getTitle() {
        return checkList.getTitle();
    }

    @Override
    public Date getCreationDate() {
        return checkList.getCreationDate();
    }

    @Override
    public Long getId() {
        return checkList.getId();
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public List<CheckListItem> getItems() {
        return items;
    }

    public void setItems(List<CheckListItem> items) {
        this.items = items;
    }
}
