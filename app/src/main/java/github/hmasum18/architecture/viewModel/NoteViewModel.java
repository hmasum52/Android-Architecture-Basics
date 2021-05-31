package github.hmasum18.architecture.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.service.repository.NoteRepoWithThreadPool;
import github.hmasum18.architecture.service.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    public static final String TAG = "NoteViewModel->";
    private final NoteRepoWithThreadPool noteRepoWithThreadPool;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepoWithThreadPool = new NoteRepoWithThreadPool(application);
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
        return noteRepoWithThreadPool.getAllNotes(); //we don't have to create asyn task for this as room automatically do observe live data background thread
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
    }
}
