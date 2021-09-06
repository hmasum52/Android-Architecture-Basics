package github.hmasum18.architecture.dagger.component;

import dagger.BindsInstance;
import dagger.Subcomponent;
import github.hmasum18.architecture.dagger.anotation.MainActivityScope;
import github.hmasum18.architecture.dagger.module.MainActivityModule;
import github.hmasum18.architecture.dagger.module.NoteViewModelModule;
import github.hmasum18.architecture.view.Fragments.ItemListFragment;
import github.hmasum18.architecture.view.MainActivity;
import github.hmasum18.architecture.viewModel.NoteViewModel;

@MainActivityScope
@Subcomponent(modules = {MainActivityModule.class, NoteViewModelModule.class})
public interface MainActivityComponent{
    NoteViewModel getNoteViewModel();

    @Subcomponent.Builder
    interface Builder{
        Builder mainActivityModule(MainActivityModule mainActivityModule);
        MainActivityComponent build();
    }

    void inject(MainActivity mainActivity);
    void inject(ItemListFragment itemListFragment);
}
