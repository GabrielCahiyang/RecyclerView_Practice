package com.example.crudsingleton;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudsingleton.notes.notesManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNotesBoard extends AppCompatActivity {

    private EditText etNoteContent;
    private EditText etNoteDateTime;
    private Button btnSaveNote;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_notes_board);

        etNoteContent = findViewById(R.id.etNoteContent);
        etNoteDateTime = findViewById(R.id.etNoteDateTime);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        // Initialize Calendar with current date and time
        calendar = Calendar.getInstance();
        updateDateTimeDisplay();

        // Clicking date/time field opens the Calendar DatePickerDialog & TimePickerDialog
        etNoteDateTime.setOnClickListener(v -> showDateTimePicker());

        // Save note to the notesManager singleton
        btnSaveNote.setOnClickListener(v -> {
            String content = etNoteContent.getText().toString().trim();
            String dateTime = etNoteDateTime.getText().toString().trim();

            if (content.isEmpty()) {
                Toast.makeText(this, "Please enter some note content", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save into notesManager singleton
            notesManager.getInstance().addNote(content);

            Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();

            // Finish this activity and return to Dashboard
            finish();
        });
    }

    // Opens DatePicker and then TimePicker using java.util.Calendar
    private void showDateTimePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            calendar.set(Calendar.YEAR, selectedYear);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

            // Once date is selected, open TimePicker
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                updateDateTimeDisplay();
            }, hour, minute, false);

            timePickerDialog.show();
        }, year, month, day);

        datePickerDialog.show();
    }

    // Formats calendar date & time into the EditText
    private void updateDateTimeDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault());
        etNoteDateTime.setText(sdf.format(calendar.getTime()));
    }
}