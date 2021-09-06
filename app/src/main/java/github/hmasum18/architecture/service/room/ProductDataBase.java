package github.hmasum18.architecture.service.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import github.hmasum18.architecture.service.room.Doas.ProductDao;

/*@Database(entities = {ProductDao.class}, version = 0, exportSchema = false)*/
public abstract class ProductDataBase /*extends RoomDatabase*/{
    public abstract ProductDao productDao(); // room will implement this
}
