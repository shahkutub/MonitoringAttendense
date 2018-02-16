package com.sadi.sreda.model;

/**
 * Created by Sadi on 2/16/2018.
 */

public class MyRecordsInfo {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }

    private String clockIn;
    private String clockOut;


}
