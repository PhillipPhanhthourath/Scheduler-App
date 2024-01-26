package com.example.scheduler_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CalendarViewHolder extends RecyclerView.ViewHolder {

    public final TextView dayOfMonth;
    public CalendarViewHolder(@NonNull View itemView){
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.Cell_Day_Text);
    }
}
