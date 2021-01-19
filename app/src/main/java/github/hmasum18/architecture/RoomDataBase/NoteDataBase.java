package github.hmasum18.architecture.RoomDataBase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import github.hmasum18.architecture.RoomDataBase.Doas.NoteDao;
import github.hmasum18.architecture.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * step 3
 * this singleton creates the sqLite database that we need
 * and we creates the tables we want to create
 */
@Database(entities = {Note.class} , version = 1,exportSchema = false)
public abstract class NoteDataBase extends RoomDatabase {

    public static final String TAG = "NoteDataBase :";
    private static  NoteDataBase noteDataBase;
    /**
     * allowing maximum five background threads only
     */
    public ExecutorService executorService = Executors.newFixedThreadPool(5);


    //Room will create this Doa ..but for remote DB or api we hate to define it
    public abstract NoteDao noteDao(); ///by this room wil create the Doa  //for retrofit we do this by retrofit.create(Test.class)


    public static synchronized NoteDataBase getInstance(Context context) {
        if(noteDataBase == null) {
            Log.w(TAG, "note database created");
            noteDataBase = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class , "note_database")
                    .fallbackToDestructiveMigration() ///delete existing database
                    .addCallback(roomCallBack) //will call our callback when db is created and populate the table
                    .build();
        }
        Log.w(TAG, "note database returned");
        return  noteDataBase;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.w(TAG,"room database on create");
            new PopulateDatabaseAsynTask(noteDataBase).execute();
        }
    };

    private static class PopulateDatabaseAsynTask extends AsyncTask<Void,Void , Void> {
        private NoteDao noteDao ;

        private PopulateDatabaseAsynTask(NoteDataBase db){
            noteDao = db.noteDao(); // get the Doa of note_table to insert data
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1 ", "Description 1 ", 1));
            noteDao.insert(new Note("Title2 ", "Description 2 ", 2));
            noteDao.insert(new Note("Title3 ", "Description 3 ", 3));
            Log.w(TAG,"data inserted succesfuly");
            return null;
        }
    }

}
