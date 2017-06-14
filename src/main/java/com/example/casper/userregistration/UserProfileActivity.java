package com.example.casper.userregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfileActivity extends AppCompatActivity {
    FirebaseUser user;
    ImageView imageView;
    TextView nameTextView;
    TextView addressTextView;
    Button customerBtn;
    DatabaseReference mRef;
    StorageReference storageReference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nameTextView=(TextView)findViewById(R.id.user_loginname);
        addressTextView=(TextView)findViewById(R.id.user_loginaddress);
        imageView=(ImageView)findViewById(R.id.profile);
        customerBtn=(Button)findViewById(R.id.showCustomerPage);

        //Getting reference for that particular user
        user= FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        if(user!=null){

            //StorageReference picRef=storageReference.child(user.getUid());
            Glide.with(getApplicationContext()).load(Main2Activity.uploadedUri).into(imageView);
            mRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo info=dataSnapshot.getValue(UserInfo.class);
                    nameTextView.setText(info.getName());
                    addressTextView.setText(info.getAddress());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            customerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(UserProfileActivity.this,CustomerPage.class);
                    startActivity(intent);
                }
            });
        }

    }
}
