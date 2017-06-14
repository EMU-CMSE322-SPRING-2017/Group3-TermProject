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
 * Created by CASPER on 5/25/2017.
 */

public class RestaurantItemsAdapter extends ArrayAdapter<RestaurantItems> {
    Context context;
    ArrayList<RestaurantItems> objects;
    ArrayList<RestaurantItems> mobjects;
    public RestaurantItemsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<RestaurantItems> objects) {
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
    public RestaurantItems getItem(int position) {
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.menu_restaurant_listview,parent,false);
        }

        RestaurantItems restaurantItems=(RestaurantItems) getItem(position);
        TextView textView=(TextView)convertView.findViewById(R.id.MenuTextView);
        TextView textView1=(TextView)convertView.findViewById(R.id.MenuPriceView);

        ImageView imageView=(ImageView)convertView.findViewById(R.id.MenuimageView);
        textView.setText(restaurantItems.getName());
        textView1.setText(restaurantItems.getPrice());
        Glide.with(getContext()).load(restaurantItems.getImg()).into(imageView);
        return convertView;

    }
}
