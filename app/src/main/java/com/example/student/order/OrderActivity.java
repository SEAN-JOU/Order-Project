package com.example.student.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;
import static com.example.student.order.R.id.editRemark;

public class OrderActivity extends Activity {
    private TextView txtNumber, txtName, txtDate, txtSetNum;
    private ListView listView_MealItem, listView_OrderItem;
    private String strSetNum;
    private MealItemAdapter itemAdapter;
    ArrayList<OrderItems> itemArray;
    private orderItemAdapter adapter;
    private Button btnGoOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        strSetNum = getIntent().getStringExtra("seatNo");
        findViews();
        readSharePreferences();
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
        itemArray = new ArrayList();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onOrderClick(View view){
        for(int i=0; i< itemArray.size(); i++) {
            itemArray.get(i).strPosition = strSetNum;
            Log.w("OrderActivity----", itemArray.get(i).strItem + ":" + itemArray.get(i).str_remarks);
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
            OnClick listener = null;
            if (convertView == null) {
                localViewHolder = new ViewHolder();
                convertView = activity.getLayoutInflater().inflate(R.layout.layout_orderitems, null);
                listener = new OnClick(position);//在这里新建监听对象
                localViewHolder.tvItemTitle = convertView.findViewById(R.id.txtItem);
                localViewHolder.tvItemMoney = convertView.findViewById(R.id.txtMoney);
                localViewHolder.editRemark = convertView.findViewById(editRemark);
                localViewHolder.tvItemTitle.setText(order_items.get(position).strItem);
                localViewHolder.tvItemMoney.setText(String.valueOf(order_items.get(position).i_money));
                localViewHolder.editRemark.setText(order_items.get(position).str_remarks);
                localViewHolder.editRemark.setOnClickListener(listener);
                localViewHolder.editRemark.setTag(position);
                order_items.get(position).str_remarks = localViewHolder.editRemark.getText().toString();
                convertView.setTag(localViewHolder);
            } else {
                localViewHolder = (ViewHolder)convertView.getTag();
            }

            return convertView;
        }


        class ViewHolder {
            TextView tvItemTitle, tvItemMoney;
            TextView editRemark;
        }

        class OnClick implements View.OnClickListener {
            int position;

            public OnClick(int position) {
                Log.w("OrderActivity-1-OnClick", position + ":" + itemArray.get(position).str_remarks);
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                Log.w("OrderActivity-2-OnClick", position + ":" + itemArray.get(position).str_remarks);
                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(activity);
                final EditText editRemark = new EditText(activity);
                editRemark.setText(order_items.get(position).str_remarks);
                dlgBuilder.setView(editRemark)
                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  order_items.get(position).str_remarks = editRemark.getText().toString();
                                  //adapter = (orderItemAdapter)listView_OrderItem.getAdapter();
                                  adapter = new orderItemAdapter(activity, itemArray);
                                  listView_OrderItem.setAdapter(adapter);
                                  adapter.notifyDataSetChanged();
                              }
                          })
                        .create()
                        .show();
            }
        }
    }
}
