package com.example.rajeshkumarreddy.bealign;

/**
 * Created by Rajeshkumar Reddy on 29-11-2017.
 */

public class Timetable {
    private String time,period;

    public Timetable(){

    }

    public Timetable(String time, String period) {
        this.time = time;
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
