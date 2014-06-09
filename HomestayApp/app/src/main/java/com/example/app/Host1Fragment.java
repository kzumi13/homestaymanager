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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Map;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Host1Fragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Host1Fragment#newInstance} factory method to
// * create an instance of this fragment.
// *
// */
public class Host1Fragment extends Fragment {
    View rootView;

    //Begin Fragment1 Declaration
    private ImageView profilePicIv;
    private EditText nameEtxt;
    private EditText phoneNumberEtxt;
    private EditText emailEtxt;
    private EditText addressEtxt;
    private EditText date1Etxt;
    private EditText date2Etxt;
    private Button date1Btn;
    private Button date2Btn;
    private EditText distanceEtxt;
    private CheckBox dogCbox;
    private CheckBox catCbox;
    private CheckBox noPreferenceCbox;
    private CheckBox noPetsCbox;
    private RadioButton smokeYesRbtn;
    private RadioButton smokeNoRbtn;
    private RadioButton gpMaleRbtn;
    private RadioButton gpFemaleRbtn;
    private RadioButton gpNoneRbtn;
    private  RadioButton childYesRbtn;
    private RadioButton childNoRbtn;
    private EditText otherInfoEtxt;
    private Button saveBtn;
    private Button undoBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_host1, container, false);

        profilePicIv = (ImageView) rootView.findViewById(R.id.img_hProfile);
        nameEtxt = (EditText) rootView.findViewById(R.id.etxt_hName);;
        phoneNumberEtxt = (EditText) rootView.findViewById(R.id.etxt_hPhone);;
        emailEtxt = (EditText) rootView.findViewById(R.id.etxt_hEmail);
        addressEtxt = (EditText) rootView.findViewById(R.id.etxt_hAddress);
        date1Etxt = (EditText) rootView.findViewById(R.id.etxt_hDate1);
        date2Etxt = (EditText) rootView.findViewById(R.id.etxt_hDate2);
        distanceEtxt = (EditText) rootView.findViewById(R.id.etxt_hDistance);
        dogCbox  = (CheckBox) rootView.findViewById(R.id.cbox_hDog);
        catCbox  = (CheckBox) rootView.findViewById(R.id.cbox_hCat);
        noPreferenceCbox  = (CheckBox) rootView.findViewById(R.id.cbox_hNoPref);
        noPetsCbox  = (CheckBox) rootView.findViewById(R.id.cbox_hNoPets);
        smokeYesRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hSmokeYes);
        smokeNoRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hSmokeNo);
        gpMaleRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hPrefMale);
        gpFemaleRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hPrefFemale);
        gpNoneRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hNoGenderPref);
        childYesRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hChildYes);
        childNoRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_hChildNo);
        otherInfoEtxt  = (EditText) rootView.findViewById(R.id.etxt_hOtherInfo);
        saveBtn = (Button) rootView.findViewById(R.id.btn_hSave);
        undoBtn = (Button) rootView.findViewById(R.id.btn_hUndo);


        //On Fragment1Student stuff from firebase ---------------------------------------------------------------------------------------
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
                    nameEtxt.setText((String)((Map)value).get("name"));
                    phoneNumberEtxt.setText((String)((Map)value).get("phone"));
                    emailEtxt.setText((String)((Map)value).get("email"));
                    addressEtxt.setText((String)((Map)value).get("address"));
                    date1Etxt.setText((String)((Map)value).get("date1"));
                    date2Etxt.setText((String)((Map)value).get("date2"));
                    distanceEtxt.setText((String)((Map)value).get("distance"));

                    if(((Map)value).get("dog").equals("yes"))
                        dogCbox.setChecked(true);
                    if(((Map)value).get("cat").equals("yes"))
                        catCbox.setChecked(true);
                    if(((Map)value).get("dog").equals("idc") && ((Map)value).get("cat").equals("idc"))
                        noPreferenceCbox.setChecked(true);
                    if(((String)((Map)value).get("dog")).equals("n") && ((String)((Map)value).get("cat")).equals("n"))
                        noPetsCbox.setChecked(true);
                    if(((Map)value).get("smoke").equals("y"))
                        smokeYesRbtn.setChecked(true);
                    else
                        smokeNoRbtn.setChecked(true);
                    if(((Map)value).get("gender").equals("m"))
                        gpMaleRbtn.setChecked(true);
                    if(((Map)value).get("gender").equals("f"))
                        gpFemaleRbtn.setChecked(true);
                    if(((Map)value).get("gender").equals("n"))
                        gpNoneRbtn.setChecked(true);
                    if(((Map)value).get("child").equals("y"))
                        childYesRbtn.setChecked(true);
                    else
                        childNoRbtn.setChecked(true);
                    otherInfoEtxt.setText((String)((Map)value).get("info"));
                }
            }


            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


        //onclick for saveBtn --------------------------------------------------------------------------------------------------
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
                userRef.child("name").setValue(nameEtxt.getText().toString());
                userRef.child("email").setValue(emailEtxt.getText().toString());
                userRef.child("address").setValue(addressEtxt.getText().toString());
                userRef.child("phone").setValue(phoneNumberEtxt.getText().toString());
                userRef.child("profileType").setValue("host");
                userRef.child("date1").setValue(date1Etxt.getText().toString());
                userRef.child("date2").setValue(date2Etxt.getText().toString());
                userRef.child("distance").setValue(distanceEtxt.getText().toString());
                if(dogCbox.isChecked())
                    userRef.child("dog").setValue("y");
                else
                    userRef.child("dog").setValue("n");
                if(catCbox.isChecked())
                    userRef.child("cat").setValue("y");
                else
                    userRef.child("cat").setValue("n");
                if(noPreferenceCbox.isChecked()) {
                    userRef.child("dog").setValue("idc");
                    userRef.child("cat").setValue("idc");
                }
                if(noPetsCbox.isChecked()){
                    userRef.child("dog").setValue("n");
                    userRef.child("cat").setValue("n");
                }
                if(smokeYesRbtn.isChecked())
                    userRef.child("smoke").setValue("y");
                if(smokeNoRbtn.isChecked())
                    userRef.child("smoke").setValue("n");
                if(childYesRbtn.isChecked())
                    userRef.child("child").setValue("y");
                if(childNoRbtn.isChecked())
                    userRef.child("child").setValue("n");
                userRef.child("info").setValue(otherInfoEtxt.getText().toString());
            }
        });

        return  rootView;




        //return inflater.inflate(R.layout.fragment_host1, container, false);
    }

}
