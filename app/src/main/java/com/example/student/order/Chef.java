package com.example.student.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    //private Order orderList;
    private OrderItems mealContent;
    private ArrayList<Order> orderList;
    private ArrayList<OrderItems> setMealContent;
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
        cook_listItem = new HashMap();
        orderList = new ArrayList<>();
        setMealContent=new ArrayList<>();
        orderItemsListAll =new ArrayList<>();
        orderItemsList =new ArrayList<>();


        //因為有兩層，所以這邊要再new一次
       // orderItemsList = new ArrayList<>();
        //orderList.orderItems = orderItemsList;


       /* if(checkIsCooked(setMealContent)){
            orderList.str_Order="10240001";
            orderList.orderItems.add(setMealContent);
            cook_listGroup.add(orderList.str_Order);
            orderList=new Order();
            orderList.str_Order="10240002";
            cook_listGroup.add(orderList.str_Order);
            orderList=new Order();
            orderList.str_Order="10240003";
            cook_listGroup.add(orderList.str_Order);
            cook_listItem.put(cook_listGroup.get(0),setMealContent);
            cook_listItem.put(cook_listGroup.get(1),setMealContent);
            cook_listItem.put(cook_listGroup.get(2),setMealContent);
        }else{
            orderList=new Order();
            orderList.str_Order="10240002";
            cook_listGroup.add(orderList.str_Order);
            orderList=new Order();
            orderList.str_Order="10240003";
            cook_listGroup.add(orderList.str_Order);
            cook_listItem.put(cook_listGroup.get(0),setMealContent);
            cook_listItem.put(cook_listGroup.get(1),setMealContent);
        }*/

    }

    public void setCookList() {
        adapter = new ChefExpandListAdapter(this, orderList);
        cookList.setAdapter(adapter);

    }

    //判斷出菜狀態，如果全出就不顯示該筆單號
    public boolean checkIsCooked(ArrayList<OrderItems> al) {
        for (OrderItems a : al) {
            if (!a.isCooked) {  //只要有一道菜還沒出
                return true;
            }
        }
        return false;
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

                for (String strOrder: strOrderJason){
                    Gson gson = new Gson();
                    Order[] orderArray;
                    orderArray = gson.fromJson(strOrder, Order[].class); //把strOrderJason的全部資料都丟到Order陣列中
                    orderList.add(orderArray[0]);
                }
                Log.w("list","listChefTotal:"+ orderList.size());


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

