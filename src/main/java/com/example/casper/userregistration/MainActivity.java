package com.example.casper.userregistration;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText emailText;
    EditText passwordText;
    Button registerBtn;
    Button loginBtn;
    TextView regText;
    TextView forgetText;
    Button snap;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        emailText=(EditText)findViewById(R.id.email_text);
        passwordText=(EditText)findViewById(R.id.password_text);
        //registerBtn=(Button)findViewById(R.id.register_btn);
        loginBtn=(Button)findViewById(R.id.loginbtn);
        regText=(TextView)findViewById(R.id.registerText);
        forgetText=(TextView)findViewById(R.id.forgetText);
        snap=(Button)findViewById(R.id.snapbtn);
        database= FirebaseDatabase.getInstance();
        mRef= database.getReference();

        //Adding to fonts to different views
        Typeface custom=Typeface.createFromAsset(getAssets(),"fonts/UnicaOne-Regular.ttf");
        emailText.setTypeface(custom);
        passwordText.setTypeface(custom);
        loginBtn.setTypeface(custom);
        regText.setTypeface(custom);
        forgetText.setTypeface(custom);


        //Login User using information
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

        // Go to Customer registration page
        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationPage.class));
            }
        });


    }


    private void loginAuthenticate(String email, String password) {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        user=FirebaseAuth.getInstance().getCurrentUser();
                        checkUser(user);
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
        mRef.child("registeredUsers").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               UserGroup info= (UserGroup)dataSnapshot.getValue(UserGroup.class);
                if(info.getUsergroup().matches("customer")){
                  Toast.makeText(MainActivity.this,"Successful login",Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(MainActivity.this,CustomersPage.class));
               }
                else{
                   Toast.makeText(MainActivity.this,"Login on the other page",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,RestaurantLoginActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
