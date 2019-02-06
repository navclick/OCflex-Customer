package com.example.fightersarena.ocflex_costumer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.Models.CartVM;
import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ListViewHolder> {

    private List<CartVM> cartList;

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ListViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_cart_servicename);
        }
    }

    public CartAdapter(List<CartVM> objList) {
        this.cartList = objList;
    }

    @Override
    public CartAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_menucart, parent, false);

        return new CartAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ListViewHolder holder, int position) {
        CartVM cart = cartList.get(position);
        String servicename = cart.getServiceName();
        holder.name.setText(servicename);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}