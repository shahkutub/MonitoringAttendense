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

    String user_id,user_name,user_email,username,mobile_no,designations,images;


    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getDesignations() {
        return designations;
    }

    public void setDesignations(String designations) {
        this.designations = designations;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
