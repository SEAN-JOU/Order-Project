package com.example.student.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Chef extends AppCompatActivity {
    private ExpandableListView cookList;
    private ChefExpandListAdapter adapter;
    private ArrayList<String> cook_listGroup=new ArrayList<>();
    private HashMap<String,ArrayList<String>> cook_listItem=new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        initial();
        setCookList();
    }

    public void initial(){
        cookList=(ExpandableListView)findViewById(R.id.cookList);
    }

    public void setCookList(){
        cook_listGroup.add("10240001");
        cook_listGroup.add("10240002");
        cook_listGroup.add("10240003");
        ArrayList<String> list1=new ArrayList<>();
        list1.add("可樂");
        list1.add("牛排");
        list1.add("凱薩沙拉");
        list1.add("提拉米蘇");
        list1.add("義大利麵");
        list1.add("玉米濃湯");
        list1.add("紅茶");
        list1.add("綠茶");
        cook_listItem.put(cook_listGroup.get(0),list1);
        cook_listItem.put(cook_listGroup.get(1),list1);
        cook_listItem.put(cook_listGroup.get(2),list1);

        adapter=new ChefExpandListAdapter(this,cook_listGroup,cook_listItem);
        cookList.setAdapter(adapter);
        cookList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });
        cookList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return true;
            }
        });




    }


}
