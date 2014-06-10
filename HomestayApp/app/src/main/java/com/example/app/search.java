package com.example.app;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Chiharu on 6/10/2014.
 */
public class Search {
    public List<User> users;
    public List<Match> matches;

    public void Search(){
        //basic constructor
    }


    //add all users from the database to the users lists
    public void populateUserList(String fireBaseRef){
        //users stuff
        Firebase baseRef = new Firebase(fireBaseRef);
        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    User myUser = new User();
                    myUser.setUserName(data.child("name").getValue(String.class));
                    myUser.setRating(0);
                    myUser.setUserType(data.child("userType").getValue(String.class));
                    users.add(myUser);
                }
            }


            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    //add all matches from the database to the matches list
    public void populateMatchList(String fireBaseRef){
        Firebase baseRef = new Firebase(fireBaseRef);
        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    Match myMatch = new Match();
                    myMatch.setStudent(data.child("student").getValue(String.class));
                    myMatch.setHost(data.child("student").getValue(String.class));
                    myMatch.setApproved(data.child("approved").getValue(String.class));
                    matches.add(myMatch);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });
    }

}
