package github.hmasum18.architecture.service.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.service.room.Doas.NoteDao;
import github.hmasum18.architecture.service.room.NoteDataBase;
import github.hmasum18.architecture.service.model.Note;
import github.hmasum18.architecture.view.App;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class NoteRepo {
    public static final String TAG = "NoteRepository->";

    @Inject
    ExecutorService roomExecutorService;

    @Inject
    NoteDao noteDao; // to update insert and add data in this table

    @Inject
    public NoteRepo(){
    }

    public void insert(Note note) {
        roomExecutorService.execute(() -> {
            noteDao.insert(note);
            Log.d(TAG,"execute: "+note.getTitle()+" inserted successfully");
        });
    }

    public void update(Note note) {
        roomExecutorService.execute(() -> {
            noteDao.update(note);
            Log.d(TAG,"execute: "+note.getTitle()+" updated successfully");
        });
    }

    public void delete(Note note) {
        roomExecutorService.execute(() -> {
            noteDao.delete(note);
            Log.d(TAG,"execute: "+note.getTitle()+" deleted successfully");
        });
    }

    public void deleteAllNotes() {
        roomExecutorService.execute(() -> {
            noteDao.deleteAllNotes();
            Log.d(TAG,"execute: all notes deleted successfully");
        });
    }

    //Live data is observed in background thread
    public LiveData<List<Note>> getAllNotes() {
         //so now we get all the notes from the note_table with the help of noteDao
        Log.d(TAG,"getAllNotes: getting all notes");
        return noteDao.getAllNotes(); //we don't have to create asyn task for this as room automatically do observe live data background thread
    }

}
