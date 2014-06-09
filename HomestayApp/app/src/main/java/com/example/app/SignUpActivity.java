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

import java.util.Calendar;

public class SignUpActivity extends ActionBarActivity {
    //TODO: find a way to save the data
    //TODO: impliment dynamic prefference input

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
    private EditText rePasswordEtxt;
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

    private RadioButton hgpYesRbtn;
    private RadioButton hgpNoRbtn;
    private RadioButton hgpNoneRbtn;

    private RadioButton hchildYesRbtn;
    private RadioButton hchildNoRbtn;

    private EditText hotherInfoEtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //TODO FIND EVERY MOTHERFUCKER BY ITS GODDAMMED ID!!!!!
        //Basic/admin widgets
        userNameEtxt = (EditText) findViewById(R.id.etxt_userName);
        nameEtxt = (EditText) findViewById(R.id.etxt_name);
        emailEtxt = (EditText) findViewById(R.id.etxt_signUpEmail);
        passwordEtxt = (EditText) findViewById(R.id.etxt_password);
        rePasswordEtxt = (EditText) findViewById(R.id.etxt_repassword);
        genderMRbtn = (RadioButton) findViewById(R.id.rbtn_male);
        genderFRbtn = (RadioButton) findViewById(R.id.rbtn_female);

        //Student declarations
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

        // Set Finish button stuff
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Check to see if info is valid
                if(student_rbtn.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Student Profile", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, StudentActivity.class);
                    startActivity(intent);

                }
                if(host_rbtn.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Host Profile", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, HostActivity.class);
                    startActivity(intent);

                }
                if(admin_rbtn.isChecked()) {
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
