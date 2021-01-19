package github.hmasum18.architecture.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureexamle_1.R;
import github.hmasum18.architecture.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteItemClickListener onNoteItemClickListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent is the recyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);

        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteDescription.setText(currentNote.getDescription());
        holder.notePrioriy.setText(currentNote.getPriority()+"");

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes( List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void getNote(int position)
    {
        notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView noteTitle;
        private TextView noteDescription;
        private TextView notePrioriy;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.item_title);
            noteDescription  = itemView.findViewById(R.id.item_description);
            notePrioriy = itemView.findViewById(R.id.item_priority);

            //when we click the item or note
            itemView.setOnClickListener(v -> {
                //then we call our custom onClick which we will define in other fragment or activity
                //when we will click the item we will have the position of it
                int position = getAdapterPosition();
                //we call the member function of the instance that we created in ListItem fragment
                //if we didn't created any instance and position is -1 or invalid(in case we deleted the item but it is still not completed)
                if(onNoteItemClickListener != null && position !=RecyclerView.NO_POSITION)
                    onNoteItemClickListener.onNoteItemClick( notes.get(position) ); //pass the note in that position to our fragment
            });
        }
    }

    //this interface work as brigde to handle the click event between adapter and fragment
    public interface OnNoteItemClickListener {
        void onNoteItemClick(Note note);
    }

    //to use the above interface we must have a method with a reference to it
    //this is a member function of adapter instance
    //which recieves instance of a class(interface here) and then we can access the member funtion of this instance
    public void setOnClickListener(OnNoteItemClickListener onNoteItemClickListener){
        this.onNoteItemClickListener = onNoteItemClickListener;
    }


}
