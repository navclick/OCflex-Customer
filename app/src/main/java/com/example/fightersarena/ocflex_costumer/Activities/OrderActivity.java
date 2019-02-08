package com.example.fightersarena.ocflex_costumer.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.GeneralHelper;
import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.fightersarena.ocflex_costumer.Helpers.Constants.IS_BILLING;
import static com.example.fightersarena.ocflex_costumer.Helpers.GeneralHelper.GetCurrentTimeUsingCalendar;

public class OrderActivity extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    String[] hours;
    public static int id, serviceRates;
    private int total;
    public static String serviceName;

    Button btnNext, btnAddMoreService;
    TextView txtServiceName, txtTotal;
    EditText txtDatePicker, txtHoursPicker, txtSecondsPicker, txtAMPM;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Spinner spinnerHours;
CheckBox checkone,checktwo,checkthree;
    ImageView imgCart;
    public TextView tv;
    public Boolean ProcessOder=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);


        checkone = (CheckBox) findViewById(R.id.checkone);
        checktwo = (CheckBox) findViewById(R.id.checktwo);
        checkthree = (CheckBox) findViewById(R.id.checkthree);


        //Side Menu and toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_order);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_order);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_order);
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

        //Initialization
        txtServiceName = (TextView) findViewById(R.id.txt_servicename);
        txtTotal = (TextView) findViewById(R.id.txt_total);
        txtDatePicker = (EditText) findViewById(R.id.txt_date);
        txtHoursPicker = (EditText) findViewById(R.id.txt_hourspicker);
        txtSecondsPicker = (EditText) findViewById(R.id.txt_secondspicker);
        txtAMPM = (EditText) findViewById(R.id.txt_ampm);

        txtDatePicker.setInputType(InputType.TYPE_NULL);
        txtHoursPicker.setInputType(InputType.TYPE_NULL);
        txtSecondsPicker.setInputType(InputType.TYPE_NULL);
        txtAMPM.setInputType(InputType.TYPE_NULL);


        String time = GetCurrentTimeUsingCalendar();
        String[] timearray = time.split(":");

        int time_hours = Integer.valueOf(timearray[0]);
        int time_seconds = Integer.valueOf(timearray[1]);

        String am_pm = "";
        if(time_hours > 11){
            if(time_hours != 12){
                time_hours = time_hours - 12;
                am_pm = "PM";
            }else{
                am_pm = "PM";
            }
        }
        else if(time_hours == 0){
            time_hours = 12;
            am_pm = "AM";
        }
        else
            am_pm = "AM";

        txtHoursPicker.setText(String.valueOf(time_hours));
        txtSecondsPicker.setText(String.valueOf(time_seconds));
        txtAMPM.setText(am_pm);


        btnNext = (Button) findViewById(R.id.btn_next);
        btnAddMoreService = (Button) findViewById(R.id.btn_addmoreservice);

        hours = getResources().getStringArray(R.array.hours_array);
        spinnerHours = (Spinner) findViewById(R.id.spinner_requiredhours);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hours_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerHours.setAdapter(adapter);

        // Setting values
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("id");
            serviceName = extras.getString("name");
            serviceRates = extras.getInt("rates");
            total = serviceRates * 2;
            txtTotal.setText(Integer.toString(total));

            String currentDate = GeneralHelper.getDateTime();
            txtDatePicker.setText(currentDate);

        }else{
            OpenActivity(ServicesListActivity.class);
        }

        // Implementations
        txtServiceName.setText(serviceName);
        //txt_rates.setText("$ " + serviceRates);

        // Listeners
        txtDatePicker.setOnClickListener(this);
        txtHoursPicker.setOnClickListener(this);
        txtSecondsPicker.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnAddMoreService.setOnClickListener(this);

        checkone.setOnClickListener(this);
        checktwo.setOnClickListener(this);
        checkthree.setOnClickListener(this);

        imgCart = findViewById(R.id.badge);
