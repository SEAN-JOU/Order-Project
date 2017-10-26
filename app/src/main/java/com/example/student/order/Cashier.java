package com.example.student.order;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.graphics.Color.parseColor;
import static android.util.Log.w;


public class Cashier extends AppCompatActivity {
    private static final String TAG = "Cashier";
    private TextView txtNumber, txtName, txtDate;
    TabHost mTabHost;
    private TabHost.TabSpec spec;
    ListView inlistview,outlistview;
    InCashierAdapter incashierAdapter;
    OutCashierAdapter outcashierAdapter;
    ArrayList<Order> out_list,in_list;
    Order out_orderlist,in_orderlist;
    int in_index,out_index;
    int RECODE_IN=1001;
    int RECODE_OUT=1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        findView();
        readSharePreferences();
        readFirebase();
        tabSettings("eatIn",R.id.tabIn,"內用");
        tabSettings("takeAway",R.id.tabOut,"外帶");
        changeTabBackgroundOnChanged();
    }

    public void findView(){

        outlistview=(ListView)findViewById(R.id.inlistview);
        inlistview=(ListView)findViewById(R.id.outlistview);
        txtDate = (TextView) findViewById(R.id.txtDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String strDate = formatter.format(curDate);
        txtDate.setText(strDate);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtName = (TextView) findViewById(R.id.txtName);
        mTabHost = (TabHost)findViewById(R.id.tabhost);
        mTabHost.setup();

        out_list=new ArrayList<>();
        //out_orderlist=new Order("1061025001","王小名",100,3);
        //out_list.add(out_orderlist);
        outcashierAdapter=new OutCashierAdapter(this,out_list);
        outlistview.setAdapter(outcashierAdapter);
        outcashierAdapter.notifyDataSetChanged();
        //外帶類型

        in_list=new ArrayList<>();
        //in_orderlist=new Order("A","1061025002",100,2);
        //in_list.add(in_orderlist);
        //in_orderlist=new Order("B","1061025003",200,2);
        //in_list.add(in_orderlist);
        //in_orderlist=new Order("C","1061025004",300,2);
        //in_list.add(in_orderlist);
        //in_orderlist=new Order("D","1061025005",400,2);
        //in_list.add(in_orderlist);

        incashierAdapter=new InCashierAdapter(this,in_list);
        inlistview.setAdapter(incashierAdapter);
        incashierAdapter.notifyDataSetChanged();
        //內用類型




        inlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int a, long l) {
                in_index=a;
                Order inOrder = in_list.get(in_index);
                Log.w("---in pop---", inOrder.str_Order + "--" + String.valueOf(inOrder.i_money));
                Intent it =new Intent();
                it.setClass(Cashier.this,Menu_context.class);
                startActivityForResult(it,RECODE_IN);

            }});

        outlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                out_index=i;
                Intent it2 =new Intent();
                it2.setClass(Cashier.this,Menu_context.class);
                startActivityForResult(it2,RECODE_OUT);

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

        if(resultCode == RESULT_OK && requestCode == RECODE_OUT){

            int status_out =Integer.valueOf(data.getStringExtra("已結帳"));
            ((Order)out_list.get(out_index)).i_status=status_out;
            outcashierAdapter.notifyDataSetChanged();
        }

        else if(resultCode == RESULT_OK && requestCode == RECODE_IN) {

            int status_in =Integer.valueOf(data.getStringExtra("已結帳"));
            ((Order)in_list.get(in_index)).i_status=status_in;
            incashierAdapter.notifyDataSetChanged();


        }
    }

    //設定tab
    public void tabSettings(String tabTagName, int id,String name){
        spec=mTabHost.newTabSpec(tabTagName);
        spec.setContent(id);
        spec.setIndicator(name);
        mTabHost.addTab(spec);
        setTabBackground();
    }

    //設定tab背景色和高度
    public void setTabBackground(){
        //初次設定
        for(int i=0; i<mTabHost.getTabWidget().getTabCount();i++){
            TextView tv=mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(parseColor("#ffffff"));
            tv.setTextSize(25);
            mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(parseColor("#99fdad0c"));
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 140;
            if(i==0){
                mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99ff504d"));
            }else if(i==1){
                mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(parseColor("#99019e94"));
            }
        }
        TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(parseColor("#000000"));
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(parseColor("#99fdad0c"));
    }

    //切換tab時設定background顏色
    public void changeTabBackgroundOnChanged(){
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabBackground();

            }
        });
    }

    public void readFirebase(){
        final ArrayList<String> strOrderJason = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference order = database.getReference("order");
        order.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String dbOrder = data.getKey(); //訂單編號
                    Log.w("FireBaseTraining", "No = " + dbOrder);
                    String order_string = data.getValue().toString();   //一筆訂單的 Json Data
                    strOrderJason.add(order_string);
                    Log.w("FireBaseTraining", "value = " + order_string);
                }
                for (String strOrder: strOrderJason){
                    Gson gson = new Gson();
                    Order[] orderArray;     //一筆訂單中的 點菜內容
                    orderArray = gson.fromJson(strOrder, Order[].class);
                    if (orderArray[0].str_Flag == 1){
                        in_list.add(orderArray[0]);
                        Log.w("....in....", String.valueOf(orderArray[0].str_Flag));
                    }
                    else {
                        out_list.add(orderArray[0]);
                        Log.w(".....out...", String.valueOf(orderArray[0].str_Flag));
                    }
                }
                incashierAdapter.notifyDataSetChanged();
                outcashierAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}




