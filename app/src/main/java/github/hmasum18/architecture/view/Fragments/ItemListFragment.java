package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.viewModel.NoteViewModel;
import github.hmasum18.architecture.service.model.Note;
import github.hmasum18.architecture.view.Adapters.NoteListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ItemListFragment extends Fragment {

    public static final String TAG = "ItemListFragment->";
    private RecyclerView  recyclerView;
    private NoteViewModel noteViewModel;
    private FloatingActionButton addFab;
  //  private NoteAdapter noteAdapter;
    private NoteListAdapter noteListAdapter;
    private List<Note> allNotes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        //find the views
        recyclerView = view.findViewById(R.id.recycler_view);
        addFab = view.findViewById(R.id.listFrag_fabId);

        //add adapters and listeners
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        noteListAdapter = new NoteListAdapter();
        recyclerView.setAdapter(noteListAdapter);

        addFab.setOnClickListener( v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_itemListFragment_to_addItemFragment);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes ->{
            noteListAdapter.submitList(notes);
            allNotes = notes;
            Log.d(TAG," notes received from testViewModel");
        });

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
                Toast.makeText(getActivity().getApplicationContext(),"Note deleted successfully",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView); //attach to our recyclerView

        //noteAdapter.setOnClickListener(new NoteAdapter.OnNoteItemClickListener() {
        noteListAdapter.setOnClickListener(new NoteListAdapter.OnNoteItemClickListener() {
            @Override
            public void onNoteItemClick(Note note) {
                Bundle bundle = new Bundle();
                bundle.putInt("note_id",note.getId());
                bundle.putString("note_title",note.getTitle());
                bundle.putString("note_description",note.getDescription());
                bundle.putInt("note_priority",note.getPriority());

                NavHostFragment.findNavController(ItemListFragment.this).navigate(
                        R.id.action_itemListFragment_to_addItemFragment,
                        bundle,null,null
                );
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}