//        imgCart = (ImageView) findViewById(R.id.badge);
        //imgCart.setOnMenuItemClickListener(this);

        spinnerHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                String some = hours[index];
                some = some.replace("Hours","");
                some = some.trim();
                some = some.trim();
                int hour = serviceRates * Integer.parseInt(some);
                txtTotal.setText(String.valueOf(hour));
//                Toast.makeText(getBaseContext(), "You have selected item : " + hours[index], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.txt_date:
                OpenDatePicker();
                break;

            case R.id.txt_hourspicker:
                OpenTimePicker();
                break;

            case R.id.txt_secondspicker:
                OpenTimePicker();
                break;

            case R.id.btn_next:
                NextService();
                break;

            case R.id.btn_addmoreservice:
                OpenActivity(ServicesListActivity.class);
                break;

            case R.id.actionbar_notifcation_img:
                OpenActivity(CartActivity.class);
                break;

            case R.id.actionbar_notifcation_textview:
                OpenActivity(ServicesListActivity.class);
                break;

            case R.id.checkone:
                setCheckboxes();
                break;

            case R.id.checktwo:
                setCheckboxes();
                break;

            case R.id.checkthree:
                setCheckboxes();
                break;

        }
    }

    public  void setCheckboxes(){

        if(checkone.isChecked()==true && checktwo.isChecked()==true && checkthree.isChecked() ){

            btnNext.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corner));
            btnNext.setTextColor( getResources().getColor(R.color.colorPrimary));
            ProcessOder=true;
        }
        else{

            btnNext.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_disable));
            btnNext.setTextColor( getResources().getColor(R.color.colordisbale));
            ProcessOder=false;

        }


    }

    private void NextService(){
//        Log.d("id",String.valueOf(id));
//        String date = txtDatePicker.getText().toString();
//        String time = hours + ":" + seconds;

        if(!ProcessOder){

            return ;

        }
        String serviceName = txtServiceName.getText().toString();
        int total = Integer.valueOf(txtTotal.getText().toString());
        int hours = Integer.valueOf(txtHoursPicker.getText().toString());
        int seconds = Integer.valueOf(txtSecondsPicker.getText().toString());
        int orderhours = Integer.parseInt(spinnerHours.getSelectedItem().toString().replace("Hours","").trim());

        Cart cart = new Cart();
        cart.ServiceId = id;
        cart.ServiceName = serviceName;
        cart.Rates = serviceRates;
        cart.OrderDate = txtDatePicker.getText().toString();
        cart.OrderHours = orderhours;
        cart.OrderTime = hours + ":" + seconds;
        boolean response = Cart.addToCart(cart,this);
        if(response == false){
            Toast.makeText(OrderActivity.this, "Items already added to add to cart", Toast.LENGTH_SHORT).show();
            return;
        }else{
            String token = tokenHelper.GetToken();
            if(token == null){
                IS_BILLING = true;
                OpenActivity(LoginActivity.class);
            }else{
                Intent intent = new Intent(OrderActivity.this, BillingActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
//            Log.d("Success","items successfully added to cart");
        }
    }

    private void OpenDatePicker() {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialog = new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // set day of month , month and year value in the edit text
                txtDatePicker.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                //txtDatePicker.setText(mMonth + "/" + mDay + "/" + mYear);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void OpenTimePicker(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(OrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm = "";
                if(selectedHour > 11)
                {
                    if(selectedHour != 12){
                        selectedHour = selectedHour - 12;
                        am_pm = "PM";
                    }else{
                        am_pm = "PM";
                    }
                }
                else if(selectedHour == 0){
                    selectedHour = 12;
                    am_pm = "AM";
                }
                else
                    am_pm = "AM";
                //txtHoursPicker.setText(selectedHour + ":" + selectedMinute);
                txtHoursPicker.setText(Integer.toString(selectedHour));
                txtSecondsPicker.setText(Integer.toString(selectedMinute));
                txtAMPM.setText(am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_order);

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
        imgCart =notifCount.findViewById(R.id.actionbar_notifcation_img);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        //tv.setText("12");
        tv.setText(String.valueOf(Cart.getCartItemsCount(this)));
        imgCart.setOnClickListener(this);
        tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

}
