package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Adapter.CartAdapter;
import com.example.fightersarena.ocflex_costumer.Adapter.CustomerServicesAdapter;
import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Listeners.RecyclerTouchListener;
import com.example.fightersarena.ocflex_costumer.Models.Billing;
import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.Models.CartVM;
import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.Models.CustomerServices;
import com.example.fightersarena.ocflex_costumer.Models.OrderItemRequestVM;
import com.example.fightersarena.ocflex_costumer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public TextView tv,txt_msg,text_add;
    public ImageView i;

    Button btnAddMoreCart, btnCheckOut;

    private List<CartVM> cartList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        //Side Menu and toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cart);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_cart);
        navigationView.setNavigationItemSelectedListener(this);


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

        btnAddMoreCart = (Button) findViewById(R.id.btn_addmorecart);
        btnCheckOut = (Button) findViewById(R.id.btn_checkout);
        txt_msg =(TextView) findViewById(R.id.txt_msg);
        text_add =(TextView) findViewById(R.id.text_add);
        // Listeners
        btnAddMoreCart.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerMenuCart);
        cartAdapter = new CartAdapter(cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);

        GetCartItem();
    }

    private void GetCartItem(){
        clearRecyclerView();
        List<OrderItemRequestVM> cartItems = Cart.getCartItems(this);
        if(cartItems.size() > 0){

            btnCheckOut.setVisibility(View.VISIBLE);
            text_add.setVisibility(View.VISIBLE);
            txt_msg.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            for (OrderItemRequestVM cartListItem: cartItems){
                int id = cartListItem.getServiceId();
                String servicename = cartListItem.getServiceName();
                int total = cartListItem.getHours() * cartListItem.getRates();
                String date = cartListItem.getStartDate();

                CartVM cart = new CartVM(id, servicename, date, total);
                cartList.add(cart);
            }
            cartAdapter.notifyDataSetChanged();

        }else{

            btnCheckOut.setVisibility(View.GONE);

            text_add.setVisibility(View.GONE);
            txt_msg.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Log.d("data","empty");
        }
    }

    private void clearRecyclerView() {
        cartList.clear();
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addmorecart:
                OpenActivity(ServicesListActivity.class);
                break;

            case R.id.btn_checkout:
                OpenActivity(BillingActivity.class);
                break;

            case R.id.actionbar_notifcation_img:
                OpenActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:
                OpenActivity(CartActivity.class);
                break;
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_cart);

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

        } else if (id == R.id.menu_pro_logout) {
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
        tv.setText(String.valueOf(Cart.getCartItemsCount(this)));
      //  i.setOnClickListener(this);
       // tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }
}
