package github.hmasum18.architecture.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.hmasum18.architecture.dagger.anotation.MainActivityScope;
import github.hmasum18.architecture.view.MainActivity;

@Module
public class MainActivityModule{
    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    MainActivity provideMainActivity(){
        return mainActivity;
    }
}
