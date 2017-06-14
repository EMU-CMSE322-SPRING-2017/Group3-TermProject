package com.example.casper.userregistration;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class ProfileFragment extends Fragment {
    FirebaseUser user;
    TextView nameTextView;
    TextView phoneNumberTextView;
    TextView addressTextView;
    Button edit;
    DatabaseReference mRef;
    FirebaseDatabase database;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        nameTextView=(TextView)view.findViewById(R.id.userName);
        phoneNumberTextView=(TextView) view.findViewById(R.id.userPhone);
        addressTextView=(TextView) view.findViewById(R.id.addressText1Name);
        edit=(Button) view.findViewById(R.id.editInfo);
        if(user!=null){

            mRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo info=dataSnapshot.getValue(UserInfo.class);
                    nameTextView.setText(info.getName());
                    phoneNumberTextView.setText(info.getPhone());
                    addressTextView.setText(info.getAddress());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameTextView.getText().toString();
                String address=addressTextView.getText().toString();
                String phone_no=phoneNumberTextView.getText().toString();
                UserInfo userInfo=new UserInfo(name,address,phone_no,"customer");
                mRef.child("users").child(user.getUid()).setValue(userInfo);
                Toast.makeText(getActivity(),"Submitted",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
