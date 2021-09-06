package github.hmasum18.architecture.service.room.Doas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import github.hmasum18.architecture.service.model.Product;

@Dao
public interface ProductDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product... product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Product> products);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Product... product);

    @Delete
    void delete(Product... product);

    @Query("DELETE FROM product_table") //delete all note data from the note_table
    void deleteAllNotes();

    //live data will observe any change in the note_table
    @Query("SELECT * FROM product_table")
    LiveData<List<Product>> getAllProducts();
}
