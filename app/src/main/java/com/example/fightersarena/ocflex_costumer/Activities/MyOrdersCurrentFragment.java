package com.example.fightersarena.ocflex_costumer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Adapter.MyOrderHistoryAdapter;
import com.example.fightersarena.ocflex_costumer.Adapter.MyOrdersAdapter;
import com.example.fightersarena.ocflex_costumer.Base.BaseFragment;
import com.example.fightersarena.ocflex_costumer.Helpers.Constants;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Listeners.RecyclerTouchListener;
import com.example.fightersarena.ocflex_costumer.Models.MyOrder;
import com.example.fightersarena.ocflex_costumer.Models.MyOrders;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersCurrentFragment extends BaseFragment

{
    public TokenHelper tokenHelper;
    public String TokenString;
    private List<MyOrder> myOrderCurrentList = new ArrayList<>();

    private RecyclerView recyclerViewCurrentOrders;
    private MyOrdersAdapter myOrderAdapter;

    public MyOrdersCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tokenHelper = new TokenHelper(getActivity().getApplicationContext());
        TokenString = tokenHelper.GetToken();
        if(TokenString == null){
            OpenActivity(LoginActivity.class);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.content_myorders_current,
                container, false);


        recyclerViewCurrentOrders = (RecyclerView) view.findViewById(R.id.recyclerCurrentOrders);


        myOrderAdapter = new MyOrdersAdapter(myOrderCurrentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewCurrentOrders.setLayoutManager(mLayoutManager);
        recyclerViewCurrentOrders.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCurrentOrders.setAdapter(myOrderAdapter);




        recyclerViewCurrentOrders.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerViewCurrentOrders, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyOrder order = myOrderCurrentList.get(position);
                Gson gson = new Gson();
                String Reslog= gson.toJson(order);
                Log.d(Constants.TAG, Reslog);


                Log.d(Constants.TAG,String.valueOf(myOrderCurrentList.get(position).getCustomer()));

                Log.d(Constants.TAG,String.valueOf(myOrderCurrentList.size()));
                Log.d(Constants.TAG,String.valueOf(order.getCustomer()));
                Log.d(Constants.TAG,String.valueOf(tokenHelper.GetToken()));


                if(order.getStatusId()== Constants.ORDER_ACTIVE) {
                    /*
                    TrackingActivity.AssociateID= order.getAssignedTo().toString();
                    Intent intent = new Intent(getActivity().getApplicationContext(), TrackingActivity.class);
                    startActivity(intent);
                */
                }
                Log.d(Constants.TAG,"NotAssigned");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        GetCurrentOrders();




        return view;
    }


    private void GetCurrentOrders(){
        showProgress();
        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<MyOrders> response = callerResponse.GetActiveOrders();

            response.enqueue(new Callback<MyOrders>() {
                @Override
                public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                    MyOrders obj = response.body();

                    Gson gson = new Gson();
                    String Reslog= gson.toJson(response);
                    Log.d(Constants.TAG, Reslog);
                    if(obj == null){
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(getActivity().getApplicationContext(), err, Toast.LENGTH_SHORT).show();
                            hideProgress();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }else{
                        List<MyOrders.Value> list = obj.getValue();
                        for (MyOrders.Value customerList: list){

                            int id = customerList.getId();
                            String servicename = customerList.getServiceName();
                            int total = customerList.getRates() * customerList.getHours();
                            String date = customerList.getStartDate();

                            if(customerList.getStatusId() ==Constants.ORDER_PENDING || customerList.getStatusId() ==Constants.ORDER_ASSIGNED   ) {
                                MyOrder ord = new MyOrder(id, servicename, total, date, customerList.getStatusId(), customerList.getAssignedTo());


                                myOrderCurrentList.add(ord);
                            }
                        }
                        myOrderAdapter.notifyDataSetChanged();
                        hideProgress();

                    }
                }
                @Override
                public void onFailure(Call<MyOrders> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                    hideProgress();

                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), "Email or password is not correct", Toast.LENGTH_SHORT).show();
            hideProgress();

        }
    }






}
