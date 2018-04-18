package com.sadi.sreda.model;

/**
 * Created by Sadi on 2/16/2018.
 */

public class MyRecordsInfo {

//    "attendance_id": "62",
//            "user_id": "12",
//            "username": "sadi",
//            "check_in_location": "Ktub Vai office",
//            "check_in_time": "2018-04-18 14:59:46",
//            "check_out_location": "Ktub Vai office",
//            "check_out_time": "2018-04-18 14:59:50",
//            "status": "1",
//            "created_by": "0",
//            "created_at": "2018-04-18 14:59:47"

    private String attendance_id,user_id,username,check_in_location,check_in_time,check_out_location,check_out_time,status;

    public String getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCheck_in_location() {
        return check_in_location;
    }

    public void setCheck_in_location(String check_in_location) {
        this.check_in_location = check_in_location;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getCheck_out_location() {
        return check_out_location;
    }

    public void setCheck_out_location(String check_out_location) {
        this.check_out_location = check_out_location;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
