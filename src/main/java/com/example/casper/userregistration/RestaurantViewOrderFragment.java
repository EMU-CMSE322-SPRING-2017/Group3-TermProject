package com.example.casper.userregistration;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantViewOrderFragment extends Fragment {
    ListView listView;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    static ViewOrderInfo ordered;
    static ArrayList<ViewOrderInfo> orderedFood=new ArrayList<ViewOrderInfo>();
    OrderFoodAdapter adapter;
    public RestaurantViewOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        mRef.child("order").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderedFood.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    ViewOrderInfo info=(ViewOrderInfo) data.getValue(ViewOrderInfo.class);
                    Toast.makeText(getActivity(), info.getName(), Toast.LENGTH_SHORT).show();
                    orderedFood.add(info);
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
        View view=inflater.inflate(R.layout.fragment_restaurant_view_order, container, false);
        listView=(ListView) view.findViewById(R.id.ordersListview);
        adapter=new OrderFoodAdapter(getContext(),android.R.layout.simple_expandable_list_item_1,orderedFood);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ordered=(ViewOrderInfo)parent.getItemAtPosition(position) ;
                Toast.makeText(getActivity(), ordered.getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),ReceiveOrderActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
