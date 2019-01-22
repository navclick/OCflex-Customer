package com.example.fightersarena.ocflex_costumer.Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.Models.CustomerService;

import static com.example.fightersarena.ocflex_costumer.Helpers.Constants.DATABASE_NAME;
import static com.example.fightersarena.ocflex_costumer.Helpers.Constants.DATABASE_VERSION;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static String TABLE_NAME = "Cart";
    private static final String CART_ID = "Id";
    private static final String SERVICE_ID = "ServiceId";
    private static final String ORDER_DATE = "OrderDate";
    private static final String ORDER_TIME = "OrderTime";
    private static final String ORDER_HOURS = "OrderHours";
    private static final String RATES = "Rates";

    private static DatabaseHandler sInstance;
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateCart = "CREATE TABLE "+ TABLE_NAME +" ( " + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SERVICE_ID + ", " + ORDER_DATE + ", "
                + ORDER_TIME + ", " + ORDER_HOURS + ", " + RATES + " ) ";
        db.execSQL(CreateCart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void addToCart(Cart data) {

        Cursor response = GetCustomerService(data.ServiceId);
        if(response.getCount() == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SERVICE_ID, data.ServiceId);
            values.put(ORDER_DATE, data.OrderDate);
            values.put(ORDER_TIME, data.OrderTime);
            values.put(ORDER_HOURS, data.OrderHours);
            values.put(RATES, data.Rates);
            // Inserting Row
            db.insert(TABLE_NAME, null, values);
            db.close(); // Closing database connection
            return;

        }else{
            Log.d("Error","Item already exists");
        }

//        Cursor response = GetCustomerService();
//
//        if(response.getCount() == 0) {
//            // show message
//            Log.d("Error","Nothing found");
//            return;
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        while (response.moveToNext()) {
//            buffer.append("Id :"+ response.getString(0)+"\n");
//            buffer.append("Name :"+ response.getString(1)+"\n");
//            buffer.append("Surname :"+ response.getString(2)+"\n");
//            buffer.append("Marks :"+ response.getString(3)+"\n\n");
//        }
//
//        Log.d("Data",buffer.toString());

    }

    public Cursor GetCustomerService(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME ,null);
        return res;
    }

    public Cursor GetCustomerService(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "select * from "+TABLE_NAME + " Where CART_ID = '"+ id +"' ";
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " Where ServiceId = "+ id +" ",null);
        return res;
    }

    public Cart getCartItem(long serviceid) {

        Cart cartItem = new Cart();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where ServiceId="+ serviceid +"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //cartItem.Id = cursor.getInt(0);
                cartItem.ServiceId = cursor.getInt(1);
                cartItem.OrderDate = cursor.getString(2);
                cartItem.OrderTime = cursor.getString(3);
                cartItem.OrderHours = cursor.getInt(4);
                cartItem.Rates = cursor.getInt(5);
                // Adding contact to list
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            //   db.close();
            db.close();
        }
        return cartItem;
    }
}