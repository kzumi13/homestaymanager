package com.example.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.Calendar;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HostActivity extends ActionBarActivity implements ActionBar.TabListener {

    ViewPager viewPager = null;
    ActionBar actionBar;

    private static final int REQUEST_CODE= 1;

    //Date picker delcaration
    boolean clicked_date1 = false;
    boolean clicked_date2 = false;

    //Begin Fragment1 Declaration
    private ImageView profilePicIv;
    private EditText nameEtxt;
    private EditText phoneNumberEtxt;
    private EditText emailEtxt;
    private EditText addressEtxt;
    private EditText date1Etxt;
    private EditText date2Etxt;
    private EditText distanceEtxt;

    private CheckBox hide1Cbox;
    private CheckBox hide2Cbox;
    private CheckBox hide3Cbox;

    private CheckBox dogCbox;
    private CheckBox catCbox;
    private CheckBox noPetsCbox;

    private RadioButton smokeYesRbtn;
    private RadioButton smokeNoRbtn;

    private RadioButton gpMaleRbtn;
    private RadioButton gpFemaleRbtn;
    private RadioButton gpNoneRbtn;

    private RadioButton childYesRbtn;
    private RadioButton childNoRbtn;

    private EditText otherInfoEtxt;
    private Button saveBtn;
    private Button undoBtn;

    //Begin Fragment2 Declaration
    private ListView matchesLv;

    //Begin Fragment3 Declaration
    private EditText studentSearchEtxt;
    private ListView studentSearchLv;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        //TODO FIND EVERY MOTHERFUCKER BY ITS GODDAMMED ID!!!!!
        profilePicIv = (ImageView) findViewById(R.id.img_hProfile);
        nameEtxt = (EditText) findViewById(R.id.etxt_hName);
        phoneNumberEtxt = (EditText) findViewById(R.id.etxt_hPhone);
        emailEtxt = (EditText) findViewById(R.id.etxt_hEmail);
        addressEtxt = (EditText) findViewById(R.id.etxt_hAddress);
        distanceEtxt = (EditText) findViewById(R.id.etxt_hDistance);

        hide1Cbox = (CheckBox) findViewById(R.id.cbox_hHide1);
        hide2Cbox = (CheckBox) findViewById(R.id.cbox_hHide2);
        hide3Cbox = (CheckBox) findViewById(R.id.cbox_hHide3);

        dogCbox = (CheckBox) findViewById(R.id.cbox_hDog);
        catCbox = (CheckBox) findViewById(R.id.cbox_hCat);
        noPetsCbox = (CheckBox) findViewById(R.id.cbox_hNoPets);

        smokeYesRbtn = (RadioButton) findViewById(R.id.rbtn_hSmokeYes);
        smokeNoRbtn =  (RadioButton) findViewById(R.id.rbtn_hSmokeNo);

        gpMaleRbtn  = (RadioButton) findViewById(R.id.rbtn_hPrefMale);
        gpFemaleRbtn = (RadioButton) findViewById(R.id.rbtn_hPrefFemale);
        gpNoneRbtn = (RadioButton) findViewById(R.id.rbtn_hNoGenderPref);

        childYesRbtn = (RadioButton) findViewById(R.id.rbtn_hChildYes);
        childNoRbtn = (RadioButton) findViewById(R.id.rbtn_hChildNo);

        otherInfoEtxt = (EditText) findViewById(R.id.etxt_hOtherInfo);
        saveBtn = (Button) findViewById(R.id.btn_hSave);
        undoBtn = (Button) findViewById(R.id.btn_hUndo);

        //Begin Fragment2 Declaration
        matchesLv = (ListView) findViewById(R.id.lv_hMatches);

        //Begin Fragment3 Declaration
        studentSearchEtxt = (EditText) findViewById(R.id.etxt_hStudentSearch);
        studentSearchLv = (ListView) findViewById(R.id.lv_hStudentSearch);


        //BEGIN View Pager stuff --------------------------------------------------------------------------------------------------------------
        viewPager = (ViewPager) findViewById(R.id.hostPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new HostAdapter(fragmentManager));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //NO IMPLEMENTATION NEEDED
            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);  //set action bar position to reflect viewPager position
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //NO IMPLEMENTATION NEEDED
            }
        });

        //Action Bar stuff -----------------------------------------------------------------------------------------------------
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Profile");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("My Matches");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("Student Search");
        tab3.setTabListener(this);

        //Add the tabs
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
    }



    //actionBar functions
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());  //set the viewPager position based upon which tab is selected
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //NO IMPLEMENTATION NEEDED
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //NO IMPLEMENTATION NEED
    }

    //BEGIN date picker stuff ---------------------------------------------------------------------------
    public void hSelectDate1(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(clicked_date2 == false) {
            clicked_date1 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void hSelectDate2(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(clicked_date2 == false) {
            clicked_date2 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }


    public void populateSetDate(int year, int month, int day) {

        date1Etxt = (EditText) findViewById(R.id.etxt_hDate1);
        date2Etxt = (EditText) findViewById(R.id.etxt_hDate2);
        if(clicked_date1) {
            date1Etxt.setText(month + "/" + day + "/" + year);
            System.out.println("First host");
            clicked_date1 = false;
        }
        if (clicked_date2) {
            date2Etxt.setText(month + "/" + day + "/" + year);
            System.out.println("Second host");
            clicked_date2 = false;
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
            populateSetDate(yy, mm + 1, dd);
        }
    }
        //END date picker ------------------------------------------------------------------------------
}

//Make pager adapter class
class HostAdapter extends FragmentStatePagerAdapter
{
    private static final int NUMTABS = 3; //Number of tabes in our Admin activity

    public HostAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        if(position == 0){
            fragment = new Host1Fragment();
        }
        else if(position == 1){
            fragment = new Host2Fragment();
        }
        else if(position == 2){
            fragment = new Host3Fragment();
        }
        return fragment;
    }

    //TODO: return the amount of pages we have
    @Override
    public int getCount() {
        return NUMTABS;
    }


}

