package github.hmasum18.architecture.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.view.Fragments.ItemListFragment;
import github.hmasum18.architecture.viewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    public static final String TAG = "MainActivity->";
    private NoteViewModel noteViewModel;
    private NavController navController;
    private int currentFragmentId;
    private Toolbar mToolbar;
    private IFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        //find and set toolbar as actionbar
        mToolbar = super.findViewById(R.id.toolbar);
        super.setSupportActionBar(mToolbar);

        //find and add destination changed listener to nav controller.
        navController = Navigation.findNavController(this, R.id.nav_host_fragmnet);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        if (currentFragmentId == R.id.itemListFragment) {
            mToolbar.inflateMenu(R.menu.menu_one);
        } else {
            mToolbar.inflateMenu(R.menu.add_item);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: UwU");
       //Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragmnet);
        //this.currentFragment = (IFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        noteViewModel.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
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
        super.invalidateOptionsMenu();
    }
}