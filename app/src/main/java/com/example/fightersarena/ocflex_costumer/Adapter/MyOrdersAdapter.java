package com.example.fightersarena.ocflex_costumer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Activities.TrackingActivity;
import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Models.MyOrder;
import com.example.fightersarena.ocflex_costumer.Models.OrderDetails;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ListViewHolder> {

    private List<MyOrder> myOrdersList;

<<<<<<< HEAD
    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView name, date;

        public ListViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_name);
            date = (TextView) view.findViewById(R.id.txt_date);
=======

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public Button btn_track;


        public ListViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            btn_track = (Button) view.findViewById(R.id.btn_track);
            btn_track.setOnClickListener(this);
>>>>>>> 8b98de86545e947fdae34b68036d4a5a7eab3574
        }


        @Override
        public void onClick(View view) {
            //Log.d(Constants.TAG,"Click");


            if(view.getId()==R.id.btn_track  ) {


                MyOrder  Order = myOrdersList.get(getPosition());

                OrderDetails.CustomerAddress=Order.getAddress();

                OrderDetails.CustomerName=Order.getCustomer();
                BaseActivity.startActivity(view.getContext(), TrackingActivity.class);

                }

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
        holder.date.setText(myOrders.getStartDate());
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }
}