package github.hmasum18.architecture.RoomDataBase.Doas;

// Doa = data access object

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import github.hmasum18.architecture.model.Note;

import java.util.List;

//step 2
//this is where we make all the changes in the corresponding table
@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table") //delete all note data from the note_table
    void deleteAllNotes();

    //live data will observe any change in the note_table
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData< List<Note> > getAllNotes();
}
