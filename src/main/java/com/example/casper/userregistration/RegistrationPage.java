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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPage extends AppCompatActivity {
    Button register;
    Button backToLogin;
    EditText regName;
    EditText regEmail;
    EditText regPass;
    EditText regPhone;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        register=(Button)findViewById(R.id.registerbtn);
        backToLogin=(Button)findViewById(R.id.regBackTologinbtn);
        regName=(EditText)findViewById(R.id.regname_text);
        regEmail=(EditText)findViewById(R.id.regemail_text);
        regPass=(EditText)findViewById(R.id.regpassword_text);
        regPhone=(EditText)findViewById(R.id.regPhone_text);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        mAuth=FirebaseAuth.getInstance();

        //Assigning custom font to textviews
        Typeface custom=Typeface.createFromAsset(getAssets(),"fonts/UnicaOne-Regular.ttf");
        register.setTypeface(custom);
        backToLogin.setTypeface(custom);
        regName.setTypeface(custom);
        regEmail.setTypeface(custom);
        regPass.setTypeface(custom);
        regPhone.setTypeface(custom);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationPage.this,MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=regEmail.getText().toString().trim();
                String password=regPass.getText().toString().trim();
                String name=regName.getText().toString().trim();
                String phone=regPhone.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(getApplicationContext(),"Please enter phone number",Toast.LENGTH_SHORT).show();
                    return;
                }
                register(email,password,name,phone);
            }
        });
    }

    public void register(String email, String password, final String name, final String phone){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user= FirebaseAuth.getInstance().getCurrentUser();
                    mRef.child("users").child(user.getUid()).setValue(new UserInfo(name,"magusa",phone,"customer"));
                    mRef.child("registeredUsers").child(user.getUid()).setValue(new UserGroup("customer"));
                    Toast.makeText(RegistrationPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Log.e("LoginActivity", "Failed Registration", e);
                    Toast.makeText(getApplicationContext(), "Registration failed......"+e.getMessage().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
