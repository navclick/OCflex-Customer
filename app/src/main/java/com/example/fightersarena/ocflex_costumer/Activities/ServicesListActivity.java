package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.fightersarena.ocflex_costumer.Adapter.CustomerServicesAdapter;
import com.example.fightersarena.ocflex_costumer.Listeners.RecyclerTouchListener;
import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.Models.CustomerServices;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesListActivity extends AppCompatActivity {

    private List<CustomerService> customerServicesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomerServicesAdapter customerServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCustomerServices);

        customerServiceAdapter = new CustomerServicesAdapter(customerServicesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customerServiceAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomerService cust = customerServicesList.get(position);

                Log.d("cust",String.valueOf(cust.getId()));

                Intent intent = new Intent(ServicesListActivity.this, OrderActivity.class);
                intent.putExtra("id", "2");
                intent.putExtra("name", cust.getName());
                intent.putExtra("rates", cust.getRates().toString());
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), movie.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //prepareMovieData();
        GetCustomerServices();
    }

    private void prepareMovieData() {
        CustomerService cust = new CustomerService(1,"sss", 10, "http://192.168.100.2:82/images/dummyproduct.jpg");
        customerServicesList.add(cust);

        CustomerService cus1 = new CustomerService(2,"Service One", 55, "http://192.168.100.2:82/images/dummyproduct.jpg");
        customerServicesList.add(cus1);

        cust = new CustomerService(3,"Service Two", 30, "http://192.168.100.2:82/images/dummyproduct.jpg");
        customerServicesList.add(cust);

        cust = new CustomerService(4,"Service Three", 40, "http://192.168.100.2:82/images/dummyproduct.jpg");
        customerServicesList.add(cust);

        customerServiceAdapter.notifyDataSetChanged();
    }

    private void GetCustomerServices(){
        try {
            IApiCaller token = ApiClient.createService(IApiCaller.class);
            Call<CustomerServices> response = token.GetCustomerServices();

            response.enqueue(new Callback<CustomerServices>() {
                @Override
                public void onResponse(Call<CustomerServices> call, Response<CustomerServices> response) {
                    CustomerServices obj = response.body();
                    if(obj == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(ServicesListActivity.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(ServicesListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        List<CustomerServices.Value> list = obj.getValue();
                        for (CustomerServices.Value customerList: list){

                            int id = customerList.getId();
                            String name = customerList.getName();
                            int rates = customerList.getRates();
                            String imageUrl = customerList.getImageUrl();

                            CustomerService cust = new CustomerService(id, name, 10, imageUrl);
                            customerServicesList.add(cust);
                        }
                        customerServiceAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<CustomerServices> call, Throwable t) {
                    Toast.makeText(ServicesListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(ServicesListActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }
}