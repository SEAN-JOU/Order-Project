package com.example.student.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Sec extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnWaiter, btnChef, btnCashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        btnWaiter = (ImageButton) findViewById(R.id.waiter);
        btnChef = (ImageButton) findViewById(R.id.chef);
        btnCashier = (ImageButton) findViewById(R.id.cashier);
        btnWaiter.setOnClickListener(this);
        btnChef.setOnClickListener(this);
        btnCashier.setOnClickListener(this);
    }

    //測試push

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.waiter:
                startActivity(new Intent(Sec.this,Waiter.class));
                break;
            case R.id.chef:
                startActivity(new Intent(Sec.this,Chef.class));
                break;
            case R.id.cashier:
                startActivity(new Intent(Sec.this,Cashier.class));
                break;
        }
    }

    public void ToastMsg(String strMsg){
        Toast.makeText(this, strMsg, Toast.LENGTH_LONG).show();
    }
}
