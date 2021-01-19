package github.hmasum18.architecture.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import github.hmasum18.architecture.RoomDataBase.Doas.NoteDao;
import github.hmasum18.architecture.model.Note;
import github.hmasum18.architecture.RoomDataBase.NoteDataBase;

import java.util.List;

//step 4
//connect room data base with models and other remote database
public class NoteRepoWithAsyncTask {
    private NoteDao noteDao; // to update insert and add data in this table
    private LiveData<List<Note>> allNotes; // observe all the notes in the table

    public NoteRepoWithAsyncTask(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application); // make the database or get existing
        noteDao = noteDataBase.noteDao(); // get the noteDao to access data from note_table of this database
        allNotes = noteDao.getAllNotes(); //so now we get all the notes from the note_table with the help of noteDao
    }

    //no now we create methods for all the database operation
    //we will do them in aynstask or background thread as room doesn't allow us to do this operations in main thread or ui thread

    public void insert(Note note) {
        new InsertNoteAyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAyncTask(noteDao).execute(note);
    }

    public void deleteAllnotes() {
        new DeleteAllNoteAyncTask(noteDao).execute();
    }

    //Live data is observed in background thread
    public LiveData<List<Note>> getAllNotes() {
        return allNotes; //we don't have to create asyn task for this as room automatically do observe live data background thread
    }


    //paremeter that you pass to use , progress, return value
    private static class InsertNoteAyncTask extends AsyncTask<Note, Void, Void> {
        //as the class is static we can't use our noteDoa directly
        private NoteDao noteDao; //create a field so that we can access the note_table data

        private InsertNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]); //insert the note that
            return null;
        }
    }

    //paremeter that you pass to use , progress, return value
    private static class UpdateNoteAyncTask extends AsyncTask<Note, Void, Void> {
        //as the class is static we can't use our noteDoa directly
        private NoteDao noteDao; //create a field so that we can access the note_table data

        private UpdateNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]); //insert the note that
            return null;
        }
    }

    //paremeter that you pass to use , progress, return value
    private static class DeleteNoteAyncTask extends AsyncTask<Note, Void, Void> {
        //as the class is static we can't use our noteDoa directly
        private NoteDao noteDao; //create a field so that we can access the note_table data

        private DeleteNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]); //insert the note that
            return null;
        }
    }

  //don't reive any data
    private static class DeleteAllNoteAyncTask extends AsyncTask<Void, Void, Void> {
        //as the class is static we can't use our noteDoa directly
        private NoteDao noteDao; //create a field so that we can access the note_table data

        private DeleteAllNoteAyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes(); //insert the note that
            return null;
        }
    }

}
