package com.example.app;

/**
 * Created by Chiharu on 6/10/2014.
 */
public class Match {
    private String student;
    private String host;
    private String approved;
    private String id;

    public Match(){
        //basic constructor
    }

    public Match(String student, String host, String approved, String id){
        this.student = student;
        this.host = host;
        this.approved = approved;
        this.id = id;
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
    public void setId(String id){
        this.id = id;
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

    public String getId(){
        return  id;
    }
}
