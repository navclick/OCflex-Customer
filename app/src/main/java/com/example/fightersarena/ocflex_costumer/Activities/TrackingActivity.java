package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.Constants;
import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.Models.MyOrder;
import com.example.fightersarena.ocflex_costumer.Models.MyOrders;
import com.example.fightersarena.ocflex_costumer.Models.TrackingResponse;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingActivity extends BaseActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public TextView tv;
    public ImageView i;
    private GoogleMap mMap;
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    public int index =-1;
    public List<TrackingResponse.TrackValue> TrackList;
    final int MARKER_UPDATE_INTERVAL = 10000; /* milliseconds */
    Handler handler = new Handler();
    public Marker AssociateMarker = null;
    float zoomLevel = (float) 15.0;
    public String TokenString;
    public  static String AssociateName,AssociateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        TrackList = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tracking);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_tracking);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_tracking);
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


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

       // LatLng custLocation = getLocationFromAddress(this,OrderDetails.CustomerAddress);


        LatLng custLocation= HAMBURG;
        //Log.d(Constants.TAG,"cusLoc"+ String.valueOf(custLocation.latitude));
        //  mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );


        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(custLocation);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
//        markerOptions.title(OrderDetails.CustomerName);

        markerOptions.title("test");

        // Clears the previously touched position



        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation,zoomLevel));

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_tracking);

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
        tv.setText(String.valueOf(Cart.getCartItemsCount(this)));
          i.setOnClickListener(this);
          tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){


            case R.id.actionbar_notifcation_img:
                OpenActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:
                OpenActivity(CartActivity.class);
                break;


        }
    }


    Runnable setupTracking = new Runnable() {
        @Override
        public void run() {

            Log.d("Service Running","Service Running");

            long timeMillis = System.currentTimeMillis();
            long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
            if(index < 0){
                GetTrackFromServer();

            }
            else{

                UpdateTrack();

            }


            handler.postDelayed(this, MARKER_UPDATE_INTERVAL);
        }
    };




    private void GetTrackFromServer(){

        try {
            TokenString=tokenHelper.GetToken();
            String token = "Bearer " + TokenString;
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class, token);
            Call<TrackingResponse> response = callerResponse.GetTrack(AssociateID);
            TrackList.clear();
            response.enqueue(new Callback<TrackingResponse>() {


                @Override
                public void onResponse(Call<TrackingResponse> call, Response<TrackingResponse> response) {
                    Gson gson = new Gson();
                    String Reslog= gson.toJson(response);
                    Log.d(Constants.TAG, Reslog);

                    TrackingResponse obj = response.body();

                    if(!obj.getIserror()){



                        TrackList = obj.getValue();
                        index=(TrackList.size()-1);

                        UpdateTrack();
                    }

                }
                @Override
                public void onFailure(Call<TrackingResponse> call, Throwable t) {


                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());

        }
    }





public void UpdateTrack(){

        if(index == -1){

          //  GetTrackFromServer();
            return;

        }

    TrackingResponse.TrackValue  TrackObj= TrackList.get(index);
    index--;

    Double Lat = Double.parseDouble(TrackObj.getLatitude());
    Double Long = Double.parseDouble(TrackObj.getLongitude());

    LatLng AssociateLocation = new LatLng(Lat,Long );

    Toast toast = Toast.makeText(getApplicationContext(),
            "Latitude:" + AssociateLocation.latitude + ", Longitude:" + AssociateLocation.longitude,
            Toast.LENGTH_SHORT);

    toast.show();


    if (this.AssociateMarker == null) {




        this.AssociateMarker = mMap.addMarker(new MarkerOptions()
                .position(AssociateLocation)

                .title(AssociateName)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.pin_rickshaw)))
                .snippet(AssociateName)

        );
        this.AssociateMarker.showInfoWindow();
        // mMap.addMarker(MeMarker).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(AssociateLocation));


    } else {
        // Log.i("APITEST:", "set" + String.valueOf(rickshawLocation.latitude) + " " + String.valueOf(rickshawLocation.longitude));
        this.AssociateMarker.setTitle(AssociateName);
        this.AssociateMarker.setPosition(AssociateLocation);

        this.AssociateMarker.setSnippet(AssociateName);
        this.AssociateMarker.showInfoWindow();
        this.animateMarker(this.AssociateMarker, AssociateLocation, false);

    }





   // mMap.moveCamera(CameraUpdateFactory.newLatLng(Associate));


}

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {

        Log.e("APITEST", "Tracking Distory");
        index= -1;

        handler.removeCallbacks(setupTracking);

        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        OpenActivity(MyOrderActivity.class);
        finish();
    }

    @Override
    protected void onPause() {

        Log.e("APITEST", "Tracking Distory");
        index= -1;

        //handler.removeCallbacks(setupTracking);

        super.onPause();

    }

    @Override
    protected void onStart() {

        // after the first time, tracking would run every 30 secs.

        handler.postDelayed(setupTracking, MARKER_UPDATE_INTERVAL);
        Log.e("APITEST", "Tracking Start");
        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
        Log.e("APITEST", "Tracking Distory");
        index = -1;

        handler.removeCallbacks(setupTracking);



    }



}
