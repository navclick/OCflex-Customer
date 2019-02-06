package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends BaseActivity  implements  NavigationView.OnNavigationItemSelectedListener{

    private List<MyOrder> myOrderList = new ArrayList<>();
    private List<MyOrder> myOrderHistoryList = new ArrayList<>();
    private RecyclerView recyclerViewActiveOrders, recyclerViewOrderHistory;
    private MyOrdersAdapter myOrderAdapter;
    private MyOrderHistoryAdapter myOrderHistoryAdapter;
    public TokenHelper tokenHelper;
    public String TokenString;
    public TextView tv;
    public ImageView i;
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_myorders);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_myorders);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_myorders);
        navigationView.setNavigationItemSelectedListener(this);

        //-----------------------------------

//Show pic and name on drawer menu

        View header = navigationView.getHeaderView(0);
        TextView t = (TextView) header.findViewById(R.id.txt_main_name);
        TextView tEmail = (TextView) header.findViewById(R.id.txt_email);
        ImageView profile_img= (ImageView) header.findViewById(R.id.img_nav_profile);
        tEmail.setText(tokenHelper.GetUserEmail());

        t.setText(tokenHelper.GetUserName());

        //profile_img.setBackground(getResources().getDrawable(R.drawable.profile_image_border));
        Picasso.with(this).load(tokenHelper.GetUserPhoto()).resize(110, 110).centerCrop().into(profile_img);







        ///--------







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
        showProgress();
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
                            hideProgress();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        hideProgress();
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
                        hideProgress();
                    }
                }
                @Override
                public void onFailure(Call<MyOrders> call, Throwable t) {
                    Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
               hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(MyOrderActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
            hideProgress();
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
                            hideProgress();
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(MyOrderActivity.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                       hideProgress();
                        }
                    }else{
                        List<MyOrders.Value> list = obj.getValue();
                        for (MyOrders.Value customerList: list){
                            hideProgress();
                            int id = customerList.getId();
                            String servicename = customerList.getServiceName();
                            int total = customerList.getRates() * customerList.getHours();
                            String date = customerList.getStartDate();

                            MyOrder ord = new MyOrder(id, servicename, total, date);
                            myOrderHistoryList.add(ord);
                        }
                        myOrderHistoryAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<MyOrders> call, Throwable t) {
                    Toast.makeText(MyOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                    hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(MyOrderActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
      hideProgress();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_myorders);

        if (id == R.id.my_orders) {
            // Handle the camera action
            mDrawerLayout.closeDrawers();
            // openActivityWithFinish(AboutActivity.class);
            BaseActivity.startActivity(this,MyOrderActivity.class);

        }  else if (id == R.id.menu_profile) {
            mDrawerLayout.closeDrawers();
            BaseActivity.startActivity(this,EditProfileActivity.class);
            // OpenActivity(EditProfileActivity.class);
            //openActivityProfile();
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_all_setting) {
            mDrawerLayout.closeDrawers();
            BaseActivity.startActivity(this,SettingActivity.class);

            // openActivity(ShoppingListActivity.class);
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_service) {
            mDrawerLayout.closeDrawers();
            BaseActivity.startActivity(this,ServicesListActivity.class);

            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }
        else if (id == R.id.menu_customer_experience) {
            mDrawerLayout.closeDrawers();
            BaseActivity.startActivity(this,CustomerExperienceActivity.class);

            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }




        else if (id == R.id.menu_pro_logout) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
            logOut();
        }

        return  true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        i =notifCount.findViewById(R.id.actionbar_notifcation_img);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        //tv.setText("12");
        tv.setText("0");
        //   i.setOnClickListener(this);
        //  tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

}
