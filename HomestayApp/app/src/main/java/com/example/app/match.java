package com.example.app;

/**
 * Created by Chiharu on 6/10/2014.
 */
public class Match {
    private String student;
    private String host;
    private String approved;

    public void Match(){
        //basic constructor
    }

    public void Match(String student, String host, String approved){
        this.student = student;
        this.host = host;
        this.approved = approved;
    }

    public void setStudent(String student){
        this.student = student;
    }
    public void setApproved(String approved) {
        this.approved = approved;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getApproved() {
        return approved;
    }

    public String getHost() {
        return host;
    }

    public String getStudent() {
        return student;
    }
}
