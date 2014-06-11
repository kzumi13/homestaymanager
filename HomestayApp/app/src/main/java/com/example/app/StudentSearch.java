package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentSearch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentSearch#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class StudentSearch extends Fragment {

    private static final String TAG = "StudentSearch";
    private String profileType;
    private ListView studentLv;
    private ArrayList<User> students = new ArrayList<User>();

    private EditText studentSearchEtxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_search, container, false);

        Firebase userRef = new Firebase("https://popping-fire-8794.firebaseio.com/users/");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d(TAG, "IN STUDENT onDataChange");
                Object value = snapshot.child(MainActivity.userName).getValue();
                if (value == null) {
                    System.out.println("User julie doesn't exist");
                } else {
                    Log.d(TAG, (String)((Map)value).get("profileType") + ": SET PROFILETYPE");
                    profileType = (String)((Map)value).get("profileType");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });


        Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/users");
        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d(TAG, "IN STUDENT onDataChange for ");
                for(DataSnapshot data : snapshot.getChildren()) {
                    String name = data.getName();
                    Object valueName = snapshot.child(name).getValue();
                    User aUser = new User();
                    if(((Map) valueName).get("profileType").equals("student")){
                        aUser.setUserName((String) ((Map) valueName).get("name"));
                        aUser.setEmail((String) ((Map) valueName).get("email"));
                        aUser.setAddress((String) ((Map) valueName).get("address"));
                        aUser.setPhone((String) ((Map) valueName).get("address"));
                        aUser.setRating(0);
                        students.add(aUser);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });

        studentLv = (ListView) rootView.findViewById(R.id.lv_studentSearch);
        StudentArrayAdapter myAdapter = new StudentArrayAdapter(students, getActivity().getApplicationContext());
        studentLv.setAdapter(myAdapter);

        studentSearchEtxt = (EditText) rootView.findViewById(R.id.etxt_studentSearch);
        studentSearchEtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i2, int i3) {
                final ArrayList<User> newUsers = new ArrayList<User>();
                students.clear();
                Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/users");
                baseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.d(TAG, "IN STUDENT onDataChange for " + studentSearchEtxt.getText().toString());
                        for(DataSnapshot data : snapshot.getChildren()) {
                            String name = data.getName();
                            Object valueName = snapshot.child(name).getValue();
                            User aUser = new User();
                            if(((Map) valueName).get("profileType").equals("student")){
                                if(((String) ((Map) valueName).get("name")).toLowerCase().contains((studentSearchEtxt.getText().toString()).toLowerCase())) {
                                    aUser.setUserName((String) ((Map) valueName).get("name"));
                                    aUser.setEmail((String) ((Map) valueName).get("email"));
                                    aUser.setAddress((String) ((Map) valueName).get("address"));
                                    aUser.setPhone((String) ((Map) valueName).get("address"));
                                    aUser.setRating(0);
                                    students.add(aUser);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.err.println("Listener was cancelled");
                    }
                });
                StudentArrayAdapter myAdapter = new StudentArrayAdapter(students, getActivity().getApplicationContext());
                studentLv.setAdapter(myAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rootView;
    }
    //ArrayAdapter -------------------------------------------------------------------------------------
    private class StudentArrayAdapter extends BaseAdapter {
        ArrayList<User> Students;
        Context ctxt;
        LayoutInflater myInflator;

        public StudentArrayAdapter(ArrayList<User> arr, Context c){
            Students = arr;
            ctxt = c;
            myInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return Students.size();
        }

        @Override
        public Object getItem(int i) {
            return Students.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null)
                view = myInflator.inflate(R.layout.student_layout, viewGroup, false);
            TextView name = (TextView)view.findViewById(R.id.txt_studentName);
            TextView email = (TextView)view.findViewById(R.id.txt_studentEmail);
            TextView phone = (TextView)view.findViewById(R.id.txt_studentPhone);
            TextView address = (TextView)view.findViewById(R.id.txt_studentAddress);
            Log.d(TAG, "Check: " + i);
            Log.d(TAG, "In ListView: "+ students.get(i).getUserName());
            name.setText(students.get(i).getUserName());
            email.setText(students.get(i).getEmail());
            phone.setText(students.get(i).getPhone());
            address.setText(students.get(i).getAddress());
            return view;
        }
    }
    // ArrayAdapter -------------------------------------------------------------------------------------

}
