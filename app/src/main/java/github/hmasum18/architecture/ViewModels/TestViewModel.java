package github.hmasum18.architecture.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.Repository.NoteRepoWithThreadPool;
import github.hmasum18.architecture.model.Note;

import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private NoteRepoWithThreadPool noteRepoWithThreadPool;

    private LiveData<List<Note>> allNotes;

    public TestViewModel(@NonNull Application application) {
        super(application);
        noteRepoWithThreadPool = new NoteRepoWithThreadPool(application);
        allNotes = noteRepoWithThreadPool.getAllNotes();
    }

    public void insert(Note note) {
        noteRepoWithThreadPool.insert(note);
    }

    public void update(Note note) {
        noteRepoWithThreadPool.update(note);
    }

    public void delete(Note note) {
        noteRepoWithThreadPool.delete(note);
    }

    public void deleteAllNotes() {
        noteRepoWithThreadPool.deleteAllNotes();
    }

    //Live data is observed in background thread
    public LiveData<List<Note>> getAllNotes() {
        return allNotes; //we don't have to create asyn task for this as room automatically do observe live data background thread
    }
}
