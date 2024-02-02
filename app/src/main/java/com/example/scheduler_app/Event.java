package com.example.scheduler_app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    public static ArrayList<Event> eventsList = new ArrayList<>();

    private String name;
    private LocalDate date;
    private LocalTime time;
    private String description; // New field for event description

    public Event(String name, LocalDate date, LocalTime time, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description; // Set the event description
    }

    // Getter and setter for event description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Existing getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    // Static methods to filter events
    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : eventsList) {
            if (event.getDate().equals(date)) {
                events.add(event);
            }
        }
        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : eventsList) {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if (event.getDate().equals(date) && eventHour == cellHour) {
                events.add(event);
            }
        }
        return events;
    }
}
