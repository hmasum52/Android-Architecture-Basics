package github.hmasum18.architecture.dagger.component;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Component;
import github.hmasum18.architecture.dagger.module.RoomModule;
import github.hmasum18.architecture.service.repository.NoteRepoWithThreadPool;
import github.hmasum18.architecture.service.room.Doas.NoteDao;
import github.hmasum18.architecture.service.room.NoteDataBase;

@Singleton
@Component(modules = {RoomModule.class})
public interface AppComponent{
    ExecutorService getRoomExecutorService();
    NoteDataBase getNoteDatabase();
    NoteDao getNoteDao();

    void inject(NoteRepoWithThreadPool noteRepoWithThreadPool);
}
