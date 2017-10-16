package com.example.student.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sec extends AppCompatActivity implements View.OnClickListener{
    private Button btnWaiter, btnChef, btnCashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        btnWaiter = (Button) findViewById(R.id.waiter);
        btnChef = (Button) findViewById(R.id.chef);
        btnCashier = (Button) findViewById(R.id.cashier);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.waiter:
                ToastMsg("You are Waiter!");
                break;
            case R.id.chef:
                ToastMsg("You are Chef!");
                break;
            case R.id.cashier:
                ToastMsg("You are Cashier!");
                break;
        }
    }

    public void ToastMsg(String strMsg){
        Toast.makeText(this, strMsg, Toast.LENGTH_LONG).show();
    }
}
