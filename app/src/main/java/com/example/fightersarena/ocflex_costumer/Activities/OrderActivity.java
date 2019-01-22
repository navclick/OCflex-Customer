package com.example.fightersarena.ocflex_costumer.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Models.Cart;
import com.example.fightersarena.ocflex_costumer.R;

import java.util.Calendar;

import static com.example.fightersarena.ocflex_costumer.Helpers.Constants.IS_BILLING;

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    public static Integer id;

    Button btnNext, btnAddMoreService;
    TextView txtServiceName, txt_rates;
    EditText txtDatePicker, txtHoursPicker, txtSecondsPicker;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        //Initialization
        txtServiceName = (TextView) findViewById(R.id.txt_servicename);
        txt_rates = (TextView) findViewById(R.id.txt_rates);
        txtDatePicker = (EditText) findViewById(R.id.txt_date);
        txtHoursPicker = (EditText) findViewById(R.id.txt_hourspicker);
        txtSecondsPicker = (EditText) findViewById(R.id.txt_secondspicker);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnAddMoreService = (Button) findViewById(R.id.btn_addmoreservice);

        spinner = (Spinner) findViewById(R.id.spinner_requiredhours);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hours_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Setting values
        id = Integer.parseInt(getIntent().getExtras().getString("id"));
        String serviceName = getIntent().getExtras().getString("name");
        String serviceRates = getIntent().getExtras().getString("rates");

        // Implementations
        txtServiceName.setText(serviceName);
        txt_rates.setText(serviceRates);
        //txt_rates.setText("$ " + serviceRates);

        // Listeners
        txtDatePicker.setOnClickListener(this);
        txtHoursPicker.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnAddMoreService.setOnClickListener(this);

//        Log.d("service name", serviceName);
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

            case R.id.btn_next:
                NextService();
                break;

            case R.id.btn_addmoreservice:

                break;

        }
    }

    private void NextService(){
//        Log.d("id",String.valueOf(id));
//        String date = txtDatePicker.getText().toString();
//        String time = hours + ":" + seconds;

        String serviceName = txtServiceName.getText().toString();
        int rates = Integer.valueOf(txt_rates.getText().toString());
        int hours = Integer.valueOf(txtHoursPicker.getText().toString());
        int seconds = Integer.valueOf(txtSecondsPicker.getText().toString());

        Cart cart = new Cart();
        cart.ServiceId = id;
        cart.Rates = rates * hours;
        cart.OrderDate = txtDatePicker.getText().toString();
        cart.OrderHours = Integer.valueOf(txtHoursPicker.getText().toString());
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

    private void OpenDatePicker(){
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        txtDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

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
                //txtHoursPicker.setText(selectedHour + ":" + selectedMinute);
                txtHoursPicker.setText(Integer.toString(selectedHour));
                txtSecondsPicker.setText(Integer.toString(selectedMinute));

            }
        }, hour, minute, true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }
}
