package com.sadi.sreda.model;

/**
 * Created by Nanosoft-Android on 4/10/2018.
 */

public class LoinResponse {

    String message;

   int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
