package com.example.casper.userregistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {
    ImageView customerImg;
    ImageView restaurantImg;
    TextView textView;
    TextView detailsView;
    TextView customerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        //Assining views to the mainpage
        customerImg=(ImageView)findViewById(R.id.customerImage);
        restaurantImg=(ImageView)findViewById(R.id.restaurantImage);
        textView=(TextView)findViewById(R.id.restaurantText);
        detailsView=(TextView)findViewById(R.id.detailsText);
        customerView=(TextView)findViewById(R.id.customersText);

        //Custom fonts for different textviews
        Typeface custom=Typeface.createFromAsset(getAssets(),"fonts/UnicaOne-Regular.ttf");
        textView.setTypeface(custom);
        detailsView.setTypeface(custom);
        customerView.setTypeface(custom);

        customerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        restaurantImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPageActivity.this,RestaurantLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
