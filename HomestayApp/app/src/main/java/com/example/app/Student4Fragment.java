package com.example.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;







public class Student4Fragment extends Fragment {

    ArrayList<String> planetList = new ArrayList<String>();
    View rootView;
    private ListView lv_sPlacementWizard;
    ArrayList<User> hosts = new ArrayList<User>();
    private static final String TAG = "Student4Fragment";

    Button generateBtn;

    //Current User preferences
    String dog;
    String cat;
    String noPets;
    String smoke;
    String distance;
    String child;
    String date1;
    String date2;
    String gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student4, container, false);

        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);


        lv_sPlacementWizard = (ListView) rootView.findViewById( R.id.lv_sPlacementWizard );
        generateBtn = (Button) rootView.findViewById(R.id.btn_generate);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hosts.clear();

                Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object valueName = dataSnapshot.getValue();
                        if (valueName == null) {
                            System.out.println("User doesn't exist |" + MainActivity.userName + "|");
                        } else {
                            dog = (String)((Map)valueName).get("dog");
                            cat = (String)((Map)valueName).get("cat");
                            smoke= (String)((Map)valueName).get("smoke");
                            distance = (String)((Map)valueName).get("distance");
                            child = (String)((Map)valueName).get("child");
                            date1 = (String)((Map)valueName).get("dog");
                            date2 = (String)((Map)valueName).get("dog");
                            gender = (String)((Map)valueName).get("dog");
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                Firebase f = new Firebase("https://popping-fire-8794.firebaseio.com/users");
                f.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for(DataSnapshot snap : snapshot.getChildren())
                        {
                            String username = snap.getName();
                            Object valueName = snapshot.child(username).getValue();
                            String studentType = (String)((Map)valueName).get("profileType");

                            if(studentType.equals("host")) {
                                Log.d(TAG, "HOST: " + username);
                                User aHost = new User();
                                aHost.setUserName(username);
                                aHost.setRating(0);

                                //Set user rating based upon compatibility
                                if(((String)((Map)valueName).get("dog")).equals(dog))
                                    aHost.rating++;
                                if(((String)((Map)valueName).get("cat")).equals(cat))
                                    aHost.rating++;
                                if(((String)((Map)valueName).get("smoke")).equals(smoke))
                                    aHost.rating++;
                                String hDistance = (String) ((Map)valueName).get("distance");
                                if(Integer.parseInt(hDistance) <= Integer.parseInt(distance))
                                    aHost.rating++;
                                if(((String)((Map)valueName).get("child")).equals(child));
                                aHost.rating++;
                                if(((String)((Map)valueName).get("genderPref")).equals(gender));
                                aHost.rating++;
                                Log.d(TAG, "rating: " + aHost.rating);
                                hosts.add(aHost);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                Collections.sort(hosts, new UserComparer());

                HostArrayAdapter myAdapter = new HostArrayAdapter(hosts, getActivity().getApplicationContext());
                lv_sPlacementWizard.setAdapter(myAdapter);
            }
        });

        return rootView;
    }

    private class HostArrayAdapter extends BaseAdapter {
        ArrayList<User> hosts;
        Context ctxt;
        LayoutInflater myInflator;

        public HostArrayAdapter(ArrayList<User> arr, Context c){
            hosts = arr;
            ctxt = c;
            myInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return hosts.size();
        }

        @Override
        public Object getItem(int i) {
            return hosts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if(view == null)
                view = myInflator.inflate(R.layout.wizard_layout, viewGroup, false);
            TextView wizardNameTxt = (TextView)view.findViewById(R.id.txt_wizardName);
            Button wizardBtn = (Button)view.findViewById(R.id.btn_wizard);

            wizardNameTxt.setText(hosts.get(i).getUserName());

            wizardBtn.setOnClickListener(new View.OnClickListener() {
                int numMatches;
                boolean setMatch = false;
                @Override
                public void onClick(View view) {
                    final Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/numMatches");
                    baseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Object value = snapshot.getValue();
                            if (value == null) {
                                System.out.println("User doesn't exist ");
                            } else {
                                Log.d(TAG, "NUMmATCHES parent: " + (String) ((Map) value).get("num"));
                                String myNumMatches = (String) ((Map) value).get("num");
                                numMatches = Integer.parseInt(myNumMatches);
                                setMatch = true;
                                //Long fuckyou = (Integer) ((Map) value).get("num");
                                Log.d(TAG, "numMatches: " + numMatches);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError error) {
                            System.err.println("Listener was cancelled");
                        }
                    });

                    if(setMatch){
                        Log.d(TAG, "numMatchesOut: " + numMatches);
                        baseRef.child("num").setValue("" + (numMatches + 1));

                        Firebase matchRef = new Firebase("https://popping-fire-8794.firebaseio.com/matches/" + (numMatches + 1));
                        matchRef.child("student").setValue(MainActivity.userName);
                        matchRef.child("host").setValue(hosts.get(i).getUserName());
                        matchRef.child("approved").setValue("n");
                        Toast.makeText(getActivity(), "Match Created", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    }

    public class UserComparer implements Comparator<User> {

        @Override
        public int compare(User x, User y) {
            int startComparison = compare(x.rating, y.rating);
            return startComparison;
        }


        private int compare(int a, int b) {
            return a < b ? -1
                    : a > b ? 1
                    : 0;
        }
    }
}
