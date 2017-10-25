package com.example.student.order;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ChefExpandListAdapter extends BaseExpandableListAdapter {
    private Activity act;
    private ArrayList<String> ListTitle;
    private HashMap<String,ArrayList<String>> ListItem;
    private CheckBox listContent;
    private HashMap<Integer, boolean[]> CheckStates;
    private viewGroupHolder vgh;
    private viewChildHolder vH;
    private int gp;
    private int cp;

    public ChefExpandListAdapter(Activity activity,ArrayList listTitle,HashMap listItem){
        this.act=activity;
        this.ListTitle=listTitle;
        this.ListItem=listItem;
        CheckStates=new HashMap<Integer, boolean[]>();
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
    public View getChildView(int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        View v=act.getLayoutInflater().inflate(R.layout.cook_list_layout,null);
        vH=new viewChildHolder();
        //listContent=(CheckBox)v.findViewById(R.id.listContent);
        //listContent.setText(ListItem.get(ListTitle.get(groupPosition)).get(childPosition));
        vH.listCB=v.findViewById(R.id.listContent);
        vH.listCB.setText(ListItem.get(ListTitle.get(groupPosition)).get(childPosition));
        //設定隔行背景色
        if(childPosition%2==1){
            vH.listCB.setBackgroundColor(Color.parseColor("#80ff504d"));
        }else {
            vH.listCB.setBackgroundColor(Color.parseColor("#90E6E6E6"));
        }

        gp=groupPosition;
        cp=childPosition;

        vH.listCB.setOnCheckedChangeListener(null);

        if (CheckStates.containsKey(gp)) {
            boolean getChecked[] = CheckStates.get(gp);
            vH.listCB.setChecked(getChecked[cp]);

        } else {
            boolean getChecked[] = new boolean[getChildrenCount(gp)];
            CheckStates.put(gp, getChecked);
            vH.listCB.setChecked(false);
        }

        vH.listCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    boolean getChecked[] =CheckStates.get(gp);
                    getChecked[cp] = isChecked;
                    CheckStates.put(gp, getChecked);
                    vH.listCB.setEnabled(false);
                    Log.d("child","child:"+cp);

                } else {

                    boolean getChecked[] = CheckStates.get(gp);
                    getChecked[cp] = isChecked;
                    CheckStates.put(gp, getChecked);
                }
            }
        });

        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

    class viewGroupHolder{
        TextView listTV;
    }

    class viewChildHolder{
         CheckBox listCB;
    }
