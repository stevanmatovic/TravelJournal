package com.example.traveljournal.database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.traveljournal.dao.DocumentDao;
import com.example.traveljournal.entity.Document;
import com.example.traveljournal.utils.DateConverter;
import com.example.traveljournal.dao.CheckListWithItemsDao;
import com.example.traveljournal.dao.TextDao;
import com.example.traveljournal.dao.TripDao;
import com.example.traveljournal.dao.VisualDao;
import com.example.traveljournal.entity.CheckList;
import com.example.traveljournal.entity.CheckListItem;
import com.example.traveljournal.entity.Text;
import com.example.traveljournal.entity.Trip;
import com.example.traveljournal.entity.Visual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Text.class, Visual.class, CheckList.class, CheckListItem.class, Trip.class, Document.class},version = 1)
@TypeConverters({DateConverter.class})
public abstract class TravelJournalDatabase extends RoomDatabase {

    public abstract TextDao textDao();
    public abstract VisualDao visualDao();
    public abstract CheckListWithItemsDao checklistDao();
    public abstract TripDao tripDao();
    public abstract DocumentDao documentDao();

    private static volatile TravelJournalDatabase INSTANCE;

    static TravelJournalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TravelJournalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TravelJournalDatabase.class, "travel_journal_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TextDao textDao;
        private final VisualDao visualDao;
        private final CheckListWithItemsDao checklistDao;
        private final TripDao tripDao;
        private final DocumentDao documentDao;

        PopulateDbAsync(TravelJournalDatabase db) {
            textDao = db.textDao();
            visualDao = db.visualDao();
            checklistDao = db.checklistDao();
            tripDao = db.tripDao();
            documentDao = db.documentDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            visualDao.deleteAll();
            textDao.deleteAll();
            checklistDao.deleteAllItems();
            checklistDao.deleteAll();
            documentDao.deleteAll();
            tripDao.deleteAll();
            Trip t1 = new Trip("Paris",new Date(119,8,4),new Date(119,8,14));
            t1.setId(1L);
            Trip t3 = new Trip("Belgrade",new Date(119,9,4),new Date(119,9,14));
            t3.setId(3L);
            t1.setMemoriesText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut at elit vel ante pellentesque elementum vel sed ex. Praesent sit amet nulla sagittis, feugiat magna interdum, ultricies ligula. Mauris non ante diam. Curabitur condimentum lorem at leo imperdiet tempus. Nullam viverra neque vitae dolor posuere, eu scelerisque arcu rhoncus. Suspendisse eleifend nulla id aliquam tempor. In vulputate fermentum erat.");
            tripDao.insert(t1);
            tripDao.insert(t3);
            Text text1 = new Text("Consectetur adipiscing elit","Vestibulum posuere nibh ante, quis blandit nisl tempus non. Vestibulum consequat mi non diam maximus congue. Cras sagittis diam ac tellus tempus finibus. Morbi ut luctus lectus, quis mollis nisl.",1L);
            t1.setId(49L);
            textDao.insert(text1);
            textDao.insert(new Text("Integer fermentum rutrum","Suspendisse quis feugiat lorem, non viverra massa. Integer non aliquam nibh. Integer fermentum rutrum neque ac consequat. Proin ultrices ac neque interdum condimentum.",1L));
            textDao.insert(new Text("Nunc quis elementum","Etiam dapibus imperdiet ullamcorper. Nulla facilisi. Nunc vehicula erat nisl, ac eleifend odio interdum vitae. Suspendisse ultricies mi id felis molestie venenatis.",1L));

            CheckList checkList = new CheckList("Sights",1L);
            checkList.setId(2L);
            List<CheckListItem> items = new ArrayList<>();
            items.add(new CheckListItem("Eiffel Tower",true,2L));
            items.add(new CheckListItem("Louvre",false,2L));
            items.add(new CheckListItem("Arc de triomphe",true,2L));
            checklistDao.insert(checkList);
            checklistDao.insertItems(items);

            return null;
        }
    }

}
