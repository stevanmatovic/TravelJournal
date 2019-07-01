package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.DocumentDao;
import com.example.traveljournal.entity.Document;

public class InsertDocumentAsyncTask extends AsyncTask<Document, Void, Void> {
    private DocumentDao documentDao;

    public InsertDocumentAsyncTask(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Override
    protected Void  doInBackground(Document... documents) {
        documentDao.insert(documents[0]);
        return null;
    }
}
