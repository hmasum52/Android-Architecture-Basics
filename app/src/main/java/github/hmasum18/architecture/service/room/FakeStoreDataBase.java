package github.hmasum18.architecture.service.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import github.hmasum18.architecture.service.model.Product;
import github.hmasum18.architecture.service.room.Doas.ProductDao;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class FakeStoreDataBase extends RoomDatabase{
    public abstract ProductDao productDao(); // room will implement this
}
