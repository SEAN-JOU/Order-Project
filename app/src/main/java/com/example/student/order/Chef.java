package com.example.student.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        ArrayList<String> list1=new ArrayList<>();
        list1.add("可樂");
        list1.add("牛排");
        list1.add("凱薩沙拉");
        list1.add("提拉米蘇");
        cook_listItem.put(cook_listGroup.get(0),list1);
        cook_listItem.put(cook_listGroup.get(1),list1);


        adapter=new ChefExpandListAdapter(this,cook_listGroup,cook_listItem);
        cookList.setAdapter(adapter);

    }
}
