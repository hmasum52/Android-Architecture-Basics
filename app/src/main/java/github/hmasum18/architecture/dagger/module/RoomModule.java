package github.hmasum18.architecture.dagger.module;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.hmasum18.architecture.service.room.Doas.NoteDao;
import github.hmasum18.architecture.service.room.NoteDataBase;

@Module
public class RoomModule{
    private static final String TAG = "RoomModule";

    private Context context;

    public RoomModule(Context context) {
        this.context = context;
    }

    /**
     * create 5 executor thread to perform
     * room operations in background thread
     *
     * @return {@link ExecutorService} with 5 fixed thread
     */
    @Provides
    @Singleton
    ExecutorService provideRoomExecutorService(){
        Log.d(TAG, "provideRoomExecutorService: creating room executor service...");
        return Executors.newFixedThreadPool(5);
    }

    @Provides
    @Singleton
    NoteDataBase provideNoteDataBase(){
        Log.d(TAG, "provideNoteDataBase: creating note database...");
        return Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class , "note_database")
                .fallbackToDestructiveMigration() ///delete existing database
                .build();
    }

    @Provides
    @Singleton
    NoteDao provideNoteDao(){
        Log.d(TAG, "provideNoteDao: creating note dao...");
        return provideNoteDataBase().noteDao();
    }


}
