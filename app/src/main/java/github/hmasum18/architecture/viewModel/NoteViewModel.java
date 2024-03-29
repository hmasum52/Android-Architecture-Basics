package github.hmasum18.architecture.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import github.hmasum18.architecture.service.repository.NoteRepo;
import github.hmasum18.architecture.service.model.Note;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NoteViewModel extends ViewModel{
    public static final String TAG = "NoteViewModel->";

    @Inject
    NoteRepo noteRepo;


    @Inject
    public NoteViewModel() {
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
