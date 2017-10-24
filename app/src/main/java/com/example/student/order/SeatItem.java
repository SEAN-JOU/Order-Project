package com.example.student.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class SeatItem extends AppCompatActivity {
    private TextView tableNumber_seatPage,employeeNumber,employeeName;
    private String tableNo;
    private TabHost tabHost_seat;
    private TabHost.TabSpec spec_s;
    private ScrollFragment seat1,seat2,seat3,seat4;
    private ArrayList<String> OrderList=new ArrayList<>();
    private ArrayList<String> OrderList1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_item);
        initial();
        getInfo();
        tabSettings("seat1",R.id.seatTab1,tableNo+"1");
        tabSettings("seat2",R.id.seatTab2,tableNo+"2");
        tabSettings("seat3",R.id.seatTab3,tableNo+"3");
        tabSettings("seat4",R.id.seatTab4,tableNo+"4");
        changeTabBackgroundOnChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setFragmentSeatNumber();
        setFragmentOrderList();
    }

    public void initial(){
        tableNumber_seatPage=(TextView)findViewById(R.id.tableNumber_seatPage);
        employeeNumber=(TextView)findViewById(R.id.employeeNumber);
        employeeName=(TextView)findViewById(R.id.employeeName);
        tabHost_seat = (TabHost)findViewById(R.id.tabHost_seat);
        tabHost_seat.setup();
        seat1=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat1);
        seat2=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat2);
        seat3=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat3);
        seat4=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat4);
    }


    public void getInfo(){
        //取得桌號
        Intent in=getIntent();
        tableNo=in.getStringExtra("tableNo");
        tableNumber_seatPage.setText(tableNo);
        SharedPreferences sp=getSharedPreferences("log",MODE_PRIVATE);
        //取得員工編號
        employeeNumber.setText(sp.getString("id","0000"));
        employeeName.setText(sp.getString("name","Default"));
    }

    //設定tab
    public void tabSettings(String tabTagName,  int id,String name){
        spec_s=tabHost_seat.newTabSpec(tabTagName);
        spec_s.setContent(id);
        spec_s.setIndicator(name);
        tabHost_seat.addTab(spec_s);
        setTabBackground();
    }

    //設定tab背景色和高度
    public void setTabBackground(){
        //初次設定
        for(int i=0; i<tabHost_seat.getTabWidget().getTabCount();i++){
            TextView tv=tabHost_seat.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(parseColor("#ffffff"));
            tv.setTextSize(25);
            tabHost_seat.getTabWidget().getChildAt(tabHost_seat.getCurrentTab()).setBackgroundColor(parseColor("#99fdad0c"));
            tabHost_seat.getTabWidget().getChildAt(i).getLayoutParams().height = 140;
            if(i==0){
                tabHost_seat.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99ff504d"));
            }else if(i==1){
                tabHost_seat.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99019e94"));
            }else if(i==2){
                tabHost_seat.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99ff504d"));
            }else if(i==3){
                tabHost_seat.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99019e94"));
            }
        }
        TextView tv = (TextView)tabHost_seat.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(parseColor("#000000"));
        tabHost_seat.getTabWidget().getChildAt(tabHost_seat.getCurrentTab()).setBackgroundColor(parseColor("#99fdad0c"));
    }

    //切換tab時設定background顏色
    public void changeTabBackgroundOnChanged(){
        tabHost_seat.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabBackground();

            }
        });
    }

    public void setFragmentSeatNumber(){
        seat1.setSeatNumber(tableNo+"1");
        seat2.setSeatNumber(tableNo+"2");
        seat3.setSeatNumber(tableNo+"3");
        seat4.setSeatNumber(tableNo+"4");
    }

    public void setFragmentOrderList(){
        OrderList.add("凱薩沙拉"); //測試用假資料
        OrderList.add("可口可樂");
        OrderList.add("牛排");
        OrderList.add("提拉米蘇");
        OrderList.add("豬肋排");
        OrderList.add("草莓冰沙");
        seat1.setSeatOrderList(OrderList);
        seat2.setSeatOrderList(OrderList1);
        seat3.setSeatOrderList(OrderList);
        seat4.setSeatOrderList(OrderList);
    }



}
