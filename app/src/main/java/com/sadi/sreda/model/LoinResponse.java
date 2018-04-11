package com.sadi.sreda.model;

/**
 * Created by Nanosoft-Android on 4/10/2018.
 */

public class LoinResponse {

    String message;

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    int status;

   private LoginData loginData = new LoginData();



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
