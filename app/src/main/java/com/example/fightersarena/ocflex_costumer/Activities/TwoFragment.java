package com.example.fightersarena.ocflex_costumer.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fightersarena.ocflex_costumer.Base.BaseFragment;
import com.example.fightersarena.ocflex_costumer.R;

public class TwoFragment extends BaseFragment implements View.OnClickListener {
Button Btn_emp;
    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_two,
                container, false);

        Btn_emp = (Button) view.findViewById(R.id.Btn_emp);
        Btn_emp.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.Btn_emp:

                OpenActivity(EmploymentOppActivity.class);
                break;


        }
    }
}