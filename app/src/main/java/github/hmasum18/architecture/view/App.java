package github.hmasum18.architecture.view;

import android.app.Application;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.dagger.component.DaggerAppComponent;
import github.hmasum18.architecture.dagger.module.AppModule;
import github.hmasum18.architecture.dagger.module.RoomModule;

public class App extends Application{
    private static final String TAG = "App";
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        appComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this))
                .build();

        Log.d(TAG, "onCreate: app component created successfully");
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
