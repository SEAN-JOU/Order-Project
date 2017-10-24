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
    public int i_table;    //桌號
    public String people_number;    //人數
    public ArrayList<OrderItems> orderItems;    //點菜內容
    public int i_status;    //(1.出菜中(訂單成立) 2.待結帳(廚房已出菜) 3.已結帳(收銀完畢)
    public int i_money; //總金額
    public String str_waiter;   //點單員編
    public String str_cashier;  //結帳員編
    public String str_customer; //外帶客人姓名
    public String str_customer_tel; //外帶客人電話
}
