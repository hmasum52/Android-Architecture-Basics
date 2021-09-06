package github.hmasum18.architecture.view;

import android.app.Application;
import android.util.Log;

import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.dagger.component.DaggerAppComponent;
import github.hmasum18.architecture.dagger.module.RoomModule;

public class App extends Application {
    private static final String TAG = "App";
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        appComponent = DaggerAppComponent.builder().
                roomModule(new RoomModule(getApplicationContext()))
                .build();

        Log.d(TAG, "onCreate: app component created successfully");
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
