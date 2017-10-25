package com.example.student.order;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;
import static com.example.student.order.R.id.btnAdd;

/**
 * Created by student on 2017/10/23.
 */

public class MealItemAdapter extends BaseAdapter {
    private Activity activity;
    private String[] itemTitles;
    private String[] itemMoney;

    public MealItemAdapter(Activity activity, int mealType) {
        this.activity = activity;
        switch (mealType){
            case 1:
                itemTitles = activity.getResources().getStringArray(R.array.salad);
                itemMoney = activity.getResources().getStringArray(R.array.salad_money);
                break;
            case 2:
                itemTitles = activity.getResources().getStringArray(R.array.main_meal);
                itemMoney = activity.getResources().getStringArray(R.array.main_meal_money);
                break;
            case 3:
                itemTitles = activity.getResources().getStringArray(R.array.dessert);
                itemMoney = activity.getResources().getStringArray(R.array.dessert_money);
                break;
            case 4:
                itemTitles = activity.getResources().getStringArray(R.array.drink);
                itemMoney = activity.getResources().getStringArray(R.array.drink_money);
                break;
        }

    }

    @Override
    public int getCount() {
        return itemTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder localViewHolder = null;
        OnClick listener = null;
        if (convertView == null) {
            localViewHolder = new ViewHolder();
            listener = new OnClick(position);//在这里新建监听对象
            convertView = activity.getLayoutInflater().inflate(R.layout.layout_mealitem,null);
            localViewHolder.txtTitle = convertView.findViewById(R.id.item_name);
            localViewHolder.txtTitle.setText(itemTitles[position]);
            localViewHolder.txtMoney = convertView.findViewById(R.id.item_money);
            localViewHolder.txtMoney.setText(itemMoney[position]);
            localViewHolder.btnAdd = (Button) convertView.findViewById(btnAdd);
            localViewHolder.btnAdd.setOnClickListener(listener);
            localViewHolder.btnAdd.setTag(position);
            convertView.setTag(localViewHolder);
        } else {
            localViewHolder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        TextView txtTitle, txtMoney;
        Button btnAdd;
    }

    class OnClick implements OnClickListener {
        int position;

        public OnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getId() + itemTitles[position]);
            OrderItems orderItems = new OrderItems();
            orderItems.strItem = itemTitles[position];
            orderItems.i_money = Integer.valueOf(itemMoney[position]);
            orderItems.strtable = "";
            orderItems.strPosition = "";
            orderItems.str_remarks = "";

            ((OrderActivity)activity).addOrder(orderItems);
        }
    }
}
