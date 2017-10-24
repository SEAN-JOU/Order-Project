package com.example.student.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText account,password;
    Button clear,login;
    ArrayList<Map<String,Object>> member;
    String useraccount,userpassword, username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = (EditText)findViewById(R.id.account);
        password =(EditText)findViewById(R.id.password);
        clear = (Button)findViewById(R.id.clear);
        login = (Button)findViewById(R.id.login);

        member = new ArrayList<>();
        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("id","001");
        map1.put("name", "Andy");
        map1.put("PWD", "0001");
        member.add(map1);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("id","002");
        map2.put("name", "Marry");
        map2.put("PWD", "0002");
        member.add(map2);
        HashMap<String,Object> map3 = new HashMap<>();
        map3.put("id","003");
        map3.put("PWD", "0003");
        map3.put("name", "cherry");
        member.add(map3);
        //測試用免id/pwd登入，記得刪除
        HashMap<String,Object> map4 = new HashMap<>();
        map4.put("id","000");
        map4.put("PWD", "");
        map4.put("name", "測試帳號");
        member.add(map4);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                useraccount=account.getText().toString();
                userpassword=password.getText().toString();
                if(check(member,useraccount,userpassword)){

                    WriteSharedPreferences();
                    Intent it = new Intent();
                    it.setClass(MainActivity.this,Sec.class);
                    startActivity(it);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"請確認輸入的帳號或密碼正確",Toast.LENGTH_SHORT).show();

                }}});
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                account.setText("");
                password.setText("");

            }});}


    boolean check(ArrayList<Map<String,Object>> m,String account,String password ) {
        //for (Map<String, Object> a : m) {
        for (int x=0; x < m.size(); x++){
            //if (a.get("id").toString().equals(account) && a.get("PWD").toString().equals(password) ) {
            if (m.get(x).get("id").toString().equals(account) &&
                    m.get(x).get("PWD").toString().equals(password))
            {
                username = m.get(x).get("name").toString();
                return true;
            }
        }
        return false;//
    }
    public void WriteSharedPreferences(){
        SharedPreferences sp = getSharedPreferences("log",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("id",useraccount);
        ed.putString("name",username);
        ed.commit();
    }
}
