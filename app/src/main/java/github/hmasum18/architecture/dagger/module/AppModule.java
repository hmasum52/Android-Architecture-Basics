package github.hmasum18.architecture.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.hmasum18.architecture.view.App;

@Module
public class AppModule{
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApp(){
        return app;
    }
}
