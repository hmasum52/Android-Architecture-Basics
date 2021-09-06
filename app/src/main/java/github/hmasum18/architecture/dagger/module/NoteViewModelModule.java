package github.hmasum18.architecture.dagger.module;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import github.hmasum18.architecture.dagger.anotation.MainActivityScope;
import github.hmasum18.architecture.view.App;
import github.hmasum18.architecture.view.MainActivity;
import github.hmasum18.architecture.viewModel.NoteViewModel;

@Module
public abstract class NoteViewModelModule{
    @Provides
    @MainActivityScope
    static NoteViewModel provideNoteViewModel(MainActivity mainActivity){
        return new ViewModelProvider(mainActivity,
                ViewModelProvider.AndroidViewModelFactory.getInstance(mainActivity.getApplication()))
                .get(NoteViewModel.class);
    }
}
