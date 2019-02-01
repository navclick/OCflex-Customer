package com.example.fightersarena.ocflex_costumer.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.ImageHelper;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Models.Token;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public TextView tv;
    public ImageView i;

    public TokenHelper tokenHelper;
    public String TokenString;

    EditText txtFullName, txtPhone, txtAddressOne, txtEmail;
    ImageView imgProfile;
    private AsyncTask mMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Initializations
        tokenHelper = new TokenHelper(this);
        TokenString = tokenHelper.GetToken();

        if(TokenString == null){
            OpenActivity(LoginActivity.class);
        }else{
            //Declarations
            txtEmail = (EditText) findViewById(R.id.txt_register_email);
            txtFullName = (EditText) findViewById(R.id.txt_register_fullname);
            txtAddressOne = (EditText) findViewById(R.id.txt_register_address);
            txtPhone = (EditText) findViewById(R.id.txt_register_phone);
            imgProfile = (ImageView) findViewById(R.id.img_register_profile);

            //Side Menu and toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editprofile);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_editprofile);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_editprofile);
            navigationView.setNavigationItemSelectedListener(this);

            //Initializations
            GetProfile();
        }
    }

    private void GetProfile(){
        try {
            String token = TokenString;
            token = "Bearer " + token;
            IApiCaller apiCaller = ApiClient.createService(IApiCaller.class, token);
            Call<UserResponse> response = apiCaller.GetUser();

            response.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse objResponse = response.body();
                    if(objResponse == null){
                        try {
                            Toast.makeText(EditProfileActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(objResponse.getIserror()){
                            OpenActivity(LoginActivity.class);
                        }else{
                            String fullname = objResponse.getValue().getFullName();
                            String email = objResponse.getValue().getEmail();
                            String phone = objResponse.getValue().getPhoneNumber();
                            String addressOne = objResponse.getValue().getAddressOne();
                            String image = objResponse.getValue().getImage();

//                            ImageHelper imgHelper = new ImageHelper();
//                            Bitmap convertedImg = imgHelper.ConvertToBitmap(image);

                            txtEmail.setText(email);
                            txtFullName.setText(fullname);
                            txtAddressOne.setText(addressOne);
                            txtPhone.setText(phone);
                            if(image != null){
                                //imgProfile.setImageBitmap(convertedImg);
                                mMyTask = new AsyncTaskLoadImage().execute(image);
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateProfile(){

    }

    @Override
    public void onClick(View v) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_editprofile);
        if (id == R.id.menu_home) {
            // Handle the camera action
            mDrawerLayout.closeDrawers();
            // openActivityWithFinish(AboutActivity.class);

        } else if (id == R.id.menu_pro_req) {
            mDrawerLayout.closeDrawers();
            // openActivityProductRequest();
            //MenuHandler.orderHistory(this);

        } else if (id == R.id.menu_profile) {
            mDrawerLayout.closeDrawers();
            // openActivityProfile();
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_shopping) {
            mDrawerLayout.closeDrawers();
            // openActivity(ShoppingListActivity.class);
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_all_cat) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

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

    public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
//        private ImageButton imageView;
//        public AsyncTaskLoadImage(ImageButton imageView) {
//            this.imageView = imageView;
//        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgProfile.setImageBitmap(bitmap);
        }
    }
}
