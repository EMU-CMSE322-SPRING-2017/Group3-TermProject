package com.example.casper.userregistration;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewOrdersFragment extends Fragment {
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    TextView nameOfCustomer;
    TextView addressOfCustomer;
    TextView phoneNumber;
    TextView food;
    TextView price;
     static ViewOrderInfo info;
    public ViewOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_orders, container, false);
        nameOfCustomer=(TextView) view.findViewById(R.id.nameCustomer);
        addressOfCustomer=(TextView) view.findViewById(R.id.addrCustomer);
        phoneNumber=(TextView) view.findViewById(R.id.phoneCustomer);
        food=(TextView) view.findViewById(R.id.foodCustomer);
        price=(TextView) view.findViewById(R.id.priceCustomer);

        //Applying custom fonts to list view
        Typeface custom=Typeface.createFromAsset(getActivity().getAssets(),"fonts/UnicaOne-Regular.ttf");
        nameOfCustomer.setTypeface(custom);
        addressOfCustomer.setTypeface(custom);
        phoneNumber.setTypeface(custom);
        food.setTypeface(custom);
        price.setTypeface(custom);

        //Put the order in the fragment
        mRef.child("order").child("DGMFkEM7JoXsEhSvgvq8NIVqR763").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                info = dataSnapshot.getValue(ViewOrderInfo.class);
                nameOfCustomer.setText(info.getName());
                addressOfCustomer.setText(info.getAddress());
                phoneNumber.setText(info.getPhone());
                food.setText(info.getFood());
                price.setText(info.getPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // Toast.makeText(getContext(),info.getName(), Toast.LENGTH_LONG).show();
    }
}
