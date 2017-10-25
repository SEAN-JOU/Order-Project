package com.example.student.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Menu_context extends AppCompatActivity {

    ListView menuinformation;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_context);
        menuinformation=(ListView)findViewById(R.id.menuinformation);
        checkout=(Button)findViewById(R.id.checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it2= new Intent();
                it2.putExtra("已結帳","3");
                setResult(RESULT_OK,it2);
                finish();
            }});

    }}
