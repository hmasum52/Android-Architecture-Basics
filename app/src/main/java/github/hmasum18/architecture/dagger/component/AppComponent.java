package github.hmasum18.architecture.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import github.hmasum18.architecture.dagger.module.AppModule;
import github.hmasum18.architecture.dagger.module.NetworkModule;
import github.hmasum18.architecture.dagger.module.RoomModule;
import github.hmasum18.architecture.service.repository.NoteRepo;
import github.hmasum18.architecture.service.repository.ProductRepo;
import github.hmasum18.architecture.view.Fragments.AddItemFragment;
import github.hmasum18.architecture.view.Fragments.FakeStoreFragment;
import github.hmasum18.architecture.view.Fragments.ItemListFragment;
import github.hmasum18.architecture.view.MainActivity;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class, NetworkModule.class})
public interface AppComponent{
    // repo
    void inject(NoteRepo noteRepo);
    void inject(ProductRepo productRepo);

    // views
    void inject(MainActivity mainActivity);
    void inject(ItemListFragment itemListFragment);
    void inject(AddItemFragment addItemFragment);

    void inject(FakeStoreFragment fakeStoreFragment);
}
