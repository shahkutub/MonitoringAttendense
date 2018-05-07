package com.sadi.sreda.model;

/**
 * Created by Sadi on 2/16/2018.
 */

public class ExAttanRecordsInfo {
//"type": "Attendance",
//        "created_at": "2018-04-23 21:54:57",
//        "user_id": "12",
//        "username": "sadi",
//        "check_in_location": "Sadi home",
//        "check_in_time": "2018-04-23 21:54:54",
//        "check_out_location": "Sadi home",
//        "check_out_time": "2018-05-05 16:05:40",
//        "user_name": "sadi"

    private String type,check_in_time,check_out_time,status_text;

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }
}
