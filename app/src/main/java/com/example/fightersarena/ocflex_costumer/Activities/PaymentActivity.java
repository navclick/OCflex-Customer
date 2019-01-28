package com.example.fightersarena.ocflex_costumer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.fightersarena.ocflex_costumer.Models.Billing;
import com.example.fightersarena.ocflex_costumer.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments);

        Intent intent=getIntent();
        Billing billing = (Billing) intent.getSerializableExtra("Billing");
        String fullname = billing.getFullName();
        String phone = billing.getPhone();
        String address = billing.getAddress();
        String city = billing.getCity();
        String postal = billing.getPostal();

        Log.d("fullname",fullname);
    }
}
