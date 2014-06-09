package com.example.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

public class SignUpActivity extends ActionBarActivity {
    //TODO: find a way to save the data
    //TODO: impliment dynamic prefference input

    public static String userName;
    private static final int REQUEST_CODE= 1;

    //date picker stuff
    boolean clicked_sDate1 = false;
    boolean clicked_sDate2 = false;
    boolean clicked_hDate1 = false;
    boolean clicked_hDate2 = false;

    RadioButton host_rbtn;
    RadioButton student_rbtn;
    RadioButton admin_rbtn;

    //Begin basic/admin input declarations-----------------------!
    private ImageView profilePic;
    private EditText userNameEtxt;
    private EditText nameEtxt;
    private EditText emailEtxt;
    private EditText passwordEtxt;
    private RadioButton genderMRbtn;
    private RadioButton genderFRbtn;
    private Button finishBtn;

    //Begin student declarations----------------------------------!
    EditText student_date1;
    EditText student_date2;
    private EditText sdistanceEtxt;
    private CheckBox sdogCbox;
    private CheckBox scatCbox;
    private CheckBox snoPreferenceCbox;
    private CheckBox snoPetsCbox;
    private RadioButton ssmokeYesRBtn;
    private RadioButton ssmokeNoRbtn;
    private  RadioButton schildYesRbtn;
    private RadioButton schildNoRbtn;
    private EditText sotherInfoEtxt;

    //Begin host delcarations---------------------------------!
    EditText host_date1;
    EditText host_date2;
    private EditText hdistanceEtxt;
    private CheckBox hdogCbox;
    private CheckBox hcatCbox;
    private CheckBox hnoPetsCbox;
    private RadioButton hsmokeYesRBtn;
    private RadioButton hsmokeNoRbtn;
    private RadioButton hgpMaleRbtn;
    private RadioButton hgpFemaleRbtn;
    private RadioButton hgpNoneRbtn;
    private RadioButton hchildYesRbtn;
    private RadioButton hchildNoRbtn;
    private EditText hotherInfoEtxt;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //TODO FIND EVERY MOTHERFUCKER BY ITS GODDAMMED ID!!!!!
        //Basic/admin widgets
        userNameEtxt = (EditText) findViewById(R.id.etxt_userName);
        nameEtxt = (EditText) findViewById(R.id.etxt_name);
        emailEtxt = (EditText) findViewById(R.id.etxt_signUpEmail);
        passwordEtxt = (EditText) findViewById(R.id.etxt_password);
        genderMRbtn = (RadioButton) findViewById(R.id.rbtn_male);
        genderFRbtn = (RadioButton) findViewById(R.id.rbtn_female);

        //set on click listener for Image
        // begin imgView stuff -------------------------------------------------------------------------------------------------------
        profilePic = (ImageView) findViewById(R.id.img_profile);
        profilePic.setClickable(true);
        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // END image stuff -----------------------------------------------------------------------------

        // BEGIN finish Button -------------------------------------------------------------
        finishBtn = (Button) findViewById(R.id.btn_finish);
        //Go to new activity based on user input

        student_rbtn = (RadioButton) findViewById(R.id.rbtn_student);
        host_rbtn = (RadioButton) findViewById(R.id.rbtn_host);
        admin_rbtn = (RadioButton) findViewById(R.id.rbtn_admin);

        // Set Finish button stuff ------------------------------------------------------------------------------------------------
        finishBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO: Check to see if info is valid

                Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/users");
                Firebase userRef = baseRef.child(userNameEtxt.getText().toString());

                userName = userNameEtxt.getText().toString();

                userRef.child("name").setValue(nameEtxt.getText().toString());
                userRef.child("email").setValue(emailEtxt.getText().toString());
                userRef.child("password").setValue(passwordEtxt.getText().toString());
                if(genderMRbtn.isChecked())
                    userRef.child("gender").setValue("m");
                else if(genderFRbtn.isChecked())
                    userRef.child("gender").setValue("f");

