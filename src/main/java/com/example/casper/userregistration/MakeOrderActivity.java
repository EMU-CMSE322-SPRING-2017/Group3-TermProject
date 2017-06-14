package com.example.casper.userregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {
    ListView listView;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    static ArrayList<RestaurantItems> restaurantFood=new ArrayList<RestaurantItems>();
    RestaurantItemsAdapter adapter;
    Button food;
    Button drink;
    Button water;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        listView=(ListView) findViewById(R.id.menuCustomerlistview);
        Button food=(Button) findViewById(R.id.foodbtn);
        Button drink=(Button) findViewById(R.id.drinkbtn);
        Button water=(Button) findViewById(R.id.waterbtn);

        // Setting up the adapter
        adapter=new RestaurantItemsAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,restaurantFood);
        mRef.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantFood.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    RestaurantItems items=(RestaurantItems) data.getValue(RestaurantItems.class);
                    Toast.makeText(MakeOrderActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                    restaurantFood.add(items);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setAdapter(adapter);

        //Making an order with the listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantItems item=((RestaurantItems) parent.getItemAtPosition(position));
                ViewOrderInfo info=new ViewOrderInfo("Ahmet","Naviaprks",item.getName(),item.getPrice(),"6790312",user.getUid());
                mRef.child("order").child(item.getRestaurantId()).child(user.getUid()).setValue(info);
                Toast.makeText(MakeOrderActivity.this, "Order Sent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MakeOrderActivity.this,CustomersPage.class));
            }
        });
    }
}
