package com.example.student.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Sec extends AppCompatActivity {
    private Button btnWaiter, btnChef, btnCashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        btnWaiter = (Button) findViewById(R.id.waiter);
        btnChef = (Button) findViewById(R.id.chef);
        btnCashier = (Button) findViewById(R.id.cashier);
    }

    //測試push

}
