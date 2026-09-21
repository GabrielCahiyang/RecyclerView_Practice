package com.example.crudsingleton.notes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudsingleton.R;

public class NotesActivity extends AppCompatActivity {

    private NotesAdapter adapter;
    private notesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // 1. Get your singleton manager instance (single source of truth for in-memory / persistent notes)
        manager = notesManager.getInstance();

        // 2. Find views from XML layout
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EditText searchInput = findViewById(R.id.searchInput);

        // 3. CRITICAL: RecyclerView will show NOTHING if you forget this line!
        // Tells it to display items vertically from top to bottom.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 4. Create the adapter and define what happens when Edit/Delete buttons are clicked
        adapter = new NotesAdapter(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onEdit(notes note) {
                // Example: Update note text via the singleton manager
                manager.updateNote(note.getId(), "Updated text!");
                adapter.setNotes(manager.getAllNotes()); // Refresh screen dataset
                Toast.makeText(NotesActivity.this, "Note updated!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(notes note) {
                // Delete from singleton manager
                manager.deleteNote(note.getId());
                adapter.setNotes(manager.getAllNotes()); // Refresh screen dataset
                Toast.makeText(NotesActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Attach adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        // 6. Load initial data from manager to screen
        adapter.setNotes(manager.getAllNotes());

        // 7. Live search as you type using Android's TextWatcher
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter notes and refresh screen instantly with filtered subset
                adapter.setNotes(manager.searchNotes(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
