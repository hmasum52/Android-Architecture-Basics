package github.hmasum18.architecture.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import github.hmasum18.architecture.R;
import github.hmasum18.architecture.dagger.component.AppComponent;
import github.hmasum18.architecture.databinding.FragmentAddItemBinding;
import github.hmasum18.architecture.view.App;
import github.hmasum18.architecture.viewModel.NoteViewModel;
import github.hmasum18.architecture.service.model.Note;

public class AddItemFragment extends Fragment {
    public static final String TAG = "AddItemFragment->";
    private FragmentAddItemBinding mVB;
    @Inject
    NoteViewModel noteViewModel;

    private int note_id  = -1;
    private String note_title = "";
    private String note_description = "";
    private int note_priority = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        super.setHasOptionsMenu(true);

        if (getArguments() != null) {
            note_id = this.getArguments().getInt("note_id", -1);
            note_title = this.getArguments().getString("note_title");
            note_description = this.getArguments().getString("note_description");
            note_priority = this.getArguments().getInt("note_priority");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_item,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mVB.addFragTitleId.setText("");
        mVB.addFragDescriptionId.setText("");
        mVB.addFragPriorityPicker.setValue(1);
        Toast.makeText(getContext(),item.getTitle()+" clicked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        mVB = FragmentAddItemBinding.inflate(inflater, container,false);
        View view = mVB.getRoot();

        AppComponent appComponent= ( (App) getActivity().getApplication() ).getAppComponent();
        appComponent.inject(this);
        Log.d(TAG, "onCreateView: viewModel: "+noteViewModel);

        // Inflate the layout for this fragment
        //and find the views
        mVB.addFragPriorityPicker.setMinValue(1);
        mVB.addFragPriorityPicker.setMaxValue(10);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        //init the fields
        if(note_id != -1 &&!note_title.equals("") && !note_description.equals("") && note_priority>0) {
            mVB.addFragTitleId.setText(note_title);
            mVB.addFragDescriptionId.setText(note_description);
            mVB.addFragPriorityPicker.setValue(note_priority);
        }

        mVB.addFragSaveBtnId.setOnClickListener( v -> {
            saveNote();
            NavHostFragment.findNavController(this).popBackStack();
            Toast.makeText(getActivity().getApplicationContext(),"Note saved successfully",Toast.LENGTH_SHORT).show();
        });
    }

    private void saveNote() {
        String title = mVB.addFragTitleId.getText().toString();
        String description =  mVB.addFragDescriptionId.getText().toString();
        int priority = mVB.addFragPriorityPicker.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty() ) {
            Toast.makeText(getActivity().getApplicationContext(), "Please insert all the data", Toast.LENGTH_SHORT).show();
            return;
        }
        if(note_id == -1) //we are in add note mode
        {
           // noteViewModel.insert(new Note(title,description,priority));
            noteViewModel.insert(new Note(title,description,priority));
            Toast.makeText(getContext(), "new note inserted successfully", Toast.LENGTH_SHORT).show();

        }else { //we are in edit note mode
            Note note = new Note(title,description,priority);
            note.setId(note_id);
           // noteViewModel.update(note);
            noteViewModel.update(note);
            Toast.makeText(getContext(), "note updated successfully", Toast.LENGTH_SHORT).show();
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