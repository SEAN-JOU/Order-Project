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
import java.util.HashMap;

public class Chef extends AppCompatActivity {
    private static final String TAG = "Chef";
    private ExpandableListView cookList;
    private ChefExpandListAdapter adapter;
    private HashMap<String, ArrayList<OrderItems>> cook_listItem;
    private OrderItems mealContent;
    private ArrayList<Order> orderList;
    ArrayList<Order> arrShowOrder;
    private ArrayList<OrderItems> MealContent;
    private ArrayList<ArrayList<ArrayList<OrderItems>>> orderItemsListAll;
    private ArrayList<ArrayList<OrderItems>> orderItemsList;
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
        // cook_listItem = new HashMap();
        orderList = new ArrayList<>();
        arrShowOrder=new ArrayList<>();
        //MealContent=new ArrayList<>();
        //orderItemsListAll =new ArrayList<>();
        //orderItemsList =new ArrayList<>();
    }

    public void setCookList() {
        adapter = new ChefExpandListAdapter(this, arrShowOrder);
        cookList.setAdapter(adapter);

        cookList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                OrderItems oi = (OrderItems)adapter.getChild(groupPosition,childPosition);
                oi.isCooked=true;
                Order changeOrder=orderList.get(groupPosition); //取得修改狀態的order物件
                changeOrder.i_status=2;
                ArrayList<Order> tmpArray=new ArrayList<Order>(); //建立一個暫存陣列
                tmpArray.add(changeOrder);   //把修改好的Order物件放入陣列
                Gson gson = new Gson();
                String OrderStr = gson.toJson(tmpArray);  //把陣列轉成json字串
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference order_Ref = database.getReference("order").child(changeOrder.getI_Order()); //取得要修改的訂單單號
                order_Ref.setValue(OrderStr);
                Log.w("OrderStr->", OrderStr);
                Log.d("status","status2:"+oi.isCooked);
                Log.d("status","status3:"+ changeOrder.i_status);
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot data : dataSnapshot.getChildren()) {  //取得order下的子項目
                    String dbOrder = data.getKey(); //訂單編號
                    Log.w("FireBaseTraining", "No = " + dbOrder);
                    //strKeyJason.add(dbOdrder)
                    String order_string = data.getValue().toString();   //一筆訂單的 Json Data
                    strOrderJason.add(order_string);
                    Log.w("FireBaseTraining", "value = " + order_string);
                }

                for (String strOrder: strOrderJason) {
                    Gson gson = new Gson();
                    Order[] orderArray;
                    orderArray = gson.fromJson(strOrder, Order[].class); //把strOrderJason的全部資料都丟到Order陣列中
                    orderList.add(orderArray[0]);
                }
                Log.d("fire","size"+orderList.size());
                for(int i=0;i<orderList.size();i++){
                    Log.d("fire",""+orderList.get(i).str_Order);
                    Log.d("fire",""+orderList.get(i).str_Flag);
                    Log.d("fire",""+orderList.get(i).i_status);
                    if(orderList.get(i).i_status==3){
                        orderList.remove(i);
                    }
                    for(int j=0;j< orderList.get(i).orderItems.size();j++){
                        for(int k=0;k<orderList.get(i).orderItems.get(j).size();k++){
                            if(orderList.get(i).orderItems.get(j).get(k).isCooked!=true){
                                arrShowOrder.add(orderList.get(i));
                                break;
                            }
                            break;
                        }
                    }
                }
                //Log.w("FireBaseTraining", "arrShowOrder = " + arrShowOrder);
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