                //Student account type selected
                if(student_rbtn.isChecked()) {
                    userRef.child("profileType").setValue("student");
                    userRef.child("date1").setValue(student_date1.getText().toString());
                    userRef.child("date2").setValue(student_date2.getText().toString());
                    userRef.child("distance").setValue(sdistanceEtxt.getText().toString());
                    if(sdogCbox.isChecked())
                        userRef.child("dog").setValue("y");
                    else
                        userRef.child("dog").setValue("n");
                    if(scatCbox.isChecked())
                        userRef.child("cat").setValue("y");
                    else
                        userRef.child("cat").setValue("n");
                    if(snoPreferenceCbox.isChecked()) {
                        userRef.child("dog").setValue("idc");
                        userRef.child("cat").setValue("idc");
                    }
                    if(snoPetsCbox.isChecked()){
                        userRef.child("dog").setValue("n");
                        userRef.child("cat").setValue("n");
                    }
                    if(ssmokeYesRBtn.isChecked())
                        userRef.child("smoke").setValue("y");
                    if(ssmokeNoRbtn.isChecked())
                        userRef.child("smoke").setValue("n");
                    if(schildYesRbtn.isChecked())
                        userRef.child("child").setValue("y");
                    if(schildNoRbtn.isChecked())
                        userRef.child("child").setValue("n");
                    userRef.child("info").setValue(sotherInfoEtxt.getText().toString());
                    Toast.makeText(getApplicationContext(), "Student Profile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, StudentActivity.class);
                    startActivity(intent);
                }

                //host account type selected
                else if(host_rbtn.isChecked()) {

                    //userRef.child("date1").setValue(host_date1.getText().toString());
                    //userRef.child("date2").setValue(host_date2.getText().toString());

                    userRef.child("profileType").setValue("host");
                    userRef.child("distance").setValue(hdistanceEtxt.getText().toString());
                    if(hdogCbox.isChecked())
                        userRef.child("dog").setValue("y");
                    else
                        userRef.child("dog").setValue("n");
                    if(hcatCbox.isChecked())
                        userRef.child("cat").setValue("y");
                    else
                        userRef.child("cat").setValue("n");
                    if(hnoPetsCbox.isChecked()){
                        userRef.child("dog").setValue("n");
                        userRef.child("cat").setValue("n");
                    }
                    if(hgpMaleRbtn.isChecked())
                        userRef.child("genderPref").setValue("m");
                    if(hgpFemaleRbtn.isChecked())
                        userRef.child("genderPref").setValue("f");
                    if(hgpNoneRbtn.isChecked())
                        userRef.child("genderPref").setValue("idc");
                    if(hsmokeYesRBtn.isChecked())
                        userRef.child("smoke").setValue("y");
                    if(hsmokeNoRbtn.isChecked())
                        userRef.child("smoke").setValue("n");
                    if(hchildYesRbtn.isChecked())
                        userRef.child("child").setValue("y");
                    if(hchildNoRbtn.isChecked())
                        userRef.child("child").setValue("n");
                    userRef.child("info").setValue(hotherInfoEtxt.getText().toString());

                    Toast.makeText(getApplicationContext(), "Host Profile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, HostActivity.class);
                    startActivity(intent);
                }

                //admin account type selected
                else if(admin_rbtn.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Admin Profile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            }
        });
        // END finish button------------------------------------------------------------------------
    }

    // DATE PICKER SHIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void selectDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(clicked_sDate1 == false) {
            clicked_sDate1 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void selectDate1(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(clicked_sDate2 == false) {
            clicked_sDate2 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void selectDate2(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(!clicked_hDate1) {
            clicked_hDate1 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void selectDate3(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(!clicked_hDate2) {
            clicked_hDate2 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void populateSetDate(int year, int month, int day) {

        host_date1 = (EditText)findViewById(R.id.etxt_date1HU);
        host_date2 = (EditText)findViewById(R.id.etxt_date2HU);
        student_date1 = (EditText)findViewById(R.id.etxt_date1SU);
        student_date2 = (EditText)findViewById(R.id.etxt_date2SU);
        if(clicked_hDate1) {
            host_date1.setText(month + "/" + day + "/" + year);
            System.out.println("First host");
            clicked_hDate1 = false;
        }
        if (clicked_hDate2) {
            host_date2.setText(month + "/" + day + "/" + year);
            System.out.println("Second host");
            clicked_hDate2 = false;
        }
        if(clicked_sDate1) {
            student_date1.setText(month + "/" + day + "/" + year);
            System.out.println("First student");
            clicked_sDate1 = false;
        }
        if(clicked_sDate2) {
            student_date2.setText(month + "/" + day + "/" + year);
            System.out.println("Second student");
            clicked_sDate2 = false;
        }

    }

    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
    }

    // DATE PICKER SHIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //Handles radio button selection --------------------------------------------------------------------------------------------------------------------
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
                        student_date1 = (EditText)findViewById(R.id.etxt_date1SU);
                        student_date2 = (EditText)findViewById(R.id.etxt_date2SU);
                        sdistanceEtxt = (EditText) findViewById(R.id.etxt_distanceSU);
                        sdogCbox = (CheckBox) findViewById(R.id.cbox_dogSU);
                        scatCbox = (CheckBox) findViewById(R.id.cbox_catSU);
                        snoPreferenceCbox = (CheckBox) findViewById(R.id.cbox_catSU);
                        snoPetsCbox = (CheckBox) findViewById(R.id.cbox_catSU);
                        ssmokeYesRBtn = (RadioButton) findViewById(R.id.rbtn_smokeYesSU);
                        ssmokeNoRbtn = (RadioButton) findViewById(R.id.rbtn_smokeNoSU);
                        schildYesRbtn = (RadioButton) findViewById(R.id.rbtn_childYesSU);
                        schildNoRbtn = (RadioButton) findViewById(R.id.rbtn_childNoSU);
                        sotherInfoEtxt = (EditText) findViewById(R.id.etxt_otherInfoSU);
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
                        host_date1 = (EditText) findViewById(R.id.etxt_date1HU);
                        host_date2 = (EditText)findViewById(R.id.etxt_date2HU);
                        hdistanceEtxt = (EditText) findViewById(R.id.etxt_distanceHU);
                        hdogCbox = (CheckBox) findViewById(R.id.cbox_dogHU);
                        hcatCbox = (CheckBox) findViewById(R.id.cbox_catHU);
                        hnoPetsCbox = (CheckBox) findViewById(R.id.cbox_catHU);
                        hsmokeYesRBtn = (RadioButton) findViewById(R.id.rbtn_smokeYesHU);
                        hsmokeNoRbtn = (RadioButton) findViewById(R.id.rbtn_smokeNoHU);
                        hgpMaleRbtn = (RadioButton) findViewById(R.id.rbtn_prefMaleHU);
                        hgpFemaleRbtn = (RadioButton) findViewById(R.id.rbtn_prefFemaleHU);
                        hgpNoneRbtn = (RadioButton) findViewById(R.id.rbtn_noGenderPrefHU);
                        hchildYesRbtn = (RadioButton) findViewById(R.id.rbtn_childYesHU);
                        hchildNoRbtn = (RadioButton) findViewById(R.id.rbtn_childNoHU);
                        hotherInfoEtxt = (EditText) findViewById(R.id.etxt_otherInfoHU);
                    }
                    break;
                }
            case R.id.rbtn_admin:
                if (checked){
                    if(studentPref != null) {
                        //need to save te view
                        View removeStudentPref = findViewById(R.id.student_preferences);
                        ViewGroup parent = (ViewGroup) removeStudentPref.getParent();
                        parent.removeView(removeStudentPref);
                    }
                    if(hostPref != null){
                        View removeHostPref = findViewById(R.id.host_preferences);
                        ViewGroup parent = (ViewGroup) removeHostPref.getParent();
                        parent.removeView(removeHostPref);
                    }
                    break;
                }
        }
    }

    //END radio button selection ----------------------------------------------------------------------------------------------------------------



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
