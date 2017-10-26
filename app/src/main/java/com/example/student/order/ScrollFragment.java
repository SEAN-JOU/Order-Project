package com.example.student.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScrollFragment extends Fragment implements View.OnClickListener {
    private TextView seatNumber,orderListEmpty;
    private ListView seatOrderList;
    private LinearLayout seatContainer;
    private OrderListAdapter adapter;

    public ScrollFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scroll, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        initial();
    }

    public void initial(){
        seatNumber=(TextView)getView().findViewById(R.id.seatNumber);
        orderListEmpty=(TextView)getView().findViewById(R.id.orderListEmpty);
        seatOrderList=(ListView)getView().findViewById(R.id.seatOrderList);
        seatContainer=(LinearLayout)getView().findViewById(R.id.seatContainer);
        seatContainer.setClickable(true);
        //seatContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        /*Intent in=new Intent();
        in.putExtra("seatNo",seatNumber.getText().toString());
        in.setClass(getActivity(),OrderActivity.class);
        //startActivity(in);
        startActivityForResult(in, 1);*/
    }

    public void setSeatNumber(CharSequence seatNo){
        seatNumber.setText(seatNo);
    }

    public void setSeatOrderList(ArrayList<String> OrderList){
        adapter=new OrderListAdapter(getActivity(),R.layout.orderlistviewlayout,OrderList);
        seatOrderList.setEmptyView(orderListEmpty);
        seatOrderList.setAdapter(adapter);
    }
}
