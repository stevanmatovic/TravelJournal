package com.example.traveljournal.entity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity
        (foreignKeys = {
                @ForeignKey(
                        entity = CheckList.class,
                        parentColumns = "id",
                        childColumns = "checklist_id",
                        onDelete = ForeignKey.CASCADE
                )
        }, indices = {
                @Index(value = "checklist_id")
        })
public class CheckListItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String name;

    private boolean checked;

    @ColumnInfo(name = "checklist_id")
    public Long checkListId;

    private Date creationDate;

    public CheckListItem(String name, boolean checked, Long checkListId) {
        this.name = name;
        this.checked = checked;
        this.checkListId = checkListId;
        this.creationDate = new Date();
    }



    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(Long checkListId) {
        this.checkListId = checkListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
