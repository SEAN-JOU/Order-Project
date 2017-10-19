package com.example.student.order;

/**
 * Created by student on 2017/10/19.
 */

//點菜內容
public class OrderItems {
    public int i_table; //桌號
    public String strPosition;  //座位別
    public String strItem;  //點菜項目
    public int i_money; //金額
    public String str_remarks;  //備註
    public boolean isCooked;    //出菜狀態 true:已出菜, false:製作中(default)
}
