package github.hmasum18.architecture.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import github.hmasum18.architecture.service.repository.NoteRepo;
import github.hmasum18.architecture.service.model.Note;
import github.hmasum18.architecture.view.IFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NoteViewModel extends ViewModel{
    public static final String TAG = "NoteViewModel->";

    @Inject
    NoteRepo noteRepo;

    private IFragment currentFragment;

    @Inject
    public NoteViewModel() {
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
