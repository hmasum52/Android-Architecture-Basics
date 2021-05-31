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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.viewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    public static final String TAG = "MainActivity->";
    private NoteViewModel noteViewModel;
    private NavController navController;
    private int currentFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        navController = Navigation.findNavController(this, R.id.nav_host_fragmnet);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_one, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        if (currentFragmentId == R.id.itemListFragment) {
            menu.findItem(R.id.menu_one_delete_all_item).setVisible(true);
            menu.findItem(R.id.menu_one_refresh).setVisible(false);
        } else {
            menu.findItem(R.id.menu_one_delete_all_item).setVisible(false);
            menu.findItem(R.id.menu_one_refresh).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_one_delete_all_item) {
            noteViewModel.deleteAllNotes();
            Toast.makeText(getApplicationContext(), "All notes deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
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
        this.currentFragmentId = destination.getId();
        super.invalidateOptionsMenu();
    }
}