package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.GeneralHelper;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Models.Billing;
import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.Models.OrderItemRequestVM;
import com.example.fightersarena.ocflex_costumer.Models.OrderRequest;
import com.example.fightersarena.ocflex_costumer.Models.OrderResponse;
import com.example.fightersarena.ocflex_costumer.Models.Token;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;
import com.example.fightersarena.ocflex_costumer.Utility.ValidationUtility;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public TextView tv;
    public ImageView i;

    EditText txtFullName, txtAddress, txtPhone, txtCity, txtPostal;
    Button btnOrder;
    public TokenHelper tokenHelper;
    public String TokenString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializations
        tokenHelper = new TokenHelper(this);
        TokenString = tokenHelper.GetToken();

        if(TokenString == null){
            OpenActivity(LoginActivity.class);
        }else{
            setContentView(R.layout.billing);

            //Side Menu and toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_billing);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_billing);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_billing);
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


            txtFullName = (EditText) findViewById(R.id.txt_fullname);
            txtAddress = (EditText) findViewById(R.id.txt_address);
            txtPhone = (EditText) findViewById(R.id.txt_phone);
            txtCity = (EditText) findViewById(R.id.txt_city);
            txtPostal = (EditText) findViewById(R.id.txt_postal);

            btnOrder = (Button) findViewById(R.id.btn_order);
            btnOrder.setOnClickListener(this);

            GetUser();
        }

    }

    private void GetUser(){
        try {
//            String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOiIyZDU0Y2JlZi1mNmVjLTQ1OGMtOGRlNS1iZGEzMTRhMTg0MDQiLCJ1bmlxdWVfbmFtZSI6ImFkbWlub25lQGdtYWlsLmNvbSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vYWNjZXNzY29udHJvbHNlcnZpY2UvMjAxMC8wNy9jbGFpbXMvaWRlbnRpdHlwcm92aWRlciI6IkFTUC5ORVQgSWRlbnRpdHkiLCJBc3BOZXQuSWRlbnRpdHkuU2VjdXJpdHlTdGFtcCI6IjdiMzgwYjlhLWVjYjQtNDJhMC04Y2M4LTZlMTI2YmMyYWY0NiIsInJvbGUiOlsiQWRtaW4iLCJTdXBlckFkbWluIl0sImlzcyI6Imh0dHA6Ly9vY2ZsZXhhcGkuaW5zaWRlZGVtby5jb20vIiwiYXVkIjoiNDE0ZTE5MjdhMzg4NGY2OGFiYzc5ZjcyODM4MzdmZDEiLCJleHAiOjE1NDgxNTY1OTAsIm5iZiI6MTU0ODA3MDE5MH0.XK5Xp3AIIHhZ37GS3jzqag1exg-iJ4N55g7kcctaDlQ";
            String token = TokenString;
            token = "Bearer " + token;
            IApiCaller client = ApiClient.createService(IApiCaller.class, token);
            Call<UserResponse> response = client.GetUser();

            response.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse objResponse = response.body();
                    if(objResponse != null){

                        String fullname = objResponse.getValue().getFullName();
                        String address = objResponse.getValue().getAddressOne();
                        String phone = objResponse.getValue().getPhoneNumber();
                        String city = objResponse.getValue().getCity();
                        String postal = objResponse.getValue().getPostalCode();

                        txtFullName.setText(fullname);
                        txtAddress.setText(address);
                        txtPhone.setText(phone);
                        txtCity.setText(city);
                        txtPostal.setText(postal);

//                        Billing billing = new Billing();
//                        billing.setFullName(reqfullname);
//                        billing.setAddress(reqaddress);
//                        billing.setPhone(reqphone);
//                        billing.setCity(reqcity);
//                        billing.setPostal(reqpostal);
//
//
//                        Intent intent = new Intent(BillingActivity.this, PaymentActivity.class);
//                        intent.putExtra("Billing", billing);
//                        startActivity(intent);

                    }else{
                        Log.d("obj","object is null");
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(BillingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(BillingActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_order:
                if(isValidate()){
                    Order();
                }else{
                    break;
                }
                break;
        }
    }

    private boolean isValidate(){
        if(!ValidationUtility.EditTextValidator(txtFullName,txtPhone, txtAddress, txtCity)){
            GeneralHelper.ShowToast(this, "Full name, phone, address or city can not be empty!");
            return false;
        }else{
            return true;
        }
    }


    private void Order(){

       showProgress();

        try {
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);

            String fullname = txtFullName.getText().toString();
            String address = txtAddress.getText().toString();
            String phone = txtPhone.getText().toString();
            String city = txtCity.getText().toString();
            String postal = txtPostal.getText().toString();

            Cart cart = new Cart();
            List<OrderItemRequestVM> cartitems = cart.getCartItems(this);
            if(cartitems != null){
                cart.removeCartItems(this);
            }

            OrderRequest request = new OrderRequest();
            request.setOrderAddress(address);
            request.setOrderPhone(phone);
            request.setOrderCity(city);
            request.setOrderPostal(postal);
            request.setOrderItemRequestVM(cartitems);

            Call<OrderResponse> response = callerResponse.AddOrders(request);

            response.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    Object objResponse = response.body();
//                    OrderResponse objResponse = response.body();
                    if(objResponse == null){
                        try {
                            hideProgress();
//                            Toast.makeText(BillingActivity.this, objResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException nulle){
                            Toast.makeText(BillingActivity.this, "You are unauthorized to create order", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(BillingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
//                        Boolean isError = objResponse.getIserror();
//                        if(isError == true){
//                            Toast.makeText(BillingActivity.this, objResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        }else{
//
//                            OpenActivity(OrderReceiptActivity.class);
//                        }
                        hideProgress();
                    }
                }
                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    Toast.makeText(BillingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    OpenActivity(LoginActivity.class);
//                Log.d("ApiError",t.getMessage());
                    hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            hideProgress();
            Toast.makeText(BillingActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }

    }

//side menu and tool bar

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_billing);

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
        tv.setText("0");
        //   i.setOnClickListener(this);
        //  tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }
}
