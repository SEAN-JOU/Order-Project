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
    private ArrayList<String> ListTitle;
    private HashMap<String,ArrayList<OrderItems>> ListItem;
    private TextView listContent;
    private TextView listTitle;
    private OrderItems getCh;
    private ArrayList<OrderItems> array_OderItems;



    public ChefExpandListAdapter(Activity activity,ArrayList listTitle,HashMap listItem){
        this.act=activity;
        this.ListTitle=listTitle;
        this.ListItem=listItem;
    }


    @Override
    public int getGroupCount() {
        return ListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ListItem.get(ListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ListItem.get(ListTitle.get(groupPosition)).get(childPosition);
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
        String OrderNmuber =(String)getGroup(groupPosition);
        View v=act.getLayoutInflater().inflate(R.layout.cook_listgroup_layout,null);
        listTitle=(TextView)v.findViewById(R.id.listTitle);
        listTitle.setText(OrderNmuber);

        return v;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        getCh=(OrderItems) getChild(groupPosition,childPosition);
        final String OrderContent=getCh.strItem;
        final String remark=getCh.str_remarks;
        String isNull=null;
        final boolean isCooked=getCh.isCooked;
        array_OderItems=new ArrayList<>();
        array_OderItems.add(getCh);
        View v=act.getLayoutInflater().inflate(R.layout.cook_list_layout,null);
        listContent=(TextView) v.findViewById(R.id.listContent);
        if(remark==isNull){
            listContent.setText(OrderContent);
        }else{
            listContent.setText(OrderContent+"  "+remark);
        }

        //設定隔行背景色
        if(childPosition%2==1){
            listContent.setBackgroundColor(Color.parseColor("#80ff504d"));
        }else {
            listContent.setBackgroundColor(Color.parseColor("#90E6E6E6"));
        }
        //判斷菜單狀態
        if(isCooked){
            listContent.setVisibility(View.GONE);
        }

        //點擊時更改狀態
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.parseColor("#000000"));
                getCh.isCooked=true;
                Log.d("child","child2:"+groupPosition);
                Log.d("child","child3:"+childPosition);
                Log.d("child","child4:"+getCh.isCooked);
            }
        });


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
