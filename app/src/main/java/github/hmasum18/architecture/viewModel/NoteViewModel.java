package github.hmasum18.architecture.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.service.repository.NoteRepo;
import github.hmasum18.architecture.service.model.Note;
import github.hmasum18.architecture.view.IFragment;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    public static final String TAG = "NoteViewModel->";
    private final NoteRepo noteRepo;
    private IFragment currentFragment;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepo = new NoteRepo(application);
    }

    public void setCurrentFragment(IFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    //note properties
    public void insert(Note note) {
        noteRepo.insert(note);
    }

    public void update(Note note) {
        noteRepo.update(note);
    }

    public void delete(Note note) {
        noteRepo.delete(note);
    }

    public void deleteAllNotes() {
        noteRepo.deleteAllNotes();
    }

    //Live data is observed in background thread
    public LiveData<List<Note>> getAllNotes() {
        return noteRepo.getAllNotes(); //we don't have to create asyn task for this as room automatically do observe live data background thread
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
    }
}
