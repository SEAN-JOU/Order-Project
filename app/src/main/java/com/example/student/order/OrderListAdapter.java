package com.example.student.order;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class OrderListAdapter extends ArrayAdapter {
    private Activity act;
    private ArrayList<String> orderlist;
    public OrderListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.act=(Activity) context;
        this.orderlist=(ArrayList<String>) objects;
    }

    @Override
    public int getCount() {
        return orderlist.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=act.getLayoutInflater().inflate(R.layout.orderlistviewlayout,parent,false);
        TextView itemName=(TextView)v.findViewById(R.id.itemName);
        itemName.setText(orderlist.get(position));
        //設定隔行背景色
        if(position%2==1){
            itemName.setBackgroundColor(Color.parseColor("#90ff504d"));
        }else {
            itemName.setBackgroundColor(Color.parseColor("#90E6E6E6"));
        }
        return v;
    }

}
