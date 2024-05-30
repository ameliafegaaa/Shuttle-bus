package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BusScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BusScheduleAdapter adapter;
    private List<BusSchedule> busSchedules;

    private Spinner daySpinner;
    private NumberPicker startHourPicker, startMinutePicker, endHourPicker, endMinutePicker;
    private Spinner lokasiAwalSpinner, lokasiAkhirSpinner;

    private boolean isDataVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        recyclerView = findViewById(R.id.recyclerView);
        daySpinner = findViewById(R.id.daySpinner);
        Button searchButton = findViewById(R.id.searchButton);
        startHourPicker = findViewById(R.id.startHourPicker);
        startMinutePicker = findViewById(R.id.startMinutePicker);
        endHourPicker = findViewById(R.id.endHourPicker);
        endMinutePicker = findViewById(R.id.endMinutePicker);
        lokasiAwalSpinner = findViewById(R.id.lokasiawalspinner);
        lokasiAkhirSpinner = findViewById(R.id.lokasiakhirspinner);

        String[] lokasiAwalOptions = {"NBH", "PU"};
        ArrayAdapter<String> lokasiAwalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lokasiAwalOptions);
        lokasiAwalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lokasiAwalSpinner.setAdapter(lokasiAwalAdapter);

        String[] lokasiAkhirOptions = {"NBH", "PU"};
        ArrayAdapter<String> lokasiAkhirAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lokasiAkhirOptions);
        lokasiAkhirAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lokasiAkhirSpinner.setAdapter(lokasiAkhirAdapter);

        startHourPicker.setMinValue(0);
        startHourPicker.setMaxValue(23);
        startHourPicker.setValue(12);

        startMinutePicker.setMinValue(0);
        startMinutePicker.setMaxValue(59);
        startMinutePicker.setValue(0);

        endHourPicker.setMinValue(0);
        endHourPicker.setMaxValue(23);
        endHourPicker.setValue(12);

        endMinutePicker.setMinValue(0);
        endMinutePicker.setMaxValue(59);
        endMinutePicker.setValue(0);

        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(BusScheduleActivity.this, MainActivity.class);
            startActivity(intent);
        });

        busSchedules = generateDummyData();
        adapter = new BusScheduleAdapter(busSchedules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);

        searchButton.setOnClickListener(v -> performSearch());

        Button showAllButton = findViewById(R.id.showAllButton);
        showAllButton.setOnClickListener(v -> showAllData());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void performSearch() {
        String selectedDay = daySpinner.getSelectedItem().toString();
        int startHour = startHourPicker.getValue();
        int startMinute = startMinutePicker.getValue();
        int endHour = endHourPicker.getValue();
        int endMinute = endMinutePicker.getValue();

        String selectedStartLocation = lokasiAwalSpinner.getSelectedItem().toString();
        String selectedEndLocation = lokasiAkhirSpinner.getSelectedItem().toString();

        if (selectedStartLocation.equals(selectedEndLocation)) {
            Toast.makeText(this, "Start and end locations cannot be the same", Toast.LENGTH_SHORT).show();
            return;
        }

        List<BusSchedule> filteredSchedules = new ArrayList<>();
        for (BusSchedule schedule : busSchedules) {
            if (schedule.getDay().equalsIgnoreCase(selectedDay) &&
                    isWithinTimeRange(schedule, startHour, startMinute, endHour, endMinute) &&
                    schedule.getStartLocation().equalsIgnoreCase(selectedStartLocation) &&
                    schedule.getEndLocation().equalsIgnoreCase(selectedEndLocation)) {
                filteredSchedules.add(schedule);
            }
        }

        if (filteredSchedules.isEmpty()) {
            TextView noDataTextView = findViewById(R.id.noDataTextView);
            noDataTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Button showAllButton = findViewById(R.id.showAllButton);
            showAllButton.setVisibility(View.GONE);
        } else {
            TextView noDataTextView = findViewById(R.id.noDataTextView);
            noDataTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            Button showAllButton = findViewById(R.id.showAllButton);
            showAllButton.setVisibility(View.VISIBLE);
        }

        adapter.setBusSchedules(filteredSchedules);
        adapter.notifyDataSetChanged();
    }

    private boolean isWithinTimeRange(BusSchedule schedule, int startHour, int startMinute, int endHour, int endMinute) {
        int scheduleStartMinutes = schedule.getStartHour() * 60 + schedule.getStartMinute();
        int scheduleEndMinutes = schedule.getEndHour() * 60 + schedule.getEndMinute();
        int startTime = startHour * 60 + startMinute;
        int endTime = endHour * 60 + endMinute;
        return scheduleStartMinutes >= startTime && scheduleEndMinutes <= endTime;
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void showAllData() {
        isDataVisible = !isDataVisible;

        TextView noDataTextView = findViewById(R.id.noDataTextView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button showAllButton = findViewById(R.id.showAllButton);

        if (isDataVisible) {
            noDataTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showAllButton.setText("Hide All");
        } else {
            recyclerView.setVisibility(View.GONE);
            noDataTextView.setVisibility(View.GONE);
            showAllButton.setText("Show All");
        }

        adapter.setBusSchedules(busSchedules);
        adapter.notifyDataSetChanged();
    }



    private List<BusSchedule> generateDummyData() {
        List<BusSchedule> schedules = new ArrayList<>();

        schedules.add(new BusSchedule("Thursday", 6, 20, "NBH", 6, 35, "PU"));
        schedules.add(new BusSchedule("Thursday", 6, 30, "NBH", 6, 45, "PU"));
        schedules.add(new BusSchedule("Thursday", 6, 40, "NBH", 6, 55, "PU"));
        schedules.add(new BusSchedule("Thursday", 8, 50, "PU", 9, 5, "NBH"));

        schedules.add(new BusSchedule("Thursday", 9, 0, "NBH", 9, 15, "PU"));
        schedules.add(new BusSchedule("Thursday", 9, 10, "NBH", 9, 25, "PU"));
        schedules.add(new BusSchedule("Thursday", 9, 45, "PU", 10, 0, "NBH"));
        schedules.add(new BusSchedule("Thursday", 10, 0, "PU", 10, 15, "NBH"));
        schedules.add(new BusSchedule("Thursday", 11, 20, "PU", 11, 35, "NBH"));

        schedules.add(new BusSchedule("Thursday", 11, 30, "NBH", 11, 45, "PU"));
        schedules.add(new BusSchedule("Thursday", 11, 40, "NBH", 11, 55, "PU"));

        schedules.add(new BusSchedule("Thursday", 12, 15, "PU", 12, 30, "NBH"));
        schedules.add(new BusSchedule("Thursday", 12, 30, "PU", 12, 45, "NBH"));

        schedules.add(new BusSchedule("Thursday", 14, 0, "PU", 14, 15, "NBH"));
        schedules.add(new BusSchedule("Thursday", 14, 10, "NBH", 14, 25, "PU"));
        schedules.add(new BusSchedule("Thursday", 14, 20, "NBH", 14, 35, "PU"));

        schedules.add(new BusSchedule("Thursday", 15, 0, "PU", 15, 15, "NBH"));
        schedules.add(new BusSchedule("Thursday", 15, 10, "PU", 15, 25, "NBH"));

        schedules.add(new BusSchedule("Friday", 6, 0, "NBH", 6, 15, "PU"));
        schedules.add(new BusSchedule("Friday", 6, 10, "NBH", 6, 25, "PU"));
        schedules.add(new BusSchedule("Friday", 6, 30, "NBH", 6, 45, "PU"));
        schedules.add(new BusSchedule("Friday", 6, 40, "NBH", 6, 55, "PU"));

        schedules.add(new BusSchedule("Friday", 9, 10, "PU", 9, 25, "NBH"));
        schedules.add(new BusSchedule("Friday", 9, 20, "PU", 9, 35, "BH"));
        schedules.add(new BusSchedule("Friday", 12, 50, "PU", 13, 5, "NBH"));
        schedules.add(new BusSchedule("Friday", 13, 0, "NBH", 13, 15, "PU"));
        schedules.add(new BusSchedule("Friday", 13, 10, "NBH", 13, 25, "PU"));

        schedules.add(new BusSchedule("Friday", 16, 30, "PU", 16, 45, "NBH"));
        schedules.add(new BusSchedule("Friday", 17, 0, "PU", 17, 15, "NBH"));

        schedules.add(new BusSchedule("Monday", 6, 20, "NBH", 6, 35, "PU"));
        schedules.add(new BusSchedule("Monday", 6, 30, "NBH", 6, 45, "PU"));
        schedules.add(new BusSchedule("Monday", 6, 40, "NBH", 6, 55, "PU"));
        schedules.add(new BusSchedule("Monday", 8, 50, "PU", 9, 5, "NBH"));

        schedules.add(new BusSchedule("Monday", 9, 0, "NBH", 9, 15, "PU"));
        schedules.add(new BusSchedule("Monday", 9, 10, "NBH", 9, 25, "PU"));
        schedules.add(new BusSchedule("Monday", 9, 45, "PU", 10, 0, "NBH"));
        schedules.add(new BusSchedule("Monday", 10, 0, "PU", 10, 15, "NBH"));
        schedules.add(new BusSchedule("Monday", 11, 20, "PU", 11, 35, "NBH"));

        schedules.add(new BusSchedule("Monday", 11, 30, "NBH", 11, 45, "PU"));
        schedules.add(new BusSchedule("Monday", 11, 40, "NBH", 11, 55, "PU"));

        schedules.add(new BusSchedule("Monday", 12, 15, "PU", 12, 30, "NBH"));
        schedules.add(new BusSchedule("Monday", 12, 30, "PU", 12, 45, "NBH"));

        schedules.add(new BusSchedule("Monday", 14, 0, "PU", 14, 15, "NBH"));
        schedules.add(new BusSchedule("Monday", 14, 10, "NBH", 14, 25, "PU"));
        schedules.add(new BusSchedule("Monday", 14, 20, "NBH", 14, 35, "PU"));

        schedules.add(new BusSchedule("Monday", 15, 0, "PU", 15, 15, "NBH"));
        schedules.add(new BusSchedule("Monday", 15, 10, "PU", 15, 25, "NBH"));


        schedules.add(new BusSchedule("Tuesday", 6, 20, "NBH", 6, 35, "PU"));
        schedules.add(new BusSchedule("Tuesday", 6, 30, "NBH", 6, 45, "PU"));
        schedules.add(new BusSchedule("Tuesday", 6, 40, "NBH", 6, 55, "PU"));
        schedules.add(new BusSchedule("Tuesday", 8, 50, "PU", 9, 5, "NBH"));
        schedules.add(new BusSchedule("Tuesday", 9, 0, "NBH", 9, 15, "PU"));
        schedules.add(new BusSchedule("Tuesday", 9, 10, "NBH", 9, 25, "PU"));
        schedules.add(new BusSchedule("Tuesday", 9, 45, "PU", 10, 0, "NBH"));
        schedules.add(new BusSchedule("Tuesday", 10, 0, "PU", 10, 15, "NBH"));
        schedules.add(new BusSchedule("Tuesday", 11, 20, "PU", 11, 35, "NBH"));

        schedules.add(new BusSchedule("Tuesday", 11, 30, "NBH", 11, 45, "PU"));
        schedules.add(new BusSchedule("Tuesday", 11, 40, "NBH", 11, 55, "PU"));

        schedules.add(new BusSchedule("Tuesday", 12, 15, "PU", 12, 30, "NBH"));
        schedules.add(new BusSchedule("Tuesday", 12, 30, "PU", 12, 45, "NBH"));

        schedules.add(new BusSchedule("Tuesday", 14, 0, "PU", 14, 15, "NBH"));
        schedules.add(new BusSchedule("Tuesday", 14, 10, "NBH", 14, 25, "PU"));
        schedules.add(new BusSchedule("Tuesday", 14, 20, "NBH", 14, 35, "PU"));

        schedules.add(new BusSchedule("Tuesday", 15, 0, "PU", 15, 15, "NBH"));
        schedules.add(new BusSchedule("Tuesday", 15, 10, "PU", 15, 25, "NBH"));

        return schedules;
    }


}
