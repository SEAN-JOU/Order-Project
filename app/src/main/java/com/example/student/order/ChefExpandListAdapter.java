package com.example.student.order;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ChefExpandListAdapter extends BaseExpandableListAdapter {
    private Activity act;
    private ArrayList<Order> OrderList;
    private HashMap<String,ArrayList<OrderItems>> ListItem;
    private TextView listContent;
    private TextView listTitle;
    private OrderItems getCh;
    // private ArrayList<ArrayList<OrderItems>> array_Seat;
    private ArrayList<OrderItems> array_OderItems;


    public ChefExpandListAdapter(Activity activity,ArrayList order){
        this.act=activity;
        this.OrderList=order;
        //this.ListItem=listItem;

    }


    @Override
    public int getGroupCount() {
        return OrderList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count=0;
        for(int i=0;i<OrderList.get(groupPosition).orderItems.size();i++){
            count+=OrderList.get(groupPosition).orderItems.get(i).size();
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return OrderList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return array_OderItems.get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        View v=act.getLayoutInflater().inflate(R.layout.cook_listgroup_layout,null);
        listTitle=(TextView)v.findViewById(R.id.listTitle);
        listTitle.setText(OrderList.get(groupPosition).getI_Order());
        return v;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        array_OderItems=new ArrayList<>();
        View v=act.getLayoutInflater().inflate(R.layout.cook_list_layout,null);
        listContent=(TextView) v.findViewById(R.id.listContent);
        for(int i=0;i<OrderList.get(groupPosition).orderItems.size();i++){
            for(int j=0;j<OrderList.get(groupPosition).orderItems.get(i).size();j++){
                array_OderItems.add(OrderList.get(groupPosition).orderItems.get(i).get(j));
            }
        }
        final String remark=array_OderItems.get(childPosition).str_remarks;
        //設定備註
        if(remark.equals("")){
            listContent.setText(array_OderItems.get(childPosition).strItem);
            Log.d("remark","remark:"+remark);
        }else{
            listContent.setText(array_OderItems.get(childPosition).strItem+"  　備註："+remark);
        }
        //設定隔行背景色
        if(childPosition%2==1){
            listContent.setBackgroundColor(Color.parseColor("#80ff504d"));
        }else {
            listContent.setBackgroundColor(Color.parseColor("#90E6E6E6"));
        }
        //判斷菜單狀態
        if(array_OderItems.get(childPosition).isCooked){
            listContent.setVisibility(View.GONE);
        }
        return v;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}


