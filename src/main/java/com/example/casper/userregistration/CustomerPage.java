package com.example.casper.userregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerPage extends AppCompatActivity {
    ListView list;
    SearchView searchView;
    DatabaseReference mRef;
    FirebaseDatabase database;
    Button addBtn;
    RestaurantAdapter adapter;
    static ArrayList<RestaurantInfo> restaurantInfos=new ArrayList<RestaurantInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerpage);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        //searchView=(SearchView) findViewById(R.id.search_shop);
        list=(ListView)findViewById(R.id.restaurantListView);
        addBtn=(Button)findViewById(R.id.addRestaurantBtn);

        //------------------------------TEST CODES-----------------------------------------
        //ArrayList<RestaurantInfo> restaurant=new ArrayList<RestaurantInfo>(Arrays.asList());
        //RestaurantInfo info=new RestaurantInfo("emu","Harput","hjshfjsfhf","6637711");
        //RestaurantInfo info1=new RestaurantInfo("emu2","Harput2","hjshfjsfhf2","66377112");
        //ArrayList<RestaurantInfo> restaurant=new ArrayList<RestaurantInfo>(Arrays.asList(info,info1));
        //restaurantInfos.add(info);
        //restaurantInfos.add(info1);

        adapter=new RestaurantAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,restaurantInfos);
        list.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RestaurantInfo info=new RestaurantInfo("Emuharput","https://firebasestorage.googleapis.com/v0/b/userregistration-7650a.appspot.com/o/NWGAlzj13JfZn5izgTOjzI61CJ83%2Fprofile.jpg?alt=media&token=67daad41-9ff3-4b49-9959-ee4f14e75344","DAU ","6637711");
                //mRef.child("restaurants").child("harput").setValue(info);
            }
        });

        mRef.child("restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantInfos.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    RestaurantInfo info=data.getValue(RestaurantInfo.class);
                    Toast.makeText(CustomerPage.this, info.getName().toString(), Toast.LENGTH_SHORT).show();
                    restaurantInfos.add(info);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
