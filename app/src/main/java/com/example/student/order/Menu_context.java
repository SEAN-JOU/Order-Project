package com.example.student.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Menu_context extends AppCompatActivity {

    ListView menuinformation;
    Button checkout,goback;
    TextView total,cashiernumber,waiternumber,Ordernumber,Tablenumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_context);
        menuinformation = (ListView) findViewById(R.id.menuinformation);
        checkout = (Button) findViewById(R.id.checkout);
        goback = (Button) findViewById(R.id.goback);
        total = (TextView) findViewById(R.id.total);
        Tablenumber = (TextView) findViewById(R.id.Tablenumber);
        cashiernumber = (TextView) findViewById(R.id.cashiernumber);
        waiternumber = (TextView) findViewById(R.id.waiternumber);
        Ordernumber = (TextView) findViewById(R.id.Ordernumber);

        total.setText(getIntent().getExtras().getString("total_money"));
        Tablenumber.setText(getIntent().getExtras().getString("order_table"));
        cashiernumber.setText(getIntent().getExtras().getString("check_out"));
        waiternumber.setText(getIntent().getExtras().getString("server"));
        Ordernumber.setText(getIntent().getExtras().getString("order_number"));




        if( getIntent().getExtras().getString("inorout") == null ){

        Gson gson = new Gson();
        OrderItems[] orderArray;     //一筆訂單中的 點菜內容
        orderArray = gson.fromJson(getIntent().getExtras().getString("order_itemString"), OrderItems[].class);
        Gson gson1 = new Gson();
        OrderItems[] orderArray1;     //一筆訂單中的 點菜內容
        orderArray1 = gson1.fromJson(getIntent().getExtras().getString("order_itemString1"), OrderItems[].class);
        Gson gson2 = new Gson();
        OrderItems[] orderArray2;     //一筆訂單中的 點菜內容
        orderArray2 = gson2.fromJson(getIntent().getExtras().getString("order_itemString2"), OrderItems[].class);
        Gson gson3 = new Gson();
        OrderItems[] orderArray3;     //一筆訂單中的 點菜內容
        orderArray3 = gson3.fromJson(getIntent().getExtras().getString("order_itemString3"), OrderItems[].class);

        ArrayList<String> schedule_menu = new ArrayList<>();

        for (int aa = 0; aa < orderArray.length; aa++) {
            String aaa = orderArray[aa].strItem;
            int bbb = orderArray[aa].i_money;
            boolean i1 =orderArray[aa].isCooked;
            if(i1){
                String i2="已出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
            }
            else {
                String i2="未出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
        }}


        for (int aa = 0; aa < orderArray1.length; aa++) {
            String aaa = orderArray1[aa].strItem;
            int bbb = orderArray1[aa].i_money;
            boolean i1 =orderArray[aa].isCooked;
            if(i1){
                String i2="已出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
            }
            else {
                String i2="未出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
        }}

        for (int aa = 0; aa < orderArray2.length; aa++) {
            String aaa = orderArray2[aa].strItem;
            int bbb = orderArray2[aa].i_money;
            boolean i1 =orderArray[aa].isCooked;
            if(i1){
                String i2="已出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
            }
            else {
                String i2="未出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
        }}

        for (int aa = 0; aa < orderArray3.length; aa++) {
            String aaa = orderArray3[aa].strItem;
            int bbb = orderArray3[aa].i_money;
            boolean i1 =orderArray[aa].isCooked;
            if(i1){
                String i2="已出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
            }
            else {
                String i2="未出菜";
                schedule_menu.add(aaa + "  " + bbb + "                  "+i2);
            }}

        OrderListAdapter orderListAdapter = new OrderListAdapter(this, R.layout.orderlistviewlayout, schedule_menu);
        menuinformation.setAdapter(orderListAdapter);

    }
    else
        {
            Gson gson = new Gson();
            OrderItems[] out_orderArray;     //一筆訂單中的 點菜內容
            out_orderArray = gson.fromJson(getIntent().getExtras().getString("out_order_itemString"), OrderItems[].class);


            ArrayList<String> out_schedule_menu = new ArrayList<>();

            for (int aa = 0; aa < out_orderArray.length; aa++) {
                String aaa = out_orderArray[aa].strItem;
                int bbb =out_orderArray[aa].i_money;
                out_schedule_menu.add(aaa + "  " + bbb);
            }

            OrderListAdapter out_orderListAdapter = new OrderListAdapter(this, R.layout.orderlistviewlayout,  out_schedule_menu);
            menuinformation.setAdapter(out_orderListAdapter);
        }




        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it2= new Intent();
                it2.putExtra("已結帳","3");
                it2.putExtra("餐點單號",getIntent().getExtras().getString("order_number"));
                setResult(RESULT_OK,it2);
                finish();


            }});


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }});

    }}

