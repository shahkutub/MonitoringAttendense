package com.sadi.sreda.model;

/**
 * Created by Sadi on 4/11/2018.
 */

public class LoginData {

//    "loginData": {
//        "user_id": "1",
//                "user_name": "Super Admins",
//                "user_email": "palash.sudip@gmail.com",
//                "username": "admins"
//    }

    String userId,user_name,user_email,username;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
