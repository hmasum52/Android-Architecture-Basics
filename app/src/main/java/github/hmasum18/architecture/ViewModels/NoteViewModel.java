package github.hmasum18.architecture.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.Repository.NoteRepoWithAsyncTask;
import github.hmasum18.architecture.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepoWithAsyncTask noteRepoWithAsyncTask;

    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepoWithAsyncTask = new NoteRepoWithAsyncTask(application);
        allNotes = noteRepoWithAsyncTask.getAllNotes();
    }


    public void insert(Note note) {
        noteRepoWithAsyncTask.insert(note);
    }

    public void update(Note note) {
        noteRepoWithAsyncTask.update(note);
    }

    public void delete(Note note) {
        noteRepoWithAsyncTask.delete(note);
    }

    public void deleteAllNotes() {
        noteRepoWithAsyncTask.deleteAllnotes();
    }

    //Live data is observed in background thread
    public LiveData<List<Note>> getAllNotes() {
        return allNotes; //we don't have to create asyn task for this as room automatically do observe live data background thread
    }
}
