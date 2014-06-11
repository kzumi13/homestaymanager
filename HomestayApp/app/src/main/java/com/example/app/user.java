package com.example.app;

/**
 * Created by Chiharu on 6/10/2014.
 */
public class User {
    public String userName;
    public int rating; //rating will be used for placement wizard
    public String email;
    public String phone;
    public String address;
    public String userType;
    //comment
    public void User (){
        //basic constructor
    }

    public void User(String userName, int rating, String userType, String email, String address, String phone){
        this.userName = userName;
        this.rating = rating;
        this.userType = userType;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }


    //set
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //get
    public String getUserName() {
        return userName;
    }
    public int getRating() {
        return rating;
    }
    public String getUserType() {
        return userType;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
