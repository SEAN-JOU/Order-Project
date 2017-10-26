package com.example.student.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Chef extends AppCompatActivity {
    private ExpandableListView cookList;
    private ChefExpandListAdapter adapter;
    private ArrayList<String> cook_listGroup;
    private HashMap<String,ArrayList<OrderItems>> cook_listItem;
    private Order orderList;
    private OrderItems mealContent;
    private ArrayList<OrderItems> setMealContent;
    private ArrayList<ArrayList<OrderItems>> orderItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        initial();
        setCookList();
    }

    public void initial(){
        cookList=(ExpandableListView)findViewById(R.id.cookList);
        cook_listGroup=new ArrayList<>();
        cook_listItem=new HashMap();

        setMealContent=new ArrayList<>();
        mealContent=new OrderItems();
        mealContent.strItem="可樂";
        mealContent.str_remarks="去冰，餐前上";
        mealContent.isCooked=false;
        setMealContent.add(mealContent);
        mealContent=new OrderItems();
        mealContent.strItem="牛排";
        mealContent.str_remarks="七分熟";
        mealContent.isCooked=false;
        setMealContent.add(mealContent);
        mealContent=new OrderItems();
        mealContent.strItem="凱薩沙拉";
        mealContent.isCooked=false;
        setMealContent.add(mealContent);
        mealContent=new OrderItems();
        mealContent.strItem="提拉米蘇";
        mealContent.isCooked=false;
        setMealContent.add(mealContent);
        mealContent=new OrderItems();
        mealContent.strItem="義大利麵";
        mealContent.isCooked=false;
        setMealContent.add(mealContent);

        orderList=new Order();

        //因為有兩層，所以這邊要再new一次
        orderItemsList=new ArrayList<>();
        orderList.orderItems=orderItemsList;


        if(checkIsCooked(setMealContent)){
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
        }

    }

    public void setCookList(){
        adapter=new ChefExpandListAdapter(this,cook_listGroup,cook_listItem);
        cookList.setAdapter(adapter);

    }
    //判斷出菜狀態，如果全出就不顯示該筆單號
    public boolean checkIsCooked(ArrayList<OrderItems> al) {
        for (OrderItems a:al){
            if(!a.isCooked){  //只要有一道菜還沒出
                 return true;
            }
        }
        return false;
    }

/*    public void addItemIntoGroup(){
        for(int i=0;i<cook_listGroup.size();i++){
            cook_listItem.put(cook_listGroup.get(i),setMealContent);
        }
    }*/

}
