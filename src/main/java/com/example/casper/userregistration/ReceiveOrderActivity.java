package com.example.casper.userregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReceiveOrderActivity extends AppCompatActivity {
    EditText text1;
    EditText text2;
    EditText text3;
    EditText text4;
    EditText text5;
    Button reply;
    Spinner spinner;
    String result;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order);
        text1=(EditText) findViewById(R.id.Customername_text);
        text2=(EditText) findViewById(R.id.CustomerAddress_text);
        text3=(EditText) findViewById(R.id.CustomerPhone_text);
        text4=(EditText) findViewById(R.id.CustomerFood_text);
        text5=(EditText) findViewById(R.id.CustomerPrice_text);
        reply=(Button) findViewById(R.id.ReplyOrderbtn);
        spinner=(Spinner) findViewById(R.id.spinnerView);
        database=FirebaseDatabase.getInstance();
        mRef= database.getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();

        // Setting up adapter
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.reply_arrays,
                android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ViewOrderInfo info=(ViewOrderInfo) RestaurantViewOrderFragment.ordered;
        text1.setText(info.getName());
        text2.setText(info.getAddress());
        text3.setText(info.getPhone());
        text4.setText(info.getFood());
        text5.setText(info.getPrice());

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text4.setText(result);
                String name=text1.getText().toString();
                String address=text2.getText().toString();
                String phone=text3.getText().toString();
                String food= text4.getText().toString();
                String price=text5.getText().toString();

                mRef.child("order").child(user.getUid()).child(info.customerId).setValue(new ViewOrderInfo(name,address,food,price,phone,info.customerId));
                Toast.makeText(ReceiveOrderActivity.this, "Reply sent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ReceiveOrderActivity.this,RestaurantPageActivity.class));
            }
        });

    }


}
