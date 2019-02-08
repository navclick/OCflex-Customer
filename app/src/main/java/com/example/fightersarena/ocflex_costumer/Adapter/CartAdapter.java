package com.example.fightersarena.ocflex_costumer.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Activities.CartActivity;
import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.Models.CartVM;
import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ListViewHolder> {

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    private List<CartVM> cartList;

    private Context mContext;

    List<CartVM> itemsPendingRemoval;
    int lastInsertedIndex; // so we can add some more items for testing purposes
    boolean undoOn =false;

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<CartVM, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    public class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        Button undoButton;



        public ListViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_cart_servicename);
            undoButton = (Button) itemView.findViewById(R.id.undo_button);
        }
    }

    public CartAdapter(List<CartVM> objList,Context c) {
        this.cartList = objList;
        itemsPendingRemoval = new ArrayList<>();
        mContext=c;
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



        if (itemsPendingRemoval.contains(cart)) {
            // we need to show the "undo" state of the row
            holder.itemView.setBackgroundColor(Color.RED);
            holder.name.setVisibility(View.GONE);
            holder.undoButton.setVisibility(View.VISIBLE);
            holder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // user wants to undo the removal, let's cancel the pending task
                    Runnable pendingRemovalRunnable = pendingRunnables.get(cart);
                    pendingRunnables.remove(cart);
                    if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
                    itemsPendingRemoval.remove(cart);
                    // this will rebind the row in "normal" state
                    notifyItemChanged(cartList.indexOf(cart));
                }
            });
        } else {
            // we need to show the "normal" state
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(servicename);
            holder.undoButton.setVisibility(View.GONE);
            holder.undoButton.setOnClickListener(null);
        }




    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public boolean isPendingRemoval(int position) {
        CartVM item = cartList.get(position);
        return itemsPendingRemoval.contains(item);
    }

    public void pendingRemoval(int position) {
        final CartVM item = cartList.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(cartList.indexOf(item));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }


    public void remove(int position) {
        CartVM item = cartList.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
        }
        if (cartList.contains(item)) {
            cartList.remove(position);
            Cart.deleteFromCart(mContext,item.getId());

            if(mContext instanceof CartActivity){
                ((CartActivity)mContext).updateCartCound();
            }
            notifyItemRemoved(position);
        }
    }
}