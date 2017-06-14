package com.example.casper.userregistration;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by CASPER on 5/25/2017.
 */

public class OrderFoodAdapter extends ArrayAdapter<ViewOrderInfo>
{
    Context context;
    ArrayList<ViewOrderInfo> objects;
    ArrayList<ViewOrderInfo> mobjects;
    public OrderFoodAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ViewOrderInfo> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.mobjects=objects;
    }

    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public ViewOrderInfo getItem(int position) {
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.order_layout_view,parent,false);
        }
        ViewOrderInfo viewOrderInfo=(ViewOrderInfo) getItem(position);
        TextView textView=(TextView)convertView.findViewById(R.id.OrderNameView);
        TextView textView1=(TextView)convertView.findViewById(R.id.OrderAddressView);
        TextView textView2=(TextView)convertView.findViewById(R.id.OrderPhoneView);
        TextView textView3=(TextView)convertView.findViewById(R.id.OrderFoodView);
        TextView textView4=(TextView)convertView.findViewById(R.id.OrderPriceView);

        textView.setText(viewOrderInfo.getName());
        textView1.setText(viewOrderInfo.getAddress());
        textView2.setText(viewOrderInfo.getPhone());
        textView3.setText(viewOrderInfo.getFood());
        textView4.setText(viewOrderInfo.getPrice());
        return convertView;
    }
}
