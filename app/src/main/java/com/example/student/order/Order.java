package com.example.student.order;

import java.util.ArrayList;

/**
 * Created by student on 2017/10/19.
 */

//訂單
public class Order {

    public int i_Order; //訂單編號
    public String str_Date; //點單日期
    public int str_Flag; //單別(1.內用 2.外帶)
    public String i_table;    //桌號
    public String people_number;    //人數
    public ArrayList<OrderItems> orderItems;    //點菜內容
    public int i_status;    //(1.出菜中(訂單成立) 2.待結帳(廚房已出菜) 3.已結帳(收銀完畢)
    public int i_money; //總金額
    public String str_customer; //客戶姓名
    public String getStr_customer_tel;//客戶電話
    public String str_waiter;   //點單員編
    public String str_cashier;  //結帳員編


    public Order(int i_Order,String str_customer, int i_money, int i_status) {
        this.i_Order = i_Order;
        this.i_status = i_status;
        this.i_money = i_money;
        this.str_customer = str_customer;
    }

    public Order(String i_table,int i_Order,int i_money,int i_status){
        this.i_Order = i_Order;
        this.i_table =i_table;
        this.i_money = i_money;
        this.i_status =i_status;

    }

    public int getI_Order() {
        return i_Order;
    }

    public String getStr_Date() {
        return str_Date;
    }

    public int getStr_Flag() {
        return str_Flag;
    }

    public String getI_table() {
        return i_table;
    }

    public String getPeople_number() {
        return people_number;
    }

    public ArrayList<OrderItems> getOrderItems() {
        return orderItems;
    }

    public int getI_status() {
        return i_status;
    }

    public int getI_money() {
        return i_money;
    }

    public String getStr_customer() {
        return str_customer;
    }

    public String getGetStr_customer_tel() {
        return getStr_customer_tel;
    }

    public String getStr_waiter() {
        return str_waiter;
    }

    public String getStr_cashier() {
        return str_cashier;
    }
}
