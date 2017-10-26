package com.example.student.order;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by student on 2017/10/24.
 */

public class OutCashierAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<Order> orders = new ArrayList<>();
    String str_status;

    public OutCashierAdapter(Activity activity, ArrayList<Order> orders){
        this.activity=activity;
        this.orders=orders;

    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v =activity.getLayoutInflater().inflate(R.layout.recycle_item,null);

        TextView single=(TextView)v.findViewById(R.id.single);
        TextView clientname=(TextView)v.findViewById(R.id.clientname);
        TextView money=(TextView)v.findViewById(R.id.money);
        TextView status=(TextView)v.findViewById(R.id.status);

        single.setText(orders.get(i).getI_Order());
        clientname.setText(orders.get(i).getStr_customer());
        money.setText(String.valueOf(orders.get(i).getI_money()));
        if(i%2==1){
            v.setBackgroundColor(Color.parseColor("#90ff504d"));
        }else {
            v.setBackgroundColor(Color.parseColor("#90E6E6E6"));
        }

        switch(orders.get(i).i_status){
            case 1:
                str_status="出菜中";
                break;
            case 2:
                str_status="待結帳";
                break;
            case 3:
                str_status="已結帳";
                break;
        }
        status.setText(str_status);
        return v;
    }}

