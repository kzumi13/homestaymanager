package com.example.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;


public class Student3Fragment extends Fragment {

    View rootView;
    ArrayList<String> planetList = new ArrayList<String>();
    private ListView lv_sManualSelection;
    private EditText date1Etxt;
    private EditText date2Etxt;
    private EditText distanceFromCampus;
    private CheckBox cbPPDog;
    private CheckBox cbPPCat;
    private CheckBox cbNoPref;
    private CheckBox cbPPNoPets;
    private RadioButton spYes;
    private RadioButton spNo;
    private RadioButton childrenYes;
    private RadioButton childrenNo;
    private Button filterButton;

    int numMatches;
    boolean setMatch = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student3, container, false);

        lv_sManualSelection = (ListView) rootView.findViewById(R.id.lv_sHosts);
        date1Etxt = (EditText) rootView.findViewById(R.id.etxt_swDate1);
        date2Etxt = (EditText) rootView.findViewById(R.id.etxt_swDate2);
        distanceFromCampus = (EditText) rootView.findViewById(R.id.etxt_swDistance);
        cbPPDog = (CheckBox) rootView.findViewById(R.id.cbox_swDog);
        cbPPCat = (CheckBox) rootView.findViewById(R.id.cbox_swCat);
        cbNoPref = (CheckBox) rootView.findViewById(R.id.cbox_swNoPref);
        cbPPNoPets = (CheckBox) rootView.findViewById(R.id.cbox_swNoPets);
        spYes = (RadioButton) rootView.findViewById(R.id.rbtn_swSmokeYes);
        spNo = (RadioButton) rootView.findViewById(R.id.rbtn_swSmokeNo);
        childrenYes = (RadioButton) rootView.findViewById(R.id.rbtn_swChildYes);
        childrenNo = (RadioButton) rootView.findViewById(R.id.rbtn_swChildNo);
        filterButton = (Button) rootView.findViewById(R.id.btn_filter);

        //FIREBASE -------------------------------------------------------------------------------------------------------
        Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Object value = snapshot.getValue();
                if (value == null) {
                    System.out.println("User julie doesn't exist");
                } else {
                    //Fill in the widgets with the student information
                    //profilePicIv = (ImageView) findViewById(R.id.img_sProfile);
                    date1Etxt.setText((String) ((Map) value).get("date1"));
                    date2Etxt.setText((String) ((Map) value).get("date2"));
                    distanceFromCampus.setText((String) ((Map) value).get("distance"));

                    if (((Map) value).get("dog").equals("yes"))
                        cbPPDog.setChecked(true);
                    if (((Map) value).get("cat").equals("yes"))
                        cbPPCat.setChecked(true);
                    if (((Map) value).get("dog").equals("idc") && ((Map) value).get("cat").equals("idc"))
                        cbNoPref.setChecked(true);
                    if (((String) ((Map) value).get("dog")).equals("n") && ((String) ((Map) value).get("cat")).equals("n"))
                        cbPPNoPets.setChecked(true);
                    if (((Map) value).get("smoke").equals("y"))
                        spYes.setChecked(true);
                    else
                        spNo.setChecked(true);
                    if (((Map) value).get("child").equals("y"))
                        childrenYes.setChecked(true);
                    else
                        childrenNo.setChecked(true);
                }
            }


            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                planetList.clear();
                Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
                userRef.child("date1").setValue(date1Etxt.getText().toString());
                userRef.child("date2").setValue(date2Etxt.getText().toString());
                userRef.child("distance").setValue(distanceFromCampus.getText().toString());
                if (cbPPDog.isChecked())
                    userRef.child("dog").setValue("y");
                else
                    userRef.child("dog").setValue("n");
                if (cbPPCat.isChecked())
                    userRef.child("cat").setValue("y");
                else
                    userRef.child("cat").setValue("n");
                if (cbNoPref.isChecked()) {
                    userRef.child("dog").setValue("idc");
                    userRef.child("cat").setValue("idc");
                }
                if (cbPPNoPets.isChecked()) {
                    userRef.child("dog").setValue("n");
                    userRef.child("cat").setValue("n");
                }
                if (spYes.isChecked())
                    userRef.child("smoke").setValue("y");
                if (spNo.isChecked())
                    userRef.child("smoke").setValue("n");
                if (childrenYes.isChecked())
                    userRef.child("child").setValue("y");
                if (childrenNo.isChecked())
                    userRef.child("child").setValue("n");

                Firebase ref = new Firebase("https://popping-fire-8794.firebaseio.com/users/");
                //Firebase ref = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        Object value = snapshot.getValue();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String userid = snap.getName();

                            Object valueName = snapshot.child(userid).getValue();
                            //String type = (String)((Map)valueName).get("profileType");
                            String type = snap.child("profileType").getValue(String.class);
                            if (!(type.equals("host"))) {
                                continue;
                            }
                            //String startDateHost = (String)((Map)valueName).get("date1");
                            String startDateHost = snap.child("date1").getValue(String.class);
                            //String endDateHost = (String)((Map)valueName).get("date2");
                            String endDateHost = snap.child("date2").getValue(String.class);
                            //String distance = (String)((Map)valueName).get("distance");
                            String distance = snap.child("distance").getValue(String.class);
                            //String dog = (String)((Map)valueName).get("dog");
                            String dog = snap.child("dog").getValue(String.class);
                            //String cat = (String)((Map)valueName).get("cat");
                            String cat = snap.child("cat").getValue(String.class);
                            //String noPref = (String)((Map)valueName).get("dog");
                            String noPref = snap.child("dog").getValue(String.class);
                            //String noPets = (String)((Map)valueName).get("dog");
                            String noPets = snap.child("dog").getValue(String.class);
                            //String smoke = (String)((Map)valueName).get("smoke");
                            String smoke = snap.child("smoke").getValue(String.class);
                            //String child = (String)((Map)valueName).get("child");
                            String child = snap.child("child").getValue(String.class);
                            int x, y;
                            x = Integer.parseInt(distance);
                            y = Integer.parseInt(distanceFromCampus.getText().toString());
                            String startDateStudent = date1Etxt.getText().toString();
                            String endDateStudent = date2Etxt.getText().toString();
                            String[] startDateHost1 = startDateHost.split("/");
                            String[] endDateHost1 = endDateHost.split("/");
                            String[] startDateStudent1 = startDateStudent.split("/");
                            String[] endDateStudent1 = endDateStudent.split("/");
                            /*
                            int startDateHost1m, startDateHost1d, startDateHost1y,
                                    endDateHost1m, endDateHost1d, endDateHost1y,
                                    startDateStudent1m, startDateStudent1d, startDateStudent1y,
                                    endDateStudent1m, endDateStudent1d, endDateStudent1y;

                            startDateHost1m = Integer.parseInt(startDateHost1[0]);
                            startDateHost1d = Integer.parseInt(startDateHost1[1]);
                            startDateHost1y = Integer.parseInt(startDateHost1[2]);
                            endDateHost1m = Integer.parseInt(endDateHost1[0]);
                            endDateHost1d = Integer.parseInt(endDateHost1[1]);
                            endDateHost1y = Integer.parseInt(endDateHost1[2]);
                            startDateStudent1m = Integer.parseInt(startDateStudent1[0]);
                            startDateStudent1d = Integer.parseInt(startDateStudent1[1]);
                            startDateStudent1y = Integer.parseInt(startDateStudent1[2]);
                            endDateStudent1m = Integer.parseInt(endDateStudent1[0]);
                            endDateStudent1d = Integer.parseInt(endDateStudent1[1]);
                            endDateStudent1y = Integer.parseInt(endDateStudent1[2]);
                            */

                            if (type.equals("host")) {
                                System.out.println("A");
                                if ((cbPPDog.isChecked() && dog.equals("y")) ||
                                        (cbPPCat.isChecked() && cat.equals("y")) ||
                                        (cbNoPref.isChecked() && noPref.equals("y")) ||
                                        (cbPPNoPets.isChecked() && dog.equals("n") && cat.equals("n"))) {
                                    System.out.println("B");
                                    if ((spYes.isChecked() && smoke.equals("y")) || (spNo.isChecked() && smoke.equals("n"))) {
                                        System.out.println("C");
                                        if ((childrenYes.isChecked() && child.equals("y")) || (childrenNo.isChecked() && child.equals("n"))) {
                                            System.out.println("D");
                                            if (x <= y) {
                                                System.out.println("E");
                                                System.out.println("H");
                                                planetList.add(userid);
                                                System.out.println("please mang");

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, planetList);
                        lv_sManualSelection.setAdapter(listAdapter);

                        lv_sManualSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                setMatch = false;
                                final Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/numMatches");
                                baseRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        Object value = snapshot.getValue();
                                        if (value == null) {
                                            System.out.println("User doesn't exist ");
                                        } else {
                                            //Log.d(TAG, "NUMmATCHES parent: " + (String) ((Map) value).get("num"));
                                            String myNumMatches = (String) ((Map) value).get("num");
                                            numMatches = Integer.parseInt(myNumMatches);
                                            setMatch = true;
                                            //Long fuckyou = (Integer) ((Map) value).get("num");
                                            //Log.d(TAG, "numMatches: " + numMatches);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError error) {
                                        System.err.println("Listener was cancelled");
                                    }
                                });

                                if (setMatch) {
                                    baseRef.child("num").setValue("" + (numMatches + 1));

                                    Firebase matchRef = new Firebase("https://popping-fire-8794.firebaseio.com/matches/" + (numMatches + 1));
                                    matchRef.child("student").setValue(MainActivity.userName);
                                    matchRef.child("host").setValue(planetList.get(i));
                                    matchRef.child("approved").setValue("n");
                                    Toast.makeText(getActivity(), "Match Created", Toast.LENGTH_SHORT).show();
                                }
                                MainActivity.userName = planetList.get(i);
                                Intent intent = new Intent(getActivity(), HostActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }


        });
        return rootView;
    }
}

