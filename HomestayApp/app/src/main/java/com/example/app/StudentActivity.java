package com.example.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import java.util.List;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StudentActivity extends ActionBarActivity implements ActionBar.TabListener {

    ViewPager viewPager = null;
    ActionBar actionBar;

    //date picker delcaration
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
    private Button date1Btn;
    private Button date2Btn;

    private EditText distanceEtxt;

    private CheckBox dogCbox;
    private CheckBox catCbox;
    private CheckBox noPreferenceCbox;
    private CheckBox noPetsCbox;

    private RadioButton smokeYesRBtn;
    private RadioButton smokeNoRbtn;

    private RadioButton gpYesRbtn;
    private RadioButton gpNoRbtn;
    private RadioButton gpNoneRbtn;

    private  RadioButton childYesRbtn;
    private RadioButton childNoRbtn;

    private EditText otherInfoEtxt;
    private Button saveBtn;
    private Button undoBtn;

    //Begin Fragmennt2 Declaration
    private ListView myMatchesLv;

    //Begin Fragment3 Delcaration
    private EditText hostSearchEtxt;
    private ListView hostSearchLv;
    private Button filterBtn;
    private Button makeMatchesBtn;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //TODO FIND EVERY MOTHERFUCKER BY ITS GODDAMMED ID!!!!!
        profilePicIv = (ImageView) findViewById(R.id.img_sProfile);
        nameEtxt = (EditText) findViewById(R.id.etxt_sName);;
        phoneNumberEtxt = (EditText) findViewById(R.id.etxt_sPhone);;
        emailEtxt = (EditText) findViewById(R.id.etxt_sEmail);
        addressEtxt = (EditText) findViewById(R.id.etxt_sAddress);
        date1Etxt = (EditText) findViewById(R.id.etxt_sDate1);
        date2Etxt = (EditText) findViewById(R.id.etxt_sDate2);
        distanceEtxt = (EditText) findViewById(R.id.etxt_sDistance);
        dogCbox  = (CheckBox) findViewById(R.id.cbox_sDog);
        catCbox  = (CheckBox) findViewById(R.id.cbox_sCat);
        noPreferenceCbox  = (CheckBox) findViewById(R.id.cbox_sNoPref);
        noPetsCbox  = (CheckBox) findViewById(R.id.cbox_sNoPets);
        smokeYesRBtn = (RadioButton) findViewById(R.id.rbtn_sSmokeYes);
        smokeNoRbtn = (RadioButton) findViewById(R.id.rbtn_sSmokeNo);
        childYesRbtn = (RadioButton) findViewById(R.id.rbtn_sChildYes);
        childNoRbtn = (RadioButton) findViewById(R.id.rbtn_sChildNo);
        otherInfoEtxt  = (EditText) findViewById(R.id.etxt_sOtherInfo);
        saveBtn = (Button) findViewById(R.id.btn_sSave);
        undoBtn = (Button) findViewById(R.id.btn_sUndo);

        //Begin fragment 2 declarations
        myMatchesLv = (ListView)findViewById(R.id.lv_hMatches);

        //Begin Fragment3 Delcaration
        /*
        private EditText hostSearchEtxt;
        private ListView hostSearchLv;
        private Button filterBtn;
        private Button makeMatchesBtn;
        */

        //View Pager stuff
        viewPager = (ViewPager) findViewById(R.id.studentPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new StudentAdapter(fragmentManager));

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

        //Action Bar stuff
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("My Profile");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("My Matches");
        tab2.setTabListener(this);

        ActionBar.Tab tab3= actionBar.newTab();
        tab3.setText("Host Search");
        tab3.setTabListener(this);

        ActionBar.Tab tab4 = actionBar.newTab();
        tab4.setText("Placement Wizard");
        tab4.setTabListener(this);

        //Add the tabs
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.addTab(tab4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
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
    public void studentSelectDate1(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(clicked_date2 == false) {
            clicked_date1 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void studentSelectDate2(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        if(clicked_date2 == false) {
            clicked_date2 = true;
        }
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }


    public void populateSetDate(int year, int month, int day) {

        date1Etxt = (EditText) findViewById(R.id.etxt_sDate1);
        date2Etxt = (EditText) findViewById(R.id.etxt_sDate2);
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
            populateSetDate(yy, mm+1, dd);
        }
        //END date picker ------------------------------------------------------------------------------
}

//Make pager adapter class
class StudentAdapter extends FragmentStatePagerAdapter
{
    private static final int NUMTABS = 4; //Number of tabes in our student activity

    public StudentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        if(position == 0){
            fragment = new Student1Fragment();
        }
        else if(position == 1){
            fragment = new Student2Fragment();
        }
        else if(position == 2){
            fragment = new Student3Fragment();
        }
        else if(position == 3){
            fragment = new Student4Fragment();
        }
        //for extra fragment if needed
        //else if(position == 2){
        //    fragment = new Student3Fragment();
        //}

        return fragment;
    }

    //TODO: return the amount of pages we have
    @Override
    public int getCount() {
        return NUMTABS;
    }
    }



    /*
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "My Profile";
        }
        else if(position == 1){
            return "My Matches";
        }
        return null;
    }
    */
}
