package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BusScheduleAdapter extends RecyclerView.Adapter<BusScheduleAdapter.ViewHolder> {

    private List<BusSchedule> busSchedules;

    public BusScheduleAdapter(List<BusSchedule> busSchedules) {
        this.busSchedules = busSchedules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusSchedule schedule = busSchedules.get(position);
        holder.dayTextView.setText(schedule.getDay());

        String startTime = String.format("%02d:%02d", schedule.getStartHour(), schedule.getStartMinute());
        String endTime = String.format("%02d:%02d", schedule.getEndHour(), schedule.getEndMinute());

        holder.timeTextView.setText(startTime + " - " + endTime);
        holder.startLocationTextView.setText(schedule.getStartLocation());
        holder.endLocationTextView.setText(schedule.getEndLocation());
    }

    @Override
    public int getItemCount() {
        return busSchedules.size();
    }

    public void setBusSchedules(List<BusSchedule> filteredSchedules) {
        this.busSchedules = filteredSchedules;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTextView, timeTextView, startLocationTextView, endLocationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            startLocationTextView = itemView.findViewById(R.id.startLocationTextView);
            endLocationTextView = itemView.findViewById(R.id.endLocationTextView);
        }
    }
}
