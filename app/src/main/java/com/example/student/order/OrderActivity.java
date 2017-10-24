package com.example.student.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OrderActivity extends Activity {
    private ListView listView_MealItem, listView_OrderItem;
    private MealItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        findViews();
    }

    public void onMealSelect(View v){
        //Toast.makeText(this, v.toString(), Toast.LENGTH_LONG).show();

        switch (v.getId()){
            case R.id.radioSalad:
                itemAdapter = new MealItemAdapter(this,1);
                break;
            case R.id.radioMainMeal:
                itemAdapter = new MealItemAdapter(this,2);
                break;
            case R.id.radioDessert:
                itemAdapter = new MealItemAdapter(this,3);
                break;
            case R.id.radioDrink:
                itemAdapter = new MealItemAdapter(this,4);
                break;
        }

        listView_MealItem.setAdapter(itemAdapter);
    }

    public void findViews(){
        listView_MealItem = (ListView) findViewById(R.id.meal_item_list);
        listView_OrderItem = (ListView) findViewById(R.id.order_item_list);
    }

    public void addOrder(String strItem){
        ArrayAdapter<OrderItems> orderItemsAda = new ArrayAdapter<OrderItems>(this, 0);
        OrderItems newOrderItem = new OrderItems();
        newOrderItem.strItem = strItem;
        orderItemsAda.add(newOrderItem);
        listView_OrderItem.setAdapter(orderItemsAda);
    }
}
