package com.example.casper.userregistration;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantViewMenuFragment extends Fragment {

    ListView listView;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    static String userId;
    static ArrayList<RestaurantItems> restaurantFood=new ArrayList<RestaurantItems>();
    RestaurantItemsAdapter adapter;
    Button food;
    Button drink;
    Button water;
    public RestaurantViewMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        mRef.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantFood.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    RestaurantItems items=(RestaurantItems) data.getValue(RestaurantItems.class);
                    Toast.makeText(getActivity(), items.getName(), Toast.LENGTH_SHORT).show();
                    restaurantFood.add(items);
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_restaurant_view_menu, container, false);
         listView=(ListView) view.findViewById(R.id.menuListview);
        Button food=(Button) view.findViewById(R.id.Foodbtn);
        Button drink=(Button) view.findViewById(R.id.Drinkbtn);
        Button water=(Button) view.findViewById(R.id.Waterbtn);

        // List view
        adapter=new RestaurantItemsAdapter(getContext(),android.R.layout.simple_expandable_list_item_1,restaurantFood);
        listView.setAdapter(adapter);

        //Button actions
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("category").child(user.getUid()).child("food").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        restaurantFood.clear();
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            RestaurantItems items=(RestaurantItems) data.getValue(RestaurantItems.class);
                            Toast.makeText(getActivity(), items.getName(), Toast.LENGTH_SHORT).show();
                            restaurantFood.add(items);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("category").child(user.getUid()).child("drink").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        restaurantFood.clear();
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            RestaurantItems items=(RestaurantItems) data.getValue(RestaurantItems.class);
                            Toast.makeText(getActivity(), items.getName(), Toast.LENGTH_SHORT).show();
                            restaurantFood.add(items);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("category").child(user.getUid()).child("water").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        restaurantFood.clear();
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            RestaurantItems items=(RestaurantItems) data.getValue(RestaurantItems.class);
                            Toast.makeText(getActivity(), items.getName(), Toast.LENGTH_SHORT).show();
                            restaurantFood.add(items);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return view;
    }

}
