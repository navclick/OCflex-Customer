package com.example.fightersarena.ocflex_costumer.Helpers;

import android.content.Context;
import android.widget.Toast;

public class GeneralHelper {

    public static void ShowToast(Context context, String message){
        Toast.makeText(context, message,
                Toast.LENGTH_LONG).show();
    }
}