package com.example.crudsingleton.notes;

import java.util.ArrayList;
import java.util.List;

public class notesManager {

    // --- SINGLETON SETUP ---
    private static notesManager instance;
    private final ArrayList<notes> noteList = new ArrayList<>();
    private int nextId = 1;

    private notesManager() {}

    public static synchronized notesManager getInstance() {
        if (instance == null) {
            instance = new notesManager();
        }
        return instance;
    }

    // --- CREATE ---
    public void addNote(String txt) {
        noteList.add(new notes(nextId++, txt));
    }

    // --- READ (ALL) ---
    public List<notes> getAllNotes() {
        return new ArrayList<>(noteList);
    }

    // --- UPDATE ---
    public boolean updateNote(int id, String newTxt) {
        for (notes note : noteList) {
            if (note.getId() == id) {
                note.setTxt(newTxt);
                return true; // Successfully updated
            }
        }
        return false; // Note not found
    }

    // --- DELETE ---
    public boolean deleteNote(int id) {
        // removeIf removes the item matching the ID in 1 line
        return noteList.removeIf(note -> note.getId() == id);
    }

    // --- SEARCH / FILTER ---
    public List<notes> searchNotes(String query) {
        String cleanQuery = query.trim().toLowerCase();

        // If search is blank, return everything
        if (cleanQuery.isEmpty()) {
            return getAllNotes();
        }

        ArrayList<notes> filtered = new ArrayList<>();
        for (notes note : noteList) {
            // Checks if the note text contains the search query
            if (note.getTxt().toLowerCase().contains(cleanQuery)) {
                filtered.add(note);
            }
        }
        return filtered;
    }
}
