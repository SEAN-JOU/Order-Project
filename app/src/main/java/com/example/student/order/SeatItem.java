package com.example.student.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class SeatItem extends AppCompatActivity {
    private TextView tableNumber_seatPage,employeeNumber,employeeName;
    private String tableNo;
    private ScrollFragment seat1,seat2,seat3,seat4;
    private ArrayList<String> OrderList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_item);
        initial();
        getInfo();

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
        seat2.setSeatOrderList(OrderList);
        seat3.setSeatOrderList(OrderList);
        seat4.setSeatOrderList(OrderList);

    }

}
