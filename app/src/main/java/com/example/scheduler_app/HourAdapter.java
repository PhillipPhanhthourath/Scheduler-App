package com.example.scheduler_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.util.ArrayList;

public class HourAdapter extends ArrayAdapter<HourEvent> {

    public HourAdapter(@NonNull Context context, ArrayList<HourEvent> hourEvents) {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HourEvent event = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);
        }
        
        setHour(convertView, event.time);
        setEvents(convertView, event.events);
        
        return convertView;
    }

    private void setHour(View convertView, LocalTime time) {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedShortTime(time));
    }

    private void setEvents(View convertView, ArrayList<Event> events) {
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);

        if (events.size() == 0) {
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
        } else if (events.size() == 1) {
            setEvents(event1, events.get(0));
            hideEvent(event2);
            hideEvent(event3);
        } else if (events.size() == 2) {
            setEvents(event1, events.get(0));
            setEvents(event2, events.get(1));
            hideEvent(event3);
        } else if (events.size() == 3) {
            setEvents(event1, events.get(0));
            setEvents(event2, events.get(1));
            setEvents(event3, events.get(2));
        } else {
            setEvents(event1, events.get(0));
            setEvents(event2, events.get(1));
            event3.setVisibility(View.VISIBLE);
            String notShown = String.valueOf(events.size() - 2);
            notShown += " More Events";
            event3.setText(notShown);
        }
    }

    private void setEvents(TextView textView, Event event) {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }

    private void hideEvent(TextView tv) {
        tv.setVisibility(View.INVISIBLE);
    }
}
