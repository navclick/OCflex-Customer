package com.example.fightersarena.ocflex_costumer.Models;

import android.content.Context;
import android.util.Log;

import com.example.fightersarena.ocflex_costumer.Handlers.DatabaseHandler;

import java.util.Date;

public class Cart {
    public int ServiceId;
    public String OrderDate;
    public String OrderTime;
    public int OrderHours;
    public int Rates;

    public static boolean addToCart(Cart cart, Context context){

        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
        Cart item = databaseHelper.getCartItem(cart.ServiceId);
        boolean newItem = true;

        if(item != null){
            newItem = false;
        }
        if(newItem){
            Cart cartItem = new Cart();
            cartItem.ServiceId = cart.ServiceId;
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


    public static Cart getCartITem(int itemID,Context context){
        DatabaseHandler databaseHelper = DatabaseHandler.getInstance(context);
        return  databaseHelper.getCartItem(itemID);
    }
}
