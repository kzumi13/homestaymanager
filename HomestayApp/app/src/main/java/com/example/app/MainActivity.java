package com.example.app;


import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;



public class MainActivity extends ActionBarActivity {

    Fragment    fragment;
    Button      signUpBtn,
                loginBtn;
    EditText    eLoginEtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FIREBASE!------------------------------------------------------------------------------------
        // Create a reference to a Firebase location
        //Firebase ref = new Firebase("https://popping-fire-8794.firebaseio.com/users");

        // Write data to Firebase
        //ref.setValue("we dont love firebase");
        //ref.child("subuser").setValue("please work");

        //Firebase childRef = ref.child("subuser");
        //childRef.child("subsubuser").setValue("value");

        // Read data and react to changes
        /*
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snap) {
                System.out.println(snap.getName() + " -> " + snap.getValue());
            }

            @Override public void onCancelled(FirebaseError error) { }
        });
        */
        //END FIREBASE!-------------------------------------------------------------------------------------

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/

        loginBtn = (Button) findViewById(R.id.btn_login);
        eLoginEtxt = (EditText) findViewById(R.id.etxt_emailLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                //Toast.makeText(getApplicationContext(), eLoginEtxt.getText(), Toast.LENGTH_SHORT).show();
                //TODO: Impliment a way to determine which profile to open up
                //Student Login
                if(eLoginEtxt.getText().equals("student")){
                    Toast.makeText(getApplicationContext(), eLoginEtxt.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                    startActivity(intent);
                }
                //Host login
                else if(eLoginEtxt.getText().equals("host")){
                    Toast.makeText(getApplicationContext(), eLoginEtxt.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HostActivity.class);
                    startActivity(intent);
                }
                //Admin Login
                else if(eLoginEtxt.getText().equals("admin")){
                    Toast.makeText(getApplicationContext(), eLoginEtxt.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                */
                Intent intent = new Intent(MainActivity.this, HostActivity.class);
                startActivity(intent);
            }
        });

        signUpBtn = (Button) findViewById(R.id.btn_signUp);
        //Onclick, we  want to switch to the SignUpActivity
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Sign Up", Toast.LENGTH_SHORT).show();

                //Switch Activities
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }
    }

}
