package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.viewModel.NoteViewModel;
import github.hmasum18.architecture.service.model.Note;

public class AddItemFragment extends Fragment {
    public static final String TAG = "AddItemFragment->";
    private EditText note_title_edtxt, note_description_edtxt;
    private NumberPicker note_priority_numPkr;
    private Button save_btn;
    private NoteViewModel noteViewModel;

    private int note_id  = -1;
    private String note_title = "";
    private String note_description = "";
    private int note_priority = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note_id = this.getArguments().getInt("note_id",-1);
            note_title = this.getArguments().getString("note_title");
            note_description = this.getArguments().getString("note_description");
            note_priority = this.getArguments().getInt("note_priority");
            getActivity().setTitle("Edit note fragment");
        }else {
            getActivity().setTitle("Add note fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        setEnterTransition(new Explode());
        setExitTransition(new Explode());

        noteViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())
        ).get(NoteViewModel.class);

        // Inflate the layout for this fragment
        //and find the views
        note_title_edtxt = view.findViewById(R.id.addFrag_titleId);
        note_description_edtxt = view.findViewById(R.id.addFrag_descriptionId);
        note_priority_numPkr = view.findViewById(R.id.addFrag_priority_picker);
        note_priority_numPkr.setMinValue(1);
        note_priority_numPkr.setMaxValue(10);
        save_btn = view.findViewById(R.id.addFrag_saveBtnId);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init the fields
        if(note_id != -1 &&!note_title.equals("") && !note_description.equals("") && note_priority>0)
        {
            note_title_edtxt.setText(note_title);
            note_description_edtxt.setText(note_description);
            note_priority_numPkr.setValue(note_priority);
        }

        save_btn.setOnClickListener( v -> {
            saveNote();
            NavHostFragment.findNavController(this).popBackStack();
            Toast.makeText(getActivity().getApplicationContext(),"Note saved successfully",Toast.LENGTH_SHORT).show();
        });
    }

    private void saveNote() {
        String title = note_title_edtxt.getText().toString();
        String description = note_description_edtxt.getText().toString();
        int priority = note_priority_numPkr.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty() ) {
            Toast.makeText(getActivity().getApplicationContext(), "Please insert all the data", Toast.LENGTH_SHORT).show();
            return;
        }
        if(note_id == -1) //we are in add note mode
        {
           // noteViewModel.insert(new Note(title,description,priority));
            noteViewModel.insert(new Note(title,description,priority));
            Toast.makeText(getActivity().getApplicationContext(), "new note inserted successfully", Toast.LENGTH_SHORT).show();

        }else { //we are in edit note mode
            Note note = new Note(title,description,priority);
            note.setId(note_id);
           // noteViewModel.update(note);
            noteViewModel.update(note);
            Toast.makeText(getActivity().getApplicationContext(), "note updated successfully", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}