package com.example.student.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class Chef extends AppCompatActivity {
    private static final String TAG = "Chef";
    private ExpandableListView cookList;
    private ChefExpandListAdapter adapter;
    private ArrayList<Order> orderList;
    ArrayList<Order> arrShowOrder,arrTmpSaveOrder;
    TreeSet<Integer> ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        initial();
        readFirebase();
        setCookList();

    }

    public void initial() {
        cookList = (ExpandableListView) findViewById(R.id.cookList);
        orderList = new ArrayList<>();
        arrShowOrder=new ArrayList<>();
        arrTmpSaveOrder=new ArrayList<>();
        ts=new TreeSet<>();
    }

    public void setCookList() {
        adapter = new ChefExpandListAdapter(this, arrShowOrder);
        cookList.setAdapter(adapter);
        cookList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                OrderItems oi= (OrderItems)adapter.getChild(groupPosition,childPosition);
                oi.isCooked=true;
                //先把有改出菜狀態的orderitems存入一個暫存的arraylist中
                ArrayList<OrderItems> tmp=new ArrayList<OrderItems>();
                tmp.add(oi);
                Order o = (Order) adapter.getGroup(groupPosition);
                o.i_status=2;
                //把剛剛暫存的arraylist放到這邊修改過出菜狀態的order中
                o.getOrderItems().add(tmp);
                ArrayList<Order> tmpArray=new ArrayList<Order>(); //建立一個暫存陣列
                tmpArray.add(o);   //把修改好的Order物件放入陣列
                Gson gson = new Gson();
                String OrderStr = gson.toJson(tmpArray);  //把陣列轉成json字串
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference order_Ref = database.getReference("order").child(o.getI_Order()); //取得要修改的訂單單號
                order_Ref.setValue(OrderStr);
                Log.w("OrderStr->", OrderStr);
                Log.d("status","status3:"+ o.i_status);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void readFirebase() {
        final ArrayList<String> strOrderJason = new ArrayList<>(); //用來暫時存放jason抓下來的菜單內容
        final FirebaseDatabase database = FirebaseDatabase.getInstance(); //連結firebase
        final DatabaseReference order = database.getReference("order");   //連結資料
        order.addValueEventListener(new ValueEventListener() {      //監聽資料
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                strOrderJason.clear();
                arrShowOrder.clear();
                arrTmpSaveOrder.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot data : dataSnapshot.getChildren()) {  //取得order下的子項目
                    String dbOrder = data.getKey(); //訂單編號
                    String order_string = data.getValue().toString();   //一筆訂單的 Json Data
                    strOrderJason.add(order_string);
                }

                for (String strOrder: strOrderJason) {
                    Gson gson = new Gson();
                    Order[] orderArray;
                    orderArray = gson.fromJson(strOrder, Order[].class); //把strOrderJason的全部資料都丟到Order陣列中
                    orderList.add(orderArray[0]);
                }
                for(int i=0;i<orderList.size();i++){

                    if(orderList.get(i).i_status!=3){
                        Log.d("fire",""+orderList.get(i).str_Order);
                        arrTmpSaveOrder.add(orderList.get(i));
                    }
                }

                for(int i=0;i< arrTmpSaveOrder.size();i++){  //orderItems.size()座位數
                    for(int j=0;j<arrTmpSaveOrder.get(i).orderItems.size();j++){
                        for(int k=0;k<arrTmpSaveOrder.get(i).orderItems.get(j).size();k++) {
                            int index=0;
                            if (arrTmpSaveOrder.get(i).orderItems.get(j).get(k).isCooked != true) {
                                index=i; //判斷如果所點菜品有一項未出菜，就抓該筆序號丟給index
                                ts.add(index);
                            }
                        }
                    }
                }
                Log.d("fire5","hs" +ts.toString());
                for (int x:ts) {  //要放在for迴圈外面這樣才不會重複加了好幾次，用hashset把重複加入的單給排除掉
                    arrShowOrder.add(arrTmpSaveOrder.get(x)); //再用暫存的order Arraylist來取得該項序號的order物件存入arrShowOrder
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });
    }
}

