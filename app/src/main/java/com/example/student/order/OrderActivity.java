package com.example.student.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;
import static com.example.student.order.R.id.editRemark;

public class OrderActivity extends Activity {
    private TextView txtNumber, txtName, txtDate, txtSetNum;
    private ListView listView_MealItem, listView_OrderItem;
    private String strSetNum, strTableNum;
    public  String strCname,strCtel;
    private MealItemAdapter itemAdapter;
    ArrayList<OrderItems> itemArray;
    private orderItemAdapter adapter;
    private Button btnGoOrder;
    private Gson gson = new Gson();
    Toast ttt;
    LinearLayout inin,outout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        inin=(LinearLayout)findViewById(R.id.inin);
        outout=(LinearLayout)findViewById(R.id.outout);
        strSetNum = getIntent().getStringExtra("seatNo");
        strTableNum = getIntent().getStringExtra("tableNo");
        strCname=getIntent().getExtras().getString("clientname");
        strCtel=getIntent().getStringExtra("clienttel");
        outorin();
        findViews();
        readSharePreferences();
    }

    private void outorin() {
        if(strCname !=null && strCtel !=null) {
            outout.setVisibility(View.VISIBLE);
        }
        else{
            inin.setVisibility(View.VISIBLE);
        }

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
        txtDate = (TextView) findViewById(R.id.txtDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String strDate = formatter.format(curDate);
        txtDate.setText(strDate);
        txtSetNum = (TextView) findViewById(R.id.tableNumber_seatPage);
        txtSetNum.setText(strSetNum);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtName = (TextView) findViewById(R.id.txtName);

        listView_MealItem = (ListView) findViewById(R.id.meal_item_list);
        listView_OrderItem = (ListView) findViewById(R.id.order_item_list);
        listView_OrderItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int iPos = position;
                Log.w("orderList-onItemClick", iPos + ":" + itemArray.get(iPos).str_remarks);
                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(OrderActivity.this);
                final EditText editRemark = new EditText(OrderActivity.this);
                editRemark.setText(itemArray.get(iPos).str_remarks);
                dlgBuilder.setView(editRemark)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemArray.get(iPos).str_remarks = editRemark.getText().toString();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
            }
        });


        itemArray = new ArrayList();
        String strItems = getIntent().getStringExtra("itemArray");
        if (strItems != null){
            OrderItems[] items;
            items = gson.fromJson(strItems, OrderItems[].class);
            for(OrderItems a: items){
                Log.e("items:", a.strItem);
                itemArray.add(a);
            }
            adapter = new orderItemAdapter(this, itemArray);
            listView_OrderItem.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


        btnGoOrder = (Button) findViewById(R.id.btnGoOrder);
        //set Default menu item
        ((RadioButton)findViewById(R.id.radioSalad)).setChecked(true);
        itemAdapter = new MealItemAdapter(this,1);
        listView_MealItem.setAdapter(itemAdapter);
    }

    public void readSharePreferences(){
        SharedPreferences sp = getSharedPreferences("log",MODE_PRIVATE);
        txtNumber.setText(sp.getString("id","0000").toString());
        txtName.setText(sp.getString("name", "Default").toString());
    }

    public void addOrder(OrderItems items){
        Log.d(TAG, items.strItem + "--" + items.i_money);
        itemArray.add(items);
        adapter = new orderItemAdapter(this, itemArray);
        listView_OrderItem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onOrderClick(View view){
        if(strCname ==null && strCtel ==null){
            for(int i=0; i< itemArray.size(); i++) {
                itemArray.get(i).strPosition = strSetNum;
                itemArray.get(i).strtable = strTableNum;
                Log.w("OrderActivity----", itemArray.get(i).strItem + ":" + itemArray.get(i).str_remarks);
            }

            String orderStr = gson.toJson(itemArray);
            Log.d("order_itemString", orderStr);
            Intent in = getIntent();
            in.putExtra("order_itemString", orderStr);
            //in.putExtra("order_items", itemArray);
            setResult(RESULT_OK,in);
            finish();
        }
        else if(strCname !=null && strCtel !=null){/*外帶狀態*/

        final AlertDialog.Builder Outoder =new AlertDialog.Builder(this);

        Outoder.setTitle("確認客戶點單是否正確");

        Outoder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                //firebase上傳的地方
                startActivity(new Intent(OrderActivity.this,Waiter.class));

            }});
        Outoder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }});
         Outoder.show();
        }
    }


    class orderItemAdapter extends BaseAdapter{
        Activity activity;
        ArrayList<OrderItems> order_items;

        public orderItemAdapter(Activity activity, ArrayList<OrderItems> orders) {
            this.activity = activity;
            this.order_items = orders;
        }

        @Override
        public int getCount() {
            return order_items.size();
        }

        @Override
        public Object getItem(int position) {
            return order_items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder localViewHolder = null;
            //OnClick listener = null;
            if (convertView == null) {
                localViewHolder = new ViewHolder();
                convertView = activity.getLayoutInflater().inflate(R.layout.layout_orderitems, null);
                //listener = new OnClick(position);//在这里新建监听对象
                localViewHolder.tvItemTitle = convertView.findViewById(R.id.txtItem);
                localViewHolder.tvItemMoney = convertView.findViewById(R.id.txtMoney);
                localViewHolder.editRemark = convertView.findViewById(editRemark);
                localViewHolder.tvItemTitle.setText(order_items.get(position).strItem);
                localViewHolder.tvItemMoney.setText(String.valueOf(order_items.get(position).i_money));
                localViewHolder.editRemark.setText(order_items.get(position).str_remarks);
                //localViewHolder.editRemark.setOnClickListener(listener);
                //localViewHolder.editRemark.setTag(position);
                //order_items.get(position).str_remarks = localViewHolder.editRemark.getText().toString();
                convertView.setTag(localViewHolder);
            } else {
                localViewHolder = (ViewHolder)convertView.getTag();
                localViewHolder.tvItemTitle.setText(order_items.get(position).strItem);
                localViewHolder.tvItemMoney.setText(String.valueOf(order_items.get(position).i_money));
                localViewHolder.editRemark.setText(order_items.get(position).str_remarks);
                //order_items.get(position).str_remarks = localViewHolder.editRemark.getText().toString();
                return convertView;
            }

            return convertView;
        }


        class ViewHolder {
            TextView tvItemTitle, tvItemMoney;
            TextView editRemark;
        }

        /*class OnClick implements View.OnClickListener {
            int position;

            public OnClick(int pos) {
                Log.w("OrderActivity-1-OnClick", position + ":" + itemArray.get(pos).str_remarks);
                this.position = pos;
            }

            @Override
            public void onClick(View v) {
                Log.w("OrderActivity-2-OnClick", position + ":" + itemArray.get(position).str_remarks);
                *//*AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(activity);
                final EditText editRemark = new EditText(activity);
                editRemark.setText(order_items.get(position).str_remarks);
                dlgBuilder.setView(editRemark)
                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  order_items.get(position).str_remarks = editRemark.getText().toString();
                                  adapter.notifyDataSetChanged();
                              }
                          })
                        .create()
                        .show();*//*
            }
        }*/
    }
}
