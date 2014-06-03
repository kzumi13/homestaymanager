package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SignUpActivity extends ActionBarActivity {
    //TODO: find a way to save the data
    //TODO: impliment dynamic prefference input
    int signUpType;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        signUpType = 1; //init to 1 since default radio button position is on student

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        continueBtn = (Button) findViewById(R.id.btn_finish);
        //Go to new activity based on user input
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Check to see if info is valid
                if(signUpType == 1) {
                    Toast.makeText(getApplicationContext(), "Student Profile", Toast.LENGTH_SHORT).show();
                }
                if(signUpType == 2) {
                    Toast.makeText(getApplicationContext(), "Host Profile", Toast.LENGTH_SHORT).show();
                }
                if(signUpType == 3) {
                    Toast.makeText(getApplicationContext(), "Admin Profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //Handles radio button selection
    public void onRadioButtonClicked(View view){     // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RelativeLayout studentPref = (RelativeLayout) findViewById(R.id.student_preferences);   //Layout for student_preferences
        RelativeLayout hostPref = (RelativeLayout) findViewById(R.id.host_preferences);         //Layout for host_preferences
        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.su_preferences);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbtn_student:
                if (checked){
                    if(hostPref != null){
                        View removeHostPref = findViewById(R.id.host_preferences);
                        ViewGroup parent = (ViewGroup) removeHostPref.getParent();
                        parent.removeView(removeHostPref);
                    }
                    if(studentPref == null) {
                        //Inflate Student Preference Information View
                        View showStudentPref = getLayoutInflater().inflate(R.layout.student_preferences, myLayout, false);
                        myLayout.addView(showStudentPref);
                    }
                    break;
                }
            case R.id.rbtn_host:
                if (checked){
                    if(studentPref != null){
                        //need to save te view
                        View removeStudentPref = findViewById(R.id.student_preferences);
                        ViewGroup parent = (ViewGroup) removeStudentPref.getParent();
                        parent.removeView(removeStudentPref);
                    }
                    if(hostPref == null){
                        View showHostPref = getLayoutInflater().inflate(R.layout.host_preferences, myLayout, false);
                        myLayout.addView(showHostPref);
                    }
                    break;
                }
            case R.id.rbtn_admin:
                if (checked){
                    break;
                }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
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
}
