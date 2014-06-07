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

    boolean clicked_sDate1 = false;
    boolean clicked_sDate2 = false;
    boolean clicked_hDate1 = false;
    boolean clicked_hDate2 = false;

    int signUpType;
    private Button finishBtn;
    private ImageView imageView;

    EditText host_date1;
    EditText host_date2;
    EditText student_date1;
    EditText student_date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        signUpType = 1; //init to 1 since default radio button position is on student

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //set on click listener for Image
        // begin imgView stuff -------------------------------------------------------------------------------------------------------
        imageView = (ImageView) findViewById(R.id.img_profile);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener(){
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

        // Set Finish button stuff
        finishBtn.setOnClickListener(new View.OnClickListener() {
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
