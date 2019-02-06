package com.example.fightersarena.ocflex_costumer.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.Constants;
import com.example.fightersarena.ocflex_costumer.Helpers.GeneralHelper;
import com.example.fightersarena.ocflex_costumer.Models.Register;
import com.example.fightersarena.ocflex_costumer.Models.RegisterRequest;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;
import com.example.fightersarena.ocflex_costumer.Utility.ValidationUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    //Declarations
    Button btnRegister;
    EditText txtFullName, txtEmail, txtPassword, txtRepeatPassword;
    TextView txt_terms;
    CheckBox check_terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Implementations
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtFullName = (EditText) findViewById(R.id.txt_fullname);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        txtRepeatPassword = (EditText) findViewById(R.id.txt_repeatpassword);


        txt_terms = (TextView) findViewById(R.id.txt_terms);
        check_terms = (CheckBox) findViewById(R.id.check_terms);

        //Listeners
        btnRegister.setOnClickListener(this);
        txt_terms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:

              if(check_terms.isChecked()){
                if(isValidate()){
                    Register();

                }}
                else{
                  showMessageDailog("SignIn", Constants.MESSAGE_ACCEPT_TERMS);

              }
                break;
            case R.id.txt_terms:
                OpenActivity(TermsDailog.class);
                break;

        }
    }

    private boolean isValidate(){

        if(!ValidationUtility.EditTextValidator(txtFullName, txtEmail, txtPassword, txtRepeatPassword)){
            GeneralHelper.ShowToast(this, "Email or password can not be empty!");
            return false;
        }else{
            return true;
        }
    }

    private void Register(){
        try {
            showProgress();
            IApiCaller callerResponse = ApiClient.createService(IApiCaller.class);
            String fullname = txtFullName.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String repeatpassword = txtRepeatPassword.getText().toString();
            Integer level = 5;

            RegisterRequest request = new RegisterRequest();
            request.setFullName(fullname);
            request.setEmail(email);
            request.setPassword(password);
            request.setConfirmPassword(repeatpassword);
            request.setLevel(level);

            Call<Register> response = callerResponse.Register(request);

            response.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    Register objResponse = response.body();
                    if(objResponse == null){
                        try {
                            hideProgress();
//                            JSONObject jObjError = new JSONObject(response.errorBody().string());
//                            String err = jObjError.getString("error_description").toString();
//                            Log.d("Error", err);
//                            Toast.makeText(RegisterActivity.this, err, Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, objResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            hideProgress();
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Boolean isError = objResponse.getIserror();
                        if(isError == true){
                            hideProgress();
                            // TODO: Open main screen if token is set successfully
                            Toast.makeText(RegisterActivity.this, objResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            hideProgress();
                            OpenActivity(LoginActivity.class);
                        }
                    }
                }
                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());

                    hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            hideProgress();
            Toast.makeText(RegisterActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }
}
