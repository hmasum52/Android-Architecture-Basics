package github.hmasum18.architecture.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.databinding.ActivityMainBinding;
import github.hmasum18.architecture.viewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    public static final String TAG = "MainActivity->";

    private ActivityMainBinding mVB;

    @Inject
    NoteViewModel noteViewModel;

    private NavController navController;
    private int currentFragmentId;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVB = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mVB.getRoot());

        // init dagger components
        AppComponent appComponent = ((App)getApplication()).getAppComponent();
        appComponent.inject(this);
        Log.d(TAG, "onCreate: viewModel: "+noteViewModel);


        //find and set toolbar as actionbar
        super.setSupportActionBar(mVB.toolbar);
        mVB.toolbar.setLogo(R.drawable.ic_launcher_foreground);


        //find and add destination changed listener to nav controller.
        navController = Navigation.findNavController(this, R.id.nav_host_fragmnet);
        navController.addOnDestinationChangedListener(this);

        //create app bar configuration
        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.itemListFragment,R.id.fakeStoreFragment).build();

        NavigationUI.setupWithNavController(mVB.toolbar,navController,appBarConfiguration);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller,
                                     @NonNull NavDestination destination,
                                     @Nullable Bundle arguments) {
        Log.d(TAG, "onDestinationChanged: current destination: "+navController.getCurrentDestination().getLabel());
        this.currentFragmentId = destination.getId();

        if(appBarConfiguration!=null){
            boolean isTopLevel = appBarConfiguration.getTopLevelDestinations().contains(currentFragmentId);
            if(!isTopLevel){
                new Handler().post(()->{
                    mVB.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
                    mVB.toolbar.getLogo().setVisible(false,false);
                });
            }else if(currentFragmentId == R.id.itemListFragment){
                new Handler().post(()->{
                   // mToolbar.getLogo().set(true,false);
                    });
            }
        }
        super.setTitle(destination.getLabel());
    }
}