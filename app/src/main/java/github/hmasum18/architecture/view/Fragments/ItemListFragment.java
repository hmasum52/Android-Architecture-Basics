package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.databinding.FragmentItemListBinding;
import github.hmasum18.architecture.view.App;
import github.hmasum18.architecture.view.IFragment;
import github.hmasum18.architecture.viewModel.NoteViewModel;
import github.hmasum18.architecture.service.model.Note;
import github.hmasum18.architecture.view.Adapters.NoteListAdapter;

import java.util.List;

import javax.inject.Inject;

public class ItemListFragment extends Fragment implements IFragment {
    public static final String TAG = "ItemListFragment->";
    private FragmentItemListBinding mVB;

    @Inject
    NoteViewModel noteViewModel;

    private NoteListAdapter noteListAdapter;
    private List<Note> allNotes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tutorial: https://developer.android.com/guide/fragments/appbar
        Log.d(TAG, "onCreate: register to option menu click call backs");
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.item_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_one_delete_all_item) {
            noteViewModel.deleteAllNotes();
            Toast.makeText(getContext(), "All notes deleted", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.fake_store) {
            NavHostFragment.findNavController(ItemListFragment.this).navigate(
                    R.id.fakeStoreFragment
            );
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        mVB = FragmentItemListBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        View view = mVB.getRoot();

        //add adapters and listeners
        mVB.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        noteListAdapter = new NoteListAdapter();
        mVB.recyclerView.setAdapter(noteListAdapter);

        mVB.listFragFabId.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_itemListFragment_to_addItemFragment);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        addSwipeListenerToRecyclerView();

        //noteAdapter.setOnClickListener(new NoteAdapter.OnNoteItemClickListener() {
        noteListAdapter.setOnClickListener(note -> {
            Bundle bundle = new Bundle();
            bundle.putInt("note_id", note.getId());
            bundle.putString("note_title", note.getTitle());
            bundle.putString("note_description", note.getDescription());
            bundle.putInt("note_priority", note.getPriority());

            NavHostFragment.findNavController(ItemListFragment.this).navigate(
                    R.id.action_itemListFragment_to_addItemFragment,
                    bundle, null, null
            );
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        AppComponent appComponent = ((App) getActivity().getApplication()).getAppComponent();
        appComponent.inject(this);

        noteViewModel.setCurrentFragment(this);
        Log.d(TAG, "onResume: viewModel: " + noteViewModel);

        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            noteListAdapter.submitList(notes);
            allNotes = notes;
            Log.d(TAG, " notes received from testViewModel");
        });
    }

    private void addSwipeListenerToRecyclerView() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //noteViewModel.delete( allNotes.get(viewHolder.getAdapterPosition()) );
                noteViewModel.delete(allNotes.get(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity().getApplicationContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mVB.recyclerView); //attach to our recyclerView
    }
}