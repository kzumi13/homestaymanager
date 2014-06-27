package com.example.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Admin1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Admin1Fragment extends Fragment {

    private EditText nameEtxt;
    private EditText phoneNumberEtxt;
    private EditText emailEtxt;
    private EditText addressEtxt;
    private Button saveBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(MainActivity.loginType.equals("admin"))
            MainActivity.userName = MainActivity.returnName;
        View rootView = inflater.inflate(R.layout.fragment_admin1, container, false);

        saveBtn = (Button) rootView.findViewById(R.id.btn_aSave);
        nameEtxt = (EditText) rootView.findViewById(R.id.etxt_adminName);
        phoneNumberEtxt = (EditText) rootView.findViewById(R.id.etxt_adminPhone);
        emailEtxt = (EditText) rootView.findViewById(R.id.etxt_adminEmail);
        addressEtxt = (EditText) rootView.findViewById(R.id.etxt_adminAddress);

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
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/" + MainActivity.userName);
                userRef.child("name").setValue(nameEtxt.getText().toString());
                userRef.child("email").setValue(emailEtxt.getText().toString());
                userRef.child("address").setValue(addressEtxt.getText().toString());
                userRef.child("phone").setValue(phoneNumberEtxt.getText().toString());
                userRef.child("profileType").setValue("admin");
            }
        });

        return rootView;
    }
}
