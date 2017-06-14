package com.example.casper.userregistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantLoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText emailText;
    EditText passwordText;
    Button loginBtn;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_login);

        mAuth=FirebaseAuth.getInstance();
        emailText=(EditText)findViewById(R.id.Restaurantemail_text);
        passwordText=(EditText)findViewById(R.id.Restaurantpassword_text);
        loginBtn=(Button) findViewById(R.id.Restaurantloginbtn);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();

        //Adding to fonts to different views
        Typeface custom=Typeface.createFromAsset(getAssets(),"fonts/UnicaOne-Regular.ttf");
        emailText.setTypeface(custom);
        passwordText.setTypeface(custom);
        loginBtn.setTypeface(custom);

        //Login Restaurant using information
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailText.getText().toString().trim();
                String password=passwordText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                loginAuthenticate(email,password);
            }
        });
    }
    private void loginAuthenticate(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(RestaurantLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    user=FirebaseAuth.getInstance().getCurrentUser();
                    //Toast.makeText(RestaurantLoginActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                    checkUser(user);
                    //Toast.makeText(RestaurantLoginActivity.this,"Successful login",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(RestaurantLoginActivity.this,CustomersPage.class));

                }
                else {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Log.e("LoginActivity", "Login Failed", e);
                    Toast.makeText(getApplicationContext(), "Login failed...... "+e.getMessage().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void checkUser(FirebaseUser user){
         final String rest="restaurant";
         mRef.child("registeredUsers").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 UserGroup info=(UserGroup) dataSnapshot.getValue(UserGroup.class);
                 if(info.usergroup.matches(rest)){
                     Toast.makeText(RestaurantLoginActivity.this,"Successful login",Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(RestaurantLoginActivity.this,RestaurantPageActivity.class));
                 }
                 else {
                     Toast.makeText(RestaurantLoginActivity.this, "Login on this page", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(RestaurantLoginActivity.this, MainActivity.class));
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

    }
}
