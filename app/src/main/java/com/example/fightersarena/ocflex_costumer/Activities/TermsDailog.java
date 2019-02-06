package com.example.fightersarena.ocflex_costumer.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.R;


public class TermsDailog extends BaseActivity implements View.OnClickListener {
    Button btn_tc_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_terms_condictions);
        btn_tc_back=(Button) findViewById(R.id.btn_tc_back);
        btn_tc_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_tc_back:
                this.onBackPressed();
                break;


        }
    }

}
