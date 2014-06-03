package com.example.app;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StudentActivity extends ActionBarActivity implements ActionBar.TabListener {

    ViewPager viewPager = null;
    ActionBar actionBar;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

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

        //Add the tabs
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
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
}

//Make pager adapter class
class StudentAdapter extends FragmentStatePagerAdapter
{
    private static final int NUMTABS = 2; //Number of tabes in our student activity

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
