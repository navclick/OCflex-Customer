package com.example.fightersarena.ocflex_costumer.Models;

import android.content.Context;
import android.util.Log;

import com.example.fightersarena.ocflex_costumer.Handlers.DatabaseHandler;

import java.util.Date;
import java.util.List;

public class Cart {
    public int ServiceId, OrderHours, Rates;
    public String ServiceName, OrderDate, OrderTime;

    public static boolean addToCart(Cart cart, Context context){

        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
        Cart item = databaseHelper.getCartItem(cart.ServiceId);
        boolean newItem = true;

        if(item.ServiceId > 0){
            newItem = false;
        }
        if(newItem){
            Cart cartItem = new Cart();
            cartItem.ServiceId = cart.ServiceId;
            cartItem.ServiceName = cart.ServiceName;
            cartItem.OrderDate = cart.OrderDate;
            cartItem.OrderTime = cart.OrderTime;
            cartItem.OrderHours = cart.OrderHours;
            cartItem.Rates = cart.Rates;
            databaseHelper.addToCart(cartItem);
            return true;
        }else{
            return false;
        }
    }

    public static List<OrderItemRequestVM> getCartItems(Context context){
        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
        return  databaseHelper.getCartItems();
    }

    public static Cart getCartItem(int itemID,Context context){
        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
        return  databaseHelper.getCartItem(itemID);
    }

    public static void removeCartItems(Context context){
        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
        databaseHelper.removeCartItems();



    }


    public static int getCartItemsCount(Context context){
        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
       int count= databaseHelper.getItemCount();
    return count;


    }


}
