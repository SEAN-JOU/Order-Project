package com.example.student.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.parseColor;


public class tableFragment extends Fragment implements View.OnClickListener {
    private TableLayout tablelayout;
    private TableRow tableStatusBackground;
    private TextView tableNumber,guestNumber,tableStatus,orderNumber;

    public tableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initial();
    }

    public void initial(){
        tablelayout=(TableLayout)getView().findViewById(R.id.tablelayout);
        tablelayout.setClickable(true);
        tablelayout.setOnClickListener(this);
        tableStatusBackground=(TableRow)getView().findViewById(R.id. tableStatusBackground);
        tableNumber=(TextView)getView().findViewById(R.id.tableNumber);
        guestNumber=(TextView)getView().findViewById(R.id.guestNumber);
        tableStatus=(TextView)getView().findViewById(R.id.tableStatus);
        orderNumber=(TextView)getView().findViewById(R.id.orderNumber);
    }

    @Override
    public void onClick(View view) {
        tableStatusBackground.setBackgroundColor(parseColor("#99ff504d"));
        tableStatus.setText("出菜中");
        Intent in=new Intent();
        in.putExtra("tableNo",tableNumber.getText().toString());
        in.setClass(getActivity(),SeatItem.class);
        startActivityForResult(in,1);
    }

    public void setTableNumber(CharSequence tableNo){
        tableNumber.setText(tableNo);
    }

    public void setTableOrderNumber(CharSequence orderNo){
        orderNumber.setText(orderNo);
    }

    public void setTableStatus(int iStatus){
        switch (iStatus){
            case 1:
                tableStatus.setText("出菜中");
                break;
            case 2:
                tableStatus.setText("待結帳");
                break;
            case 3:
                tableStatus.setText("已結帳");
                break;
        }
    }

    public void setTablePeople(String iPeople){
        guestNumber.setText(iPeople);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            setTableNumber(data.getStringExtra("In_Order_Table"));
            setTablePeople(data.getStringExtra("In_Order_People"));
            //(1.出菜中(訂單成立) 2.待結帳(廚房已出菜) 3.已結帳(收銀完畢)
            int iStatus = data.getIntExtra("In_Order_Status", 0);
            setTableStatus(iStatus);
            //
            setTableOrderNumber(data.getStringExtra("In_Order_Number"));
        }
    }
}
