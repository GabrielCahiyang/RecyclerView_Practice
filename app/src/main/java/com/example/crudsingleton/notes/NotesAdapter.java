package com.example.crudsingleton.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudsingleton.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    // 1. The internal dataset list holding note models currently rendered on screen
    private List<notes> noteList = new ArrayList<>();

    // 2. An interface so the Activity can decide what happens when Edit/Delete is clicked
    // Keeps the Adapter completely decoupled from database, business logic, or toasts!
    public interface OnNoteClickListener {
        void onEdit(notes note);
        void onDelete(notes note);
    }

    private final OnNoteClickListener listener;

    // 3. Constructor: Pass the click listener interface implementation from the host Activity
    public NotesAdapter(OnNoteClickListener listener) {
        this.listener = listener;
    }

    // 4. Call this whenever you want to update/refresh the list on screen!
    // Makes a defensive copy to prevent outside references mutating our internal state.
    public void setNotes(List<notes> newNotes) {
        this.noteList = new ArrayList<>(newNotes);
        notifyDataSetChanged(); // Tells Android to redraw the whole list cleanly
    }

    // 5. STEP A: Inflates (creates) the XML card layout into a new View object when needed
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    // 6. STEP B: Binds the data to the views (runs once for every card as it enters screen viewport)
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        notes currentNote = noteList.get(position);

        // Put text into the TextViews from the note model
        holder.tvText.setText(currentNote.getTxt());
        holder.tvDate.setText(currentNote.getDateTime());

        // Button clicks trigger the decoupled listener callbacks
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(currentNote));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(currentNote));
    }

    // 7. STEP C: Tells Android how many total items are present in the list
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // 8. The ViewHolder: Holds and caches the findViewById references for one card.
    // Avoids repeated findViewById() performance penalties during rapid scrolling.
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvText, tvDate;
        Button btnEdit, btnDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // IDs MUST match note_item.xml exactly
            tvText = itemView.findViewById(R.id.tvNoteTxt);
            tvDate = itemView.findViewById(R.id.tvNoteDate);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
