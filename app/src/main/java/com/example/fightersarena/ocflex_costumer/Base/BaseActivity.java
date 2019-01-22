package com.example.fightersarena.ocflex_costumer.Base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fightersarena.ocflex_costumer.Activities.LoginActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;

public class BaseActivity extends AppCompatActivity {


    //Declarations
    protected ViewDataBinding parentBinding;
    public TokenHelper tokenHelper;

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
}
