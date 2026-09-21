package com.example.crudsingleton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudsingleton.notes.notes;
import com.example.crudsingleton.notes.notesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Dashboard extends AppCompatActivity {

    private userManager userMgr;
    private notesManager notesMgr;
    private LinearLayout notesContainer;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // 1. Get Singleton Instances
        userMgr = userManager.getInstance();
        notesMgr = notesManager.getInstance();

        // 2. Find XML Views
        TextView dashboardName = findViewById(R.id.DashboardName);
        searchInput = findViewById(R.id.searchInput);
        notesContainer = findViewById(R.id.notesContainer);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        // 3. Display user greeting
        if (userMgr.getCurUser() != null) {
            dashboardName.setText("Welcome, " + userMgr.getCurUser().getName() + "!");
        }

        // 4. Open AddNotesBoard when Floating Action Button is clicked
        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, AddNotesBoard.class));
        });

        // 5. Real-time search filtering (ZERO adapter needed)
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When typing in search, filter and redraw the container
                List<notes> filtered = notesMgr.searchNotes(s.toString());
                displayNotes(filtered);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // =========================================================================
    // onResume() is called whenever returning back to this screen
    // (e.g. after adding a note in AddNotesBoard and pressing Save!)
    // This automatically refreshes the notes on screen without extra code.
    // =========================================================================
    @Override
    protected void onResume() {
        super.onResume();
        displayNotes(notesMgr.getAllNotes());
    }

    // =========================================================================
    // ALTERNATIVE 1: ScrollView + LinearLayout Dynamic Loop (ZERO ADAPTER!)
    //
    // Instead of RecyclerView + Adapter + ViewHolder:
    // 1. Clear previous views with container.removeAllViews()
    // 2. Loop through note items with a standard Java for-loop
    // 3. Inflate note_item.xml card and inject it directly with container.addView()
    // =========================================================================
    private void displayNotes(List<notes> list) {
        // Step 1: Clear the previous views so cards don't duplicate on refresh
        notesContainer.removeAllViews();

        if (list.isEmpty()) {
            TextView emptyTv = new TextView(this);
            emptyTv.setText("No notes yet. Tap + to add one!");
            emptyTv.setTextSize(16);
            emptyTv.setPadding(0, 32, 0, 0);
            notesContainer.addView(emptyTv);
            return;
        }

        // Step 2: Loop through every note in the list
        for (notes n : list) {
            // Step 3: Inflate the card XML layout (note_item.xml) directly
            View card = getLayoutInflater().inflate(R.layout.note_item, notesContainer, false);

            // Step 4: Find views inside this individual card
            TextView tvText = card.findViewById(R.id.tvNoteTxt);
            TextView tvDate = card.findViewById(R.id.tvNoteDate);
            Button btnEdit = card.findViewById(R.id.btnEdit);
            Button btnDelete = card.findViewById(R.id.btnDelete);

            // Step 5: Bind the note data directly
            tvText.setText(n.getTxt());
            tvDate.setText(n.getDateTime());

            // Step 6: Set up Edit click listener (opens an edit dialog)
            btnEdit.setOnClickListener(v -> showEditDialog(n));

            // Step 7: Set up Delete click listener
            btnDelete.setOnClickListener(v -> {
                notesMgr.deleteNote(n.getId());
                displayNotes(notesMgr.getAllNotes()); // Refresh immediately
                Toast.makeText(Dashboard.this, "Note deleted!", Toast.LENGTH_SHORT).show();
            });

            // Step 8: Add the card into our LinearLayout container
            notesContainer.addView(card);
        }
    }

    // Dialog for editing an existing note
    private void showEditDialog(notes note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Note");

        final EditText input = new EditText(this);
        input.setText(note.getTxt());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedText = input.getText().toString().trim();
            if (!updatedText.isEmpty()) {
                notesMgr.updateNote(note.getId(), updatedText);
                displayNotes(notesMgr.getAllNotes()); // Refresh screen
                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}