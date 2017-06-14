package com.example.casper.userregistration;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CASPER on 5/18/2017.
 */

public class RestaurantAdapter extends ArrayAdapter {
    Context context;
    ArrayList<RestaurantInfo> objects;
    ArrayList<RestaurantInfo> mobjects;
    int resource;
    public RestaurantAdapter( Context context, int resource,  ArrayList<RestaurantInfo> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.mobjects=objects;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.restaurantview,parent,false);
        }
        RestaurantInfo restaurantInfo= (RestaurantInfo) getItem(position);

        TextView textView=(TextView)convertView.findViewById(R.id.restaurantTextView);
        TextView textView1=(TextView)convertView.findViewById(R.id.restaurantAddressView);

        ImageView imageView=(ImageView)convertView.findViewById(R.id.imageViewRestaurant);

        textView.setText(restaurantInfo.getName());
        textView1.setText(restaurantInfo.getAddress());
        Glide.with(getContext()).load(restaurantInfo.icon).into(imageView);
        return convertView;
    }
}
