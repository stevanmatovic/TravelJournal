package com.example.traveljournal.entity;

import java.util.Date;

public interface Component {

    int TEXT = 0;
    int VISUAL = 1;
    int CHECKLIST = 2;

    int getType();
    String getTitle();
    Date getCreationDate();
    Long getId();
}
