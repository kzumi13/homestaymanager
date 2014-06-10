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
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link myMatches.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link myMatches#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class myMatches extends Fragment {

    private ArrayList<Match> matches = new ArrayList<Match>();
    private static final String TAG = "myMatches";

    private ListView myMatchesLv;
    private String profileType;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_matches, container, false);

        myMatchesLv = (ListView) rootView.findViewById(R.id.lv_matches);

        //BEGIN FIREBASE STUFF -------------------------------------------------------------------------------------------------
        final Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value == null) {
                    System.out.println("User julie doesn't exist");
                } else {
                    profileType = (String)((Map)value).get("profileType");
                    userName = (String)((Map)value).get("name");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });

        Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/matches/");
        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    Match aMatch = new Match();
                    aMatch.setStudent(data.child("student").getValue(String.class));
                    aMatch.setHost(data.child("host").getValue(String.class));
                    aMatch.setApproved(data.child("approved").getValue(String.class));
                    matches.add(aMatch);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });
        //END FIREBASE STUFF--------------------------------------------------------------------------------------------

        Log.d(TAG, "Print matches");
        for(Match bleh : matches){
            Log.d(TAG, "INFOR!");
            Log.d(TAG, bleh.getStudent());
        }

        Match match2 = new Match("FAG1", "FAG2", "y");
        Match match3 = new Match("jeerrry", "LOLOL", "y");

        matches.add(match2);
        matches.add(match3);

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
                CheckBox approved = (CheckBox)view.findViewById(R.id.cbox_match);

                student.setText(matches.get(i).getStudent());
                host.setText(matches.get(i).getHost());
                if(matches.get(i).getApproved().equals("y"))
                    approved.setChecked(true);

            return view;
        }
    }
    // ArrayAdapter -------------------------------------------------------------------------------------
}
