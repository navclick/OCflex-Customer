package com.example.fightersarena.ocflex_costumer.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Adapter.CustomerServicesAdapter;
import com.example.fightersarena.ocflex_costumer.Base.BaseFragment;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
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

public class OneFragment extends BaseFragment {
    public TokenHelper tokenHelper;
    public String TokenString;

    private List<CustomerService> customerServicesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomerServicesAdapter customerServiceAdapter;

    public OneFragment() {
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



        View view = inflater.inflate(R.layout.fragment_one,
                container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCustomerServices);

        customerServiceAdapter = new CustomerServicesAdapter(customerServicesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customerServiceAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomerService cust = customerServicesList.get(position);

                Log.d("cust",String.valueOf(cust.getId()));

                Intent intent = new Intent(getActivity().getApplicationContext(), OrderActivity.class);
                intent.putExtra("id", cust.getId());
                intent.putExtra("name", cust.getName());
                intent.putExtra("rates", cust.getRates());
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), movie.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //prepareMovieData();
        GetCustomerServices();

        return view;
    }


    private void GetCustomerServices(){
        showProgress();
        try {
            IApiCaller token = ApiClient.createService(IApiCaller.class);
            Call<CustomerServices> response = token.GetCustomerServices();

            response.enqueue(new Callback<CustomerServices>() {
                @Override
                public void onResponse(Call<CustomerServices> call, Response<CustomerServices> response) {
                    CustomerServices obj = response.body();
                    if(obj == null){
                        try {
                            hideProgress();
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(getActivity().getApplicationContext(), err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            hideProgress();
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        hideProgress();
                        List<CustomerServices.Value> list = obj.getValue();
                        for (CustomerServices.Value customerList: list){

                            int id = customerList.getId();
                            String name = customerList.getName();
                            int rates = customerList.getRates();
                            String imageUrl = customerList.getImage();

                            CustomerService cust = new CustomerService(id, name, rates, imageUrl);
                            customerServicesList.add(cust);
                        }
                        customerServiceAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<CustomerServices> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                    hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            hideProgress();
            Toast.makeText(getActivity().getApplicationContext(), "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }

}
