package com.example.fightersarena.ocflex_costumer.Base;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fightersarena.ocflex_costumer.Activities.LoginActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.Constants;
import com.example.fightersarena.ocflex_costumer.Helpers.ProgressLoader;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;

public class BaseActivity extends AppCompatActivity {


    //Declarations
    protected ViewDataBinding parentBinding;
    public TokenHelper tokenHelper;
    private ProgressLoader progressLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializations
        tokenHelper = new TokenHelper(this);
    }

    public void OpenActivity(Class activity){
        startActivity(new Intent(this,activity));
    }

    public <S> void OpenActivity(Class<S> activity, Boolean isTokenCheck){
        if(tokenHelper.GetToken() == null || tokenHelper.GetToken() == ""){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, activity));
        }
    }

    public View getView() {
        if (parentBinding != null)
            return parentBinding.getRoot();
        else return null;
    }


    public void showProgress() {
        try {
            if (progressLoader == null) {
                progressLoader = new ProgressLoader();
            }

            progressLoader.show(getSupportFragmentManager(), Constants.TAG);
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


    public static void startActivity(Context context, Class activity) {

        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }


    public void logOut(){

        tokenHelper.removeALL();
        // openActivity(Login.class);
        startActivity(this,LoginActivity.class);
    }


}
