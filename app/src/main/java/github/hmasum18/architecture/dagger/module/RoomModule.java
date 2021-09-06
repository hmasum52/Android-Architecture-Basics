package github.hmasum18.architecture.dagger.module;

import android.util.Log;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.hmasum18.architecture.service.room.Doas.NoteDao;
import github.hmasum18.architecture.service.room.Doas.ProductDao;
import github.hmasum18.architecture.service.room.NoteDataBase;
import github.hmasum18.architecture.service.room.FakeStoreDataBase;
import github.hmasum18.architecture.view.App;

@Module
public abstract class RoomModule{
    private static final String TAG = "RoomModule";

    /**
     * create 5 executor thread to perform
     * room operations in background thread
     *
     * @return {@link ExecutorService} with 5 fixed thread
     */
    @Provides
    @Singleton
    static ExecutorService provideRoomExecutorService(){
        Log.d(TAG, "provideRoomExecutorService: creating room executor service...");
        return Executors.newFixedThreadPool(5);
    }

    @Provides
    @Singleton
    static NoteDataBase provideNoteDataBase(App app){
        Log.d(TAG, "provideNoteDataBase: creating note database...");
        return Room.databaseBuilder(app.getApplicationContext(), NoteDataBase.class , "note_database")
                .fallbackToDestructiveMigration() ///delete existing database
                .build();
    }

    @Provides
    @Singleton
    static NoteDao provideNoteDao(App app){
        Log.d(TAG, "provideNoteDao: creating note dao...");
        return provideNoteDataBase(app).noteDao();
    }

    @Provides
    @Singleton
    static FakeStoreDataBase provideFakeStoreDataBase(App app){
        Log.d(TAG, "provideNoteDataBase: creating note database...");
        return Room.databaseBuilder(app.getApplicationContext(),
                FakeStoreDataBase.class , "fake_store")
                .build();
    }

    @Provides
    @Singleton
    static ProductDao provideProductDao(App app){
        Log.d(TAG, "provideNoteDao: creating note dao...");
        return provideFakeStoreDataBase(app).productDao();
    }


}
