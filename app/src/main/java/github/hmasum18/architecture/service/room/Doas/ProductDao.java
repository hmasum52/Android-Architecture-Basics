package github.hmasum18.architecture.service.room.Doas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import github.hmasum18.architecture.service.model.Note;
import github.hmasum18.architecture.service.model.Product;

/*@Dao*/
public interface ProductDao{
    /*@Insert
    void insert(Product... product);

    @Update
    void update(Product... product);

    @Delete
    void delete(Product... product);*/

  /*  @Query("DELETE FROM note_table") //delete all note data from the note_table
    void deleteAllNotes();

    //live data will observe any change in the note_table
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();*/
}
