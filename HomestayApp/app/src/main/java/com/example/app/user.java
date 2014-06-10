package com.example.app;

/**
 * Created by Chiharu on 6/10/2014.
 */
public class User {
    public String userName;
    public int rating; //rating will be used for placement wizard
    public String userType;

    public void User (){
        //basic constructor
    }

    public void User(String userName, int rating, String userType){
        this.userName = userName;
        this.rating = rating;
        this.userType = userType;
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
}
