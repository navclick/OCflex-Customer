package com.example.fightersarena.ocflex_costumer.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.fightersarena.ocflex_costumer.Activities.LoginActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.Constants;
import com.example.fightersarena.ocflex_costumer.Helpers.ProgressLoader;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;

public class BaseFragment extends Fragment {
    protected ViewDataBinding parentBinding;
    public TokenHelper tokenHelper;
    private ProgressLoader progressLoader;
Activity c;

    private FragmentActivity myContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tokenHelper = new TokenHelper(activity);
        c=activity;
        myContext=(FragmentActivity) activity;

    }


    public void OpenActivity(Class activity){
        startActivity(new Intent(c,activity));
    }


    public void showProgress() {
        try {
            if (progressLoader == null) {
                progressLoader = new ProgressLoader();
            }

            progressLoader.show(myContext.getSupportFragmentManager(), Constants.TAG);
        } catch (IllegalStateException e) {
            // Log.e(TAG, "showProgress:" + e.getMessage());
        }

    }

    public void hideProgress() {
        if (progressLoader != null) {
            try {
                progressLoader.dismissAllowingStateLoss();
            } catch (Exception e) {
                //Log.e(Constants.TAG, "hideProgress:" + e.getMessage());
            }
        }
    }




}
