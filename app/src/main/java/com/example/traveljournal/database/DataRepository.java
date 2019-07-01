package com.example.traveljournal.database;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dao.DocumentDao;
import com.example.traveljournal.dao.TextDao;
import com.example.traveljournal.dao.TripDao;
import com.example.traveljournal.dao.VisualDao;
import com.example.traveljournal.dto.UpdateCheckListTitleDto;
import com.example.traveljournal.task.DeleteChecklistItemAsyncTask;
import com.example.traveljournal.task.DeleteComponentAsyncTask;
import com.example.traveljournal.task.DeleteDocumentAsyncTask;
import com.example.traveljournal.task.DeleteTripAsyncTask;
import com.example.traveljournal.task.InsertChecklistItemAsyncTask;
import com.example.traveljournal.task.InsertComponentAsyncTask;
import com.example.traveljournal.task.InsertDocumentAsyncTask;
import com.example.traveljournal.task.InsertTripAsyncTask;
import com.example.traveljournal.task.UpdateCheckListTitleAsyncTask;
import com.example.traveljournal.task.UpdateCheckedAsyncTask;
import com.example.traveljournal.dto.UpdateCheckedDto;
import com.example.traveljournal.dto.UpdateMemoriesDto;
import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.CheckListWithItems;
import com.example.traveljournal.entity.Component;
import com.example.traveljournal.entity.Document;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Trip;
import com.example.traveljournal.entity.Visual;
import com.example.traveljournal.task.UpdateComponentAsyncTask;
import com.example.traveljournal.task.UpdateMemoriesAsyncTask;
import com.example.traveljournal.utils.DataSourceMerger;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {

    private TextDao textDao;
    private VisualDao visualDao;
    private CheckListWithItemsDao checklistDao;
    private TripDao tripDao;
    private DocumentDao documentDao;

    public DataRepository(Application application){

        TravelJournalDatabase db = TravelJournalDatabase.getDatabase(application);
        textDao = db.textDao();
        visualDao = db.visualDao();
        checklistDao = db.checklistDao();
        tripDao = db.tripDao();
        documentDao = db.documentDao();
    }

    public TextDao getTextDao() {
        return textDao;
    }

    public LiveData<List<Document>> getDocumentsForTrip(Long tripId){
        return documentDao.getDocumentsForTrip(tripId);
    }

    public LiveData<List<Component>> getComponentsForTrip(Long tripId){
        return DataSourceMerger.mergeDataSources(textDao.getTextsForTrip(tripId),visualDao.getVisualsForTrip(tripId),checklistDao.getCheckListsForTrip(tripId));
    }

    public LiveData<List<Trip>> getAllTrips(){
        return tripDao.getAll();
    }

    public void insert(Trip trip){
        new InsertTripAsyncTask(tripDao).execute(trip);
    }

    public Long insertCheckListSync(CheckList checkList){
        return checklistDao.insert(checkList);
    }

    public void insert(Document document){
        new InsertDocumentAsyncTask(documentDao).execute(document);
    }

    public void insert (Component component) {
        new InsertComponentAsyncTask(textDao,visualDao,checklistDao).execute(component);
    }

    public LiveData<Trip> getTripById(Long id) {
        return tripDao.get(id);
    }

    public void updateMemoriesForTrip(Long tripId, String memories) {
        new UpdateMemoriesAsyncTask(tripDao).execute(new UpdateMemoriesDto(tripId,memories));
    }

    public void deleteComponent(Component component) {
        new DeleteComponentAsyncTask(textDao,visualDao,checklistDao).execute(component);
    }

    public void deleteTripById(Long tripId) {
        new DeleteTripAsyncTask(tripDao).execute(tripId);
    }

    public void deleteDocumentById(Long tripId) {
        new DeleteDocumentAsyncTask(documentDao).execute(tripId);
    }

    public Text getTextById(Long textId) {
        return textDao.findById(textId);
    }

    public void update(Component component) {
        new UpdateComponentAsyncTask(textDao,visualDao,checklistDao).execute(component);
    }

    public Visual getVisualById(Long id) {
        return visualDao.findById(id);
    }

    public LiveData<CheckListWithItems> getChecklistById(Long id) {
        return checklistDao.findById(id);
    }

    public void insert(CheckListItem item) {
        new InsertChecklistItemAsyncTask(checklistDao).execute(item);
    }

    public void updateCheckListTitle(Long checklistId, String title) {
        new UpdateCheckListTitleAsyncTask(checklistDao).execute(new UpdateCheckListTitleDto(title,checklistId));
    }

    public void deleteChecklistItem(CheckListItem item) {
        new DeleteChecklistItemAsyncTask(checklistDao).execute(item);
    }

    public void updateCheckedForId(Long id, boolean checked) {
        new UpdateCheckedAsyncTask(checklistDao).execute(new UpdateCheckedDto(checked,id));
    }
}
