package com.example.student.order;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cashier extends AppCompatActivity {
    private TextView txtNumber, txtName, txtDate;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        findView();
        readSharePreferences();
    }

    public void findView(){
        txtDate = (TextView) findViewById(R.id.txtDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String strDate = formatter.format(curDate);
        txtDate.setText(strDate);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtName = (TextView) findViewById(R.id.txtName);
    }

    public void readSharePreferences(){
        SharedPreferences sp = getSharedPreferences("log",MODE_PRIVATE);
        txtNumber.setText(sp.getString("id","0000").toString());
        txtName.setText(sp.getString("name", "Default").toString());
    }
}
