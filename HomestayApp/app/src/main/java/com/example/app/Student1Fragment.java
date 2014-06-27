package com.example.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Map;

public class Student1Fragment extends Fragment {

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
    private RadioButton gpYesRbtn;
    private RadioButton gpNoRbtn;
    private RadioButton gpNoneRbtn;
    private EditText allergy;
    private  RadioButton childYesRbtn;
    private RadioButton childNoRbtn;
    private EditText otherInfoEtxt;
    private Button saveBtn;
    private Button undoBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(MainActivity.loginType.equals("student"))
            MainActivity.userName = MainActivity.returnName;
        rootView = inflater.inflate(R.layout.fragment_student1, container, false);

        profilePicIv = (ImageView) rootView.findViewById(R.id.img_sProfile);
        nameEtxt = (EditText) rootView.findViewById(R.id.etxt_sName);;
        phoneNumberEtxt = (EditText) rootView.findViewById(R.id.etxt_sPhone);;
        emailEtxt = (EditText) rootView.findViewById(R.id.etxt_sEmail);
        addressEtxt = (EditText) rootView.findViewById(R.id.etxt_sAddress);
        date1Etxt = (EditText) rootView.findViewById(R.id.etxt_sDate1);
        date2Etxt = (EditText) rootView.findViewById(R.id.etxt_sDate2);
        distanceEtxt = (EditText) rootView.findViewById(R.id.etxt_sDistance);
        dogCbox  = (CheckBox) rootView.findViewById(R.id.cbox_sDog);
        catCbox  = (CheckBox) rootView.findViewById(R.id.cbox_sCat);
        noPreferenceCbox  = (CheckBox) rootView.findViewById(R.id.cbox_sNoPref);
        noPetsCbox  = (CheckBox) rootView.findViewById(R.id.cbox_sNoPets);
        smokeYesRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_sSmokeYes);
        smokeNoRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_sSmokeNo);
        allergy = (EditText) rootView.findViewById(R.id.etxt_sAllergy);
        childYesRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_sChildYes);
        childNoRbtn = (RadioButton) rootView.findViewById(R.id.rbtn_sChildNo);
        otherInfoEtxt  = (EditText) rootView.findViewById(R.id.etxt_sOtherInfo);
        saveBtn = (Button) rootView.findViewById(R.id.btn_sSave);
        undoBtn = (Button) rootView.findViewById(R.id.btn_sUndo);


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
                    allergy.setText((String)((Map)value).get("allergy"));
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
                userRef.child("profileType").setValue("student");
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
                userRef.child("allergy").setValue(allergy.getText().toString());
                if(childYesRbtn.isChecked())
                    userRef.child("child").setValue("y");
                if(childNoRbtn.isChecked())
                    userRef.child("child").setValue("n");
                userRef.child("info").setValue(otherInfoEtxt.getText().toString());
            }
        });

        return  rootView;
    }

    //END date picker Stuff ---------------------------------------------------------------------------------------------------------------------
}
