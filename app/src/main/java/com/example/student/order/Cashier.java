package com.example.student.order;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Cashier extends AppCompatActivity {
    private TextView txtNumber, txtName, txtDate;
    TabHost mTabHost;
    ListView inlistview,outlistview;
    InCashierAdapter incashierAdapter,adapter;
    OutCashierAdapter outcashierAdapter;
    ArrayList<Order> out_list,in_list;
    Order out_orderlist,in_orderlist;
    int in_index,out_index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        findView();
        readSharePreferences();

        mTabHost = (TabHost)findViewById(R.id.tabhost);
        mTabHost.setup();

        TabHost.TabSpec tab1 = mTabHost.newTabSpec("0");
        tab1.setIndicator("外帶");
        tab1.setContent(R.id.tab1);
        mTabHost.addTab(tab1);

        mTabHost.addTab(mTabHost.newTabSpec("1")
                .setIndicator("內用")
                .setContent(R.id.tab2));
    }

    public void findView(){

        inlistview=(ListView)findViewById(R.id.inlistview);
        outlistview=(ListView)findViewById(R.id.outlistview);
        txtDate = (TextView) findViewById(R.id.txtDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String strDate = formatter.format(curDate);
        txtDate.setText(strDate);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtName = (TextView) findViewById(R.id.txtName);

        out_list=new ArrayList<>();
        out_orderlist=new Order(10,"王",1,1);
        out_list.add(out_orderlist);
        outcashierAdapter=new OutCashierAdapter(this,out_list);
        outlistview.setAdapter(outcashierAdapter);
        outcashierAdapter.notifyDataSetChanged();
        //外帶類型

        in_list=new ArrayList<>();
        in_orderlist=new Order("Ａ",1,1,1);
        in_list.add(in_orderlist);
        in_orderlist=new Order("Ａ",1,1,1);
        in_list.add(in_orderlist);
        in_orderlist=new Order("Ａ",1,1,1);
        in_list.add(in_orderlist);
        in_orderlist=new Order("Ａ",1,1,1);
        in_list.add(in_orderlist);
        in_orderlist=new Order("Ａ",1,1,1);
        in_list.add(in_orderlist);
        in_orderlist=new Order("Ａ",1,1,1);
        in_list.add(in_orderlist);

        incashierAdapter=new InCashierAdapter(this,in_list);
        inlistview.setAdapter(incashierAdapter);
        incashierAdapter.notifyDataSetChanged();
        //內用類型




        inlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int a, long l) {

                in_index=a;
                Intent it =new Intent();
                it.setClass(Cashier.this,Menu_context.class);
                startActivityForResult(it,101);

            return true;
            }});

        outlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                out_index=i;
                Intent it =new Intent();
                it.setClass(Cashier.this,Menu_context.class);
                startActivityForResult(it,100);

                return true;
            }});


    }

    public void readSharePreferences(){
        SharedPreferences sp = getSharedPreferences("log",MODE_PRIVATE);
        txtNumber.setText(sp.getString("id","0000").toString());
        txtName.setText(sp.getString("name", "Default").toString());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 100){

           int status_out =Integer.valueOf(data.getStringExtra("已結帳"));
            ((Order)out_list.get(out_index)).i_status=status_out;
            outcashierAdapter.notifyDataSetChanged();
        }

         else if(resultCode == RESULT_OK && requestCode == 101) {

            int status_in =Integer.valueOf(data.getStringExtra("已結帳"));
            ((Order)in_list.get(in_index)).i_status=status_in;
            incashierAdapter.notifyDataSetChanged();


        }}}





