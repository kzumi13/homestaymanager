package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Admin3Fragment extends Fragment {
    ArrayList<String> hosts = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_admin3, container, false);

        ListView hostLv = (ListView) rootView.findViewById(R.id.listView_aHosts);

        Firebase baseRef = new Firebase("https://popping-fire-8794.firebaseio.com/users");
        baseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Log.d(TAG, "IN STUDENT onDataChange for ");
                for(DataSnapshot data : snapshot.getChildren()) {
                    String name = data.getName();
                    Object valueName = snapshot.child(name).getValue();
                    if(((Map) valueName).get("profileType").equals("host")){
                        hosts.add(name);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println("Listener was cancelled");
            }
        });

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hosts);
        hostLv.setAdapter(listAdapter);

        hostLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.userName = hosts.get(i);
                Intent intent = new Intent(getActivity(), HostActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
