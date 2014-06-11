package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class myMatches extends Fragment {

    private ArrayList<Match> matches = new ArrayList<Match>();
    private static final String TAG = "myMatches";

    private ListView myMatchesLv;
    private String profileType;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_matches, container, false);
        matches.clear();
        myMatchesLv = (ListView) rootView.findViewById(R.id.lv_matches);

        Log.d(TAG, "in onCreate");
        //BEGIN FIREBASE STUFF -------------------------------------------------------------------------------------------------
        Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d(TAG,  "IN onDataChange");
                Object value = snapshot.child(MainActivity.userName).getValue();
                if (value == null) {
                    System.out.println("User julie doesn't exist");
                } else {
                    Log.d(TAG, (String)((Map)value).get("profileType") + ": SET PROFILETYPE");
                    profileType = (String)((Map)value).get("profileType");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });

        Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/matches");
        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d(TAG, "IN onDataChange for matches");
                for(DataSnapshot data : snapshot.getChildren()) {
                    String name = data.getName();
                    Object valueName = snapshot.child(name).getValue();
                    Match aMatch = new Match();
                    aMatch.setStudent((String) ((Map) valueName).get("student"));
                    aMatch.setHost((String) ((Map) valueName).get("host"));
                    aMatch.setApproved((String) ((Map) valueName).get("approved"));
                    aMatch.setId(data.getName());
                    if(profileType.equals("student")){
                        if(aMatch.getStudent().equals(MainActivity.userName))
                            matches.add(aMatch);
                    }
                    else if (profileType.equals("host")){
                        if(aMatch.getHost().equals(MainActivity.userName))
                            matches.add(aMatch);
                    }
                    else
                        matches.add(aMatch);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });
        //END FIREBASE STUFF--------------------------------------------------------------------------------------------

        MatchArrayAdapter myAdapter = new MatchArrayAdapter(matches, getActivity().getApplicationContext());
        myMatchesLv.setAdapter(myAdapter);

        return rootView;
    }

    //ArrayAdapter -------------------------------------------------------------------------------------
    private class MatchArrayAdapter extends BaseAdapter {
        ArrayList<Match> matches;
        Context ctxt;
        LayoutInflater myInflator;

        public MatchArrayAdapter(ArrayList<Match> arr, Context c){
            matches = arr;
            ctxt = c;
            myInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return matches.size();
        }

        @Override
        public Object getItem(int i) {
            return matches.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null)
                view = myInflator.inflate(R.layout.match_layout, viewGroup, false);

            TextView student = (TextView)view.findViewById(R.id.txt_matchStudent);
            TextView host = (TextView)view.findViewById(R.id.txt_matchHost);
            final CheckBox approved = (CheckBox)view.findViewById(R.id.cbox_match);

            student.setText(matches.get(i).getStudent());
            host.setText(matches.get(i).getHost());
            if(matches.get(i).getApproved().equals("y"))
                approved.setChecked(true);
            final int pos = i;

            if(profileType.equals("admin")) {
                approved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        String id = matches.get(pos).getId();
                        Log.d(TAG, "Check: " + id);
                        Firebase matchRef = new Firebase("https://popping-fire-8794.firebaseio.com/matches/" + id);
                        if (b) {
                            Log.d(TAG, "CheckYES");
                            matchRef.child("approved").setValue("y");
                        } else {
                            Log.d(TAG, "CheckNO");
                            matchRef.child("approved").setValue("n");
                        }
                    }
                });
            }
            else{
                approved.setClickable(false);
            }

            return view;
        }
    }
    // ArrayAdapter -------------------------------------------------------------------------------------
}
