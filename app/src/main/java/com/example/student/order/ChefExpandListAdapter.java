package com.example.student.order;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ChefExpandListAdapter extends BaseExpandableListAdapter {
    private Activity act;
    private ArrayList<String> ListTitle;
    private HashMap<String,ArrayList<String>> ListItem;
    private CheckBox listContent;

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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        View v=act.getLayoutInflater().inflate(R.layout.cook_listgroup_layout,null);
        TextView listTitle=(TextView)v.findViewById(R.id.listTitle);
        listTitle.setText(ListTitle.get(groupPosition));
        return v;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        View v=act.getLayoutInflater().inflate(R.layout.cook_list_layout,null);
        listContent=(CheckBox)v.findViewById(R.id.listContent);
        listContent.setText(ListItem.get(ListTitle.get(groupPosition)).get(childPosition));
        //設定隔行背景色
        if(childPosition%2==1){
            listContent.setBackgroundColor(Color.parseColor("#80ff504d"));
        }else {
            listContent.setBackgroundColor(Color.parseColor("#90E6E6E6"));
        }
        //使用listener 要抓到被點的child的位置

        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
