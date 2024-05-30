package com.example.project;

public class BusSchedule {
    private String day;
    private int startHour;
    private int startMinute;
    private String startLocation;
    private int endHour;
    private int endMinute;
    private String endLocation;

    public BusSchedule(String day, int startHour, int startMinute, String startLocation, int endHour, int endMinute, String endLocation) {
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.startLocation = startLocation;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.endLocation = endLocation;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
}
