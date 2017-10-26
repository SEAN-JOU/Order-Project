package com.example.student.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.graphics.Color.parseColor;

public class SeatItem extends AppCompatActivity implements View.OnClickListener {
    private TextView txtOrderNum, tableNumber_seatPage,employeeNumber,employeeName;
    private String tableNo;
    private TabHost tabHost_seat;
    private TabHost.TabSpec spec_s;
    private Button btnSendOrder;
    private ScrollFragment seat1,seat2,seat3,seat4;
    private ArrayList<OrderItems> orderItemsesArr1 = new ArrayList<>();
    private ArrayList<OrderItems> orderItemsesArr2 = new ArrayList<>();
    private ArrayList<OrderItems> orderItemsesArr3 = new ArrayList<>();
    private ArrayList<OrderItems> orderItemsesArr4 = new ArrayList<>();
    private ArrayList<String> order_Iitems_Array1 = new ArrayList<>();
    private ArrayList<String> order_Iitems_Array2 = new ArrayList<>();
    private ArrayList<String> order_Iitems_Array3 = new ArrayList<>();
    private ArrayList<String> order_Iitems_Array4 = new ArrayList<>();
    private Order thisOrder;

    private Gson gson = new Gson();

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
        txtOrderNum = (TextView)findViewById(R.id.txtOrderNum);
        txtOrderNum.setText(getOrderNumber());
        tableNumber_seatPage=(TextView)findViewById(R.id.tableNumber_seatPage);
        employeeNumber=(TextView)findViewById(R.id.employeeNumber);
        employeeName=(TextView)findViewById(R.id.employeeName);
        tabHost_seat = (TabHost)findViewById(R.id.tabHost_seat);
        tabHost_seat.setup();
        seat1=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat1);
        seat2=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat2);
        seat3=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat3);
        seat4=(ScrollFragment)getSupportFragmentManager().findFragmentById(R.id.seat4);
        seat1.getView().setOnClickListener(this);
        seat2.getView().setOnClickListener(this);
        seat3.getView().setOnClickListener(this);
        seat4.getView().setOnClickListener(this);
        btnSendOrder = (Button) findViewById(R.id.btnSendOrder);
        btnSendOrder.setOnClickListener(this);
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
        /*OrderList.add("凱薩沙拉"); //測試用假資料
        OrderList.add("可口可樂");
        OrderList.add("牛排");
        OrderList.add("提拉米蘇");
        OrderList.add("豬肋排");
        OrderList.add("草莓冰沙");*/
        seat1.setSeatOrderList(order_Iitems_Array1);
        seat2.setSeatOrderList(order_Iitems_Array2);
        seat3.setSeatOrderList(order_Iitems_Array3);
        seat4.setSeatOrderList(order_Iitems_Array4);
    }

    public String getOrderNumber(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String strDate = formatter.format(curDate);
        return strDate;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendOrder){
            Order thisOrder = new Order();
            thisOrder.str_Order = getOrderNumber();
            thisOrder.str_Date = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
            thisOrder.str_Flag = 1; //(內用)
            thisOrder.i_table = tableNo;
            thisOrder.people_number = String.valueOf(customer_num());
            thisOrder.i_status = 1; //(訂單成立)
            thisOrder.i_money = Cal_Order_Mondy();
            thisOrder.str_waiter = employeeNumber.getText().toString();
            thisOrder.str_customer = "";
            thisOrder.str_cashier = "";
            thisOrder.str_customer_tel = "";
            ArrayList<ArrayList<OrderItems>> list = new ArrayList();
            list.add(orderItemsesArr1);
            list.add(orderItemsesArr2);
            list.add(orderItemsesArr3);
            list.add(orderItemsesArr4);
            thisOrder.orderItems = list;
            ArrayList<Order> orderArr = new ArrayList<>();
            orderArr.add(thisOrder);
            String OrderStr = gson.toJson(orderArr);
            Log.w("SearItem->", OrderStr);

            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference order_Ref = database.getReference("order").child(thisOrder.str_Order);
            order_Ref.setValue(OrderStr);
            //return;
            finish();
        } else {
            Intent in=new Intent();
            in.putExtra("tableNo", tableNo);
            in.putExtra("seatNo", tableNo + (tabHost_seat.getCurrentTab()+1));
            switch (tabHost_seat.getCurrentTab()){
                case 0:
                    String orderStr1 = gson.toJson(orderItemsesArr1);
                    in.putExtra("itemArray", orderStr1);
                    break;
                case 1:
                    String orderStr2 = gson.toJson(orderItemsesArr2);
                    in.putExtra("itemArray", orderStr2);
                    break;
                case 2:
                    String orderStr3 = gson.toJson(orderItemsesArr3);
                    in.putExtra("itemArray", orderStr3);
                    break;
                case 3:
                    String orderStr4 = gson.toJson(orderItemsesArr4);
                    in.putExtra("itemArray", orderStr4);
                    break;
            }
            in.setClass(this,OrderActivity.class);
            //startActivity(in);
            startActivityForResult(in, 1);
        }
    }

    public String arrayListToJsonString(ArrayList<OrderItems> orderArray){
        //ArrayList ->JSON
        String str = gson.toJson(orderArray);
        return str;
    }

    //計算顧客人數
    public int customer_num(){
        int iNumber = 0;
        if (orderItemsesArr1.size() > 0){
            iNumber++;
        }
        if (orderItemsesArr2.size() > 0){
            iNumber++;
        }
        if (orderItemsesArr3.size() > 0){
            iNumber++;
        }
        if (orderItemsesArr4.size() > 0){
            iNumber++;
        }
        return iNumber;
    }

    public int Cal_Order_Mondy(){
        int money = 0;
        if (orderItemsesArr1.size() > 0){
            for(OrderItems a: orderItemsesArr1){
                money = money + a.i_money;
            }
        }
        if (orderItemsesArr2.size() > 0){
            for(OrderItems b: orderItemsesArr2){
                money = money + b.i_money;
            }
        }
        if (orderItemsesArr3.size() > 0){
            for(OrderItems c: orderItemsesArr3){
                money = money + c.i_money;
            }
        }
        if (orderItemsesArr4.size() > 0){
            for(OrderItems d: orderItemsesArr4){
                money = money + d.i_money;
            }
        }
        return money;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果當初的發的requestCode =1
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            Log.w("ScrollFragment", "點菜內容(OnActivityResult):" + data.getStringExtra("order_itemString"));
            Gson gson = new Gson();
            OrderItems[] order_Iitems = gson.fromJson(data.getStringExtra("order_itemString"), OrderItems[].class);
            switch (tabHost_seat.getCurrentTab()){
                case 0:
                    order_Iitems_Array1.clear();
                    orderItemsesArr1.clear();
                    break;
                case 1:
                    order_Iitems_Array2.clear();
                    orderItemsesArr2.clear();
                    break;
                case 2:
                    order_Iitems_Array3.clear();
                    orderItemsesArr3.clear();
                    break;
                case 3:
                    order_Iitems_Array4.clear();
                    orderItemsesArr4.clear();
                    break;
            }

            for(OrderItems a: order_Iitems){
                Log.w("order->:", a.strItem);
                switch(tabHost_seat.getCurrentTab()){
                    case 0:
                        order_Iitems_Array1.add(a.strItem);
                        orderItemsesArr1.add(a);
                        break;
                    case 1:
                        order_Iitems_Array2.add(a.strItem);
                        orderItemsesArr2.add(a);
                        break;
                    case 2:
                        order_Iitems_Array3.add(a.strItem);
                        orderItemsesArr3.add(a);
                        break;
                    case 3:
                        order_Iitems_Array4.add(a.strItem);
                        orderItemsesArr4.add(a);
                        break;
                }
            }

        }
    }
}
