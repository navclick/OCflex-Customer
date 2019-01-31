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
        public TextView name;

        public ListViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
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
        holder.name.setText(myOrders.getServiceName());
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }
}