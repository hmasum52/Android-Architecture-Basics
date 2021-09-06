package github.hmasum18.architecture.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import github.hmasum18.architecture.dagger.module.AppModule;
import github.hmasum18.architecture.dagger.module.RoomModule;
import github.hmasum18.architecture.service.repository.NoteRepo;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent{

    //sub component
    MainActivityComponent.Builder getMainActivityComponentBuilder();

    void inject(NoteRepo noteRepo);
}
