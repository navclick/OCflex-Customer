package com.example.fightersarena.ocflex_costumer.Helpers;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralHelper {

    public static void ShowToast(Context context, String message){
        Toast.makeText(context, message,
                Toast.LENGTH_LONG).show();
    }

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}