package github.hmasum18.architecture.Views.Fragments;

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

import github.hmasum18.architecture.ViewModels.NoteViewModel;
import com.example.architectureexamle_1.R;
import github.hmasum18.architecture.ViewModels.TestViewModel;
import github.hmasum18.architecture.model.Note;
import github.hmasum18.architecture.Views.Adapters.NoteListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {

    public static final String TAG = "ItemListFragment->";
    private RecyclerView  recyclerView;
    private NoteViewModel noteViewModel;
    private TestViewModel testViewModel;
    private FloatingActionButton addFab;
  //  private NoteAdapter noteAdapter;
    private NoteListAdapter noteListAdapter;
    private List<Note> allNotes;

    public ItemListFragment() {
        // Required empty public constructor
    }

    public static ItemListFragment newInstance(String param1, String param2) {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

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
        /*noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);*/
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

        testViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(TestViewModel.class);

        testViewModel.getAllNotes().observe(requireActivity(),notes ->{
            noteListAdapter.submitList(notes);
            allNotes = notes;
            Log.d(TAG," notes received from testViewModel");
        });

       /* noteViewModel.getAllNotes().observe(requireActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(notes == null)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"note list is null",Toast.LENGTH_SHORT).show();
                }
                else  if(notes.size() == 0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"note list is empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"note list size: "+notes.size(),Toast.LENGTH_SHORT).show();
                }
              //  noteAdapter.setNotes(notes);
                noteListAdapter.submitList(notes);
                allNotes = notes;
            }
        });*/

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //noteViewModel.delete( allNotes.get(viewHolder.getAdapterPosition()) );
                testViewModel.delete(allNotes.get(viewHolder.getAdapterPosition()));
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