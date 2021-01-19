package github.hmasum18.architecture.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import github.hmasum18.architecture.ViewModels.NoteViewModel;
import com.example.architectureexamle_1.R;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel =  new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);

      /*  noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Toast.makeText(getApplicationContext(),"hello world on changed", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_one,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_one_delete_all_item:
                noteViewModel.deleteAllNotes();
                Toast.makeText(getApplicationContext(),"All notes deleted",Toast.LENGTH_SHORT).show();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}