package com.example.fightersarena.ocflex_costumer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Models.MyOrder;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ListViewHolder> {

    private List<MyOrder> myOrdersList;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView txtName, txtDate;

        public ListViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
        }
    }

    public MyOrdersAdapter(List<MyOrder> objList) {
        this.myOrdersList = objList;
    }

    @Override
    public MyOrdersAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_myorderlist, parent, false);

        return new MyOrdersAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrdersAdapter.ListViewHolder holder, int position) {
        MyOrder myOrders = myOrdersList.get(position);
        holder.txtName.setText(myOrders.getServiceName());
        holder.txtDate.setText(myOrders.getStartDate());
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }
}