package com.example.fightersarena.ocflex_costumer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Models.MyOrder;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.List;

public class MyOrderHistoryAdapter extends RecyclerView.Adapter<MyOrderHistoryAdapter.ListViewHolder> {

    private List<MyOrder> myOrdersList;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView txtInActiveName,txtInActiveDate;

        public ListViewHolder(View view) {
            super(view);
            txtInActiveName = (TextView) view.findViewById(R.id.txt_inactivename);
            txtInActiveDate = (TextView) view.findViewById(R.id.txt_inactivedate);
        }
    }

    public MyOrderHistoryAdapter(List<MyOrder> objList) {
        this.myOrdersList = objList;
    }

    @Override
    public MyOrderHistoryAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_myorderhistory, parent, false);

        return new MyOrderHistoryAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrderHistoryAdapter.ListViewHolder holder, int position) {
        MyOrder myOrders = myOrdersList.get(position);
        holder.txtInActiveName.setText(myOrders.getServiceName());
        holder.txtInActiveDate.setText(myOrders.getStartDate());
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }
}