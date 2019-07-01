package com.example.traveljournal.task;

import android.os.AsyncTask;

import com.example.traveljournal.dao.DocumentDao;

public class DeleteDocumentAsyncTask extends AsyncTask<Long,Void,Void> {

    private DocumentDao documentDao;

    public DeleteDocumentAsyncTask(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Override
    protected Void doInBackground(Long... documentIds) {
        documentDao.deleteTripById(documentIds[0]);
        return null;
    }
}
