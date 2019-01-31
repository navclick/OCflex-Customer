package com.example.fightersarena.ocflex_costumer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.List;

public class CustomerServicesAdapter extends RecyclerView.Adapter<CustomerServicesAdapter.ListViewHolder> {

    private List<CustomerService> customerServiceList;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ListViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }

//        @Override
//        public void onClick(View v) {
//            if(v.getId()==R.id.img_customerservice  ) {
//
//                CustomerService objService = customerServiceList.get(getPosition());
//
//                CustomerServiceRequest service = new CustomerServiceRequest();
//                service.serviceId = objService.getId();
//                service.name = objService.getName();
//                service.rates = objService.getRates();
//                OrderActivity.objCustomerService = null;
//                OrderActivity.objCustomerService = service;
//                ActivityHelper.startActivity(v.getContext(),OrderActivity.class);
//
//                Log.d("test:", "image view click");
//            }
//        }
    }

    public CustomerServicesAdapter(List<CustomerService> objList) {
        this.customerServiceList = objList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_customerservices, parent, false);

        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        CustomerService customerService = customerServiceList.get(position);
        holder.name.setText(customerService.getName());
    }

    @Override
    public int getItemCount() {
        return customerServiceList.size();
    }
}