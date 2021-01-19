package github.hmasum18.architecture.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.RoomDataBase.Doas.NoteDao;
import github.hmasum18.architecture.RoomDataBase.NoteDataBase;
import github.hmasum18.architecture.model.Note;

import java.util.List;

public class NoteRepoWithThreadPool {
    public static final String TAG = "TestRepository->";
    private NoteDataBase noteDataBase;
    private NoteDao noteDao; // to update insert and add data in this table
    private LiveData<List<Note>> allNotes; // observe all the notes in the table

    public NoteRepoWithThreadPool(Application application) {
         noteDataBase = NoteDataBase.getInstance(application); // make the database or get existing
        noteDao = noteDataBase.noteDao(); // get the noteDao to access data from note_table of this database
        allNotes = noteDao.getAllNotes(); //so now we get all the notes from the note_table with the help of noteDao
    }

    public void insert(Note note) {
        noteDataBase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
                Log.d(TAG,"execute: "+note.getTitle()+" inserted successfully");
            }
        });
    }

    public void update(Note note) {
        noteDataBase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
                Log.d(TAG,"execute: "+note.getTitle()+" updated successfully");
            }
        });
    }

    public void delete(Note note) {
        noteDataBase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
                Log.d(TAG,"execute: "+note.getTitle()+" deleted successfully");
            }
        });
    }

    public void deleteAllNotes() {
        noteDataBase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteAllNotes();
                Log.d(TAG,"execute: all notes deleted successfully");
            }
        });
    }

    //Live data is observed in background thread
    public LiveData<List<Note>> getAllNotes() {
        Log.d(TAG,"getAllNotes: getting all notes");
        return allNotes; //we don't have to create asyn task for this as room automatically do observe live data background thread
    }

}
