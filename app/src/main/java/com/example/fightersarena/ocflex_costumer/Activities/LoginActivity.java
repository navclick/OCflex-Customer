package com.example.fightersarena.ocflex_costumer.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.GeneralHelper;
import com.example.fightersarena.ocflex_costumer.Models.Token;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;
import com.example.fightersarena.ocflex_costumer.Utility.ValidationUtility;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    // Declarations
    Button btnSignIn;
    EditText txtEmail, txtPassword;
    TextView txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Implementations
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        txtCreateAccount = (TextView) findViewById(R.id.txtCreateAccount);

        // Listeners
        btnSignIn.setOnClickListener(this);
        txtCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.txtCreateAccount:
                OpenActivity(RegisterActivity.class);
                break;

            case R.id.btn_signin:
                if(isValidate()){
                    SignIn();
                }else{
                    break;
                }
                break;
        }
    }

    private boolean isValidate(){
        if(!ValidationUtility.EditTextValidator(txtEmail,txtPassword)){
            GeneralHelper.ShowToast(this, "Email or password can not be empty!");
            return false;
        }else{
            return true;
        }
    }

    private void SignIn(){
        try {
            showProgress();
            IApiCaller token = ApiClient.createService(IApiCaller.class);
            String username = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            Call<Token> response = token.GetToken(username,password,"password");

            response.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Token objToken = response.body();
                    if(objToken == null){
                        try {
                            hideProgress();
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String err = jObjError.getString("error_description").toString();
                            Log.d("Error", err);
                            Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                      hideProgress();
                       }
                    }else{
                        String access_token = objToken.getAccessToken();
                        boolean isTokenSet = tokenHelper.SetToken(objToken.getAccessToken());
                        if(isTokenSet == true){
                            // TODO: Open main screen if token is set successfully
                            GetProfile();
                        }
                    }
                }
                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
               Log.d("ApiError",t.getMessage());
               hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(LoginActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }


    private void GetProfile(){
        try {
            String token = tokenHelper.GetToken();
            token = "Bearer " + token;
            IApiCaller apiCaller = ApiClient.createService(IApiCaller.class, token);
            Call<UserResponse> response = apiCaller.GetUser();

            response.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse objResponse = response.body();
                    if(objResponse == null){
                        try {
                           // Toast.makeText(EditProfileActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                           // Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                      hideProgress();
                        }
                    }else{
                        if(objResponse.getIserror()){
                            OpenActivity(LoginActivity.class);
                        }else{
                            hideProgress();
                            tokenHelper.SetUserName(objResponse.getValue().getFullName());
                            tokenHelper.SetUserEmail(objResponse.getValue().getEmail());
                            tokenHelper.SetUserPhoto(objResponse.getValue().getImage());
                            OpenActivity(ServicesListActivity.class);
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                   // Toast.makeText(EditProfileActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
              hideProgress();
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            //Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

}
