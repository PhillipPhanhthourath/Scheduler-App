package com.example.scheduler_app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET, eventDescriptionET, eventDateET, eventTimeET;
    private LocalDate eventDate;
    private LocalTime eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        Button deleteButton = findViewById(R.id.deleteButton); // Make sure you have this ID set for your delete button
        if (getIntent().hasExtra("eventId")) {
            loadEventDetails(getIntent().getStringExtra("eventId"));
            deleteButton.setVisibility(View.VISIBLE); // Show the delete button for existing events
        } else {
            deleteButton.setVisibility(View.GONE); // Hide the delete button for new events
            initDefaultDateTime();
        }
    }


    private void loadEventDetails(String eventId) {
        // Find the event by ID
        for (Event event : Event.eventsList) {
            if (event.getId().equals(eventId)) {
                eventNameET.setText(event.getName());
                eventDescriptionET.setText(event.getDescription());
                eventDateET.setText(event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                eventTimeET.setText(event.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                // Store the event's date and time for potential use
                eventDate = event.getDate();
                eventTime = event.getTime();
                break;
            }
        }
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDescriptionET = findViewById(R.id.eventDescriptionET);
        eventDateET = findViewById(R.id.eventDateET);
        eventTimeET = findViewById(R.id.eventTimeET);

        eventTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventEditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                eventTime = LocalTime.of(hourOfDay, minute);
                                eventTimeET.setText(eventTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    private void initDefaultDateTime() {
        // Set the current date and time as defaults
        eventDate = LocalDate.now();
        eventTime = LocalTime.now();
        eventDateET.setText(eventDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        eventTimeET.setText(eventTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public void saveEventAction(View view) {
        String eventId = getIntent().getStringExtra("eventId");
        String eventName = eventNameET.getText().toString();
        String eventDescription = eventDescriptionET.getText().toString();
        LocalDate date = LocalDate.parse(eventDateET.getText().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time = LocalTime.parse(eventTimeET.getText().toString(), DateTimeFormatter.ofPattern("HH:mm"));

        // Update existing event if editing
        if (eventId != null && !eventId.isEmpty()) {
            for (Event event : Event.eventsList) {
                if (event.getId().equals(eventId)) {
                    event.setName(eventName);
                    event.setDescription(eventDescription);
                    event.setDate(date);
                    event.setTime(time);
                    break; // Event updated, break the loop
                }
            }
        } else {
            // Create a new event if adding
            Event newEvent = new Event(eventName, date, time, eventDescription);
            Event.eventsList.add(newEvent);
        }
        finish(); // Return to the previous activity
    }

    public void deleteEventAction(View view) {
        String eventId = getIntent().getStringExtra("eventId");
        if (eventId != null && !eventId.isEmpty()) {
            // Use an iterator to avoid ConcurrentModificationException when removing
            for (Iterator<Event> iterator = Event.eventsList.iterator(); iterator.hasNext(); ) {
                Event event = iterator.next();
                if (event.getId().equals(eventId)) {
                    iterator.remove(); // Remove the event from the list
                    Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                    return; // Exit the method early
                }
            }
            Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show();
        } else {
            // If we're trying to delete an event that hasn't been saved yet
            Toast.makeText(this, "No event to delete", Toast.LENGTH_SHORT).show();
        }
        finish(); // Optionally close the activity even if no event was deleted
    }


}
