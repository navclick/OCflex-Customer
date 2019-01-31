package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Adapter.CustomerServicesAdapter;
import com.example.fightersarena.ocflex_costumer.Adapter.MyOrderHistoryAdapter;
import com.example.fightersarena.ocflex_costumer.Adapter.MyOrdersAdapter;
import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Listeners.RecyclerTouchListener;
import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.Models.MyOrder;
import com.example.fightersarena.ocflex_costumer.Models.MyOrders;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends BaseActivity {

    private List<MyOrder> myOrderList = new ArrayList<>();
    private List<MyOrder> myOrderHistoryList = new ArrayList<>();
    private RecyclerView recyclerViewActiveOrders, recyclerViewOrderHistory;
    private MyOrdersAdapter myOrderAdapter;
    private MyOrderHistoryAdapter myOrderHistoryAdapter;
    public TokenHelper tokenHelper;
    public String TokenString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);


        // Initializations
        tokenHelper = new TokenHelper(this);
        TokenString = tokenHelper.GetToken();

        if(TokenString == null){
            OpenActivity(LoginActivity.class);
        }

        recyclerViewActiveOrders = (RecyclerView) findViewById(R.id.recyclerActiveOrders);
        recyclerViewOrderHistory = (RecyclerView) findViewById(R.id.recyclerCompletedOrders);

        myOrderAdapter = new MyOrdersAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewActiveOrders.setLayoutManager(mLayoutManager);
        recyclerViewActiveOrders.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActiveOrders.setAdapter(myOrderAdapter);

        myOrderHistoryAdapter = new MyOrderHistoryAdapter(myOrderHistoryList);
        RecyclerView.LayoutManager mLayoutManagerOrderHistory = new LinearLayoutManager(getApplicationContext());
        recyclerViewOrderHistory.setLayoutManager(mLayoutManagerOrderHistory);
        recyclerViewOrderHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrderHistory.setAdapter(myOrderHistoryAdapter);

//        recyclerViewActiveOrders.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewActiveOrders, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                MyOrder order = myOrderList.get(position);
//
//                Log.d("cust",String.valueOf(order.getId()));
//
//                Intent intent = new Intent(ServicesListActivity.this, OrderActivity.class);
//                intent.putExtra("id", order.getId());
//                intent.putExtra("name", order.getName());
//                intent.putExtra("rates", order.getRates());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        GetActiveOrders();
        GetOrderHistory();
    }

    private void GetActiveOrders(){
        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<MyOrders> response = callerResponse.GetActiveOrders();

            response.enqueue(new Callback<MyOrders>() {
                @Override
                public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                    MyOrders obj = response.body();
                    if(obj == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(MyOrderActivity.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        List<MyOrders.Value> list = obj.getValue();
                        for (MyOrders.Value customerList: list){

                            int id = customerList.getId();
                            String servicename = customerList.getServiceName();
                            int total = customerList.getRates() * customerList.getHours();
                            String date = customerList.getStartDate();

                            MyOrder ord = new MyOrder(id, servicename, total, date);
                            myOrderList.add(ord);
                        }
                        myOrderAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<MyOrders> call, Throwable t) {
                    Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(MyOrderActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }

    private void GetOrderHistory(){
        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<MyOrders> response = callerResponse.GetOrderHistory();

            response.enqueue(new Callback<MyOrders>() {
                @Override
                public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                    MyOrders obj = response.body();
                    if(obj == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(MyOrderActivity.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        List<MyOrders.Value> list = obj.getValue();
                        for (MyOrders.Value customerList: list){

                            int id = customerList.getId();
                            String servicename = customerList.getServiceName();
                            int total = customerList.getRates() * customerList.getHours();
                            String date = customerList.getStartDate();

                            MyOrder ord = new MyOrder(id, servicename, total, date);
                            myOrderHistoryList.add(ord);
                        }
                        myOrderAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<MyOrders> call, Throwable t) {
                    Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(MyOrderActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }
}
