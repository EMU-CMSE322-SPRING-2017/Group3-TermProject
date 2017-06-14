package com.example.casper.userregistration;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {
    ListView listView;
    DatabaseReference mRef;
    FirebaseDatabase database;
    RestaurantAdapter adapter;
    static ArrayList<RestaurantInfo> restaurantInfos=new ArrayList<RestaurantInfo>();
    static String RESTAURANT_ID;
    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        mRef.child("restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantInfos.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    RestaurantInfo info=data.getValue(RestaurantInfo.class);
                    Toast.makeText(getContext(), info.getName().toString(), Toast.LENGTH_SHORT).show();
                    restaurantInfos.add(info);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_restaurant, container, false);
       listView=(ListView)view.findViewById(R.id.Restaurantlistview);
        adapter=new RestaurantAdapter(getContext(),android.R.layout.simple_expandable_list_item_1,restaurantInfos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantInfo info=(RestaurantInfo)parent.getItemAtPosition(position);
                RESTAURANT_ID=info.getId();
                 startActivity(new Intent(getActivity(),MakeOrderActivity.class));
            }
        });
        return view;
    }

}
