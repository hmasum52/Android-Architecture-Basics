package github.hmasum18.architecture.service.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import github.hmasum18.architecture.service.room.Doas.NoteDao;
import github.hmasum18.architecture.service.model.Note;

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

    //Room will create this Doa ..but for remote DB or api we hate to define it
    public abstract NoteDao noteDao(); ///by this room wil create the Doa  //for retrofit we do this by retrofit.create(Test.class)
}
