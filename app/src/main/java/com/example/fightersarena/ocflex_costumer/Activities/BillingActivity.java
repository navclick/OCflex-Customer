package com.example.fightersarena.ocflex_costumer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Models.Token;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends BaseActivity {

    EditText txtFullName, txtCompanyName, txtAddress, txtPhone, txtCity, txtPostal;
    public TokenHelper tokenHelper;
    public String TokenString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializations
        tokenHelper = new TokenHelper(this);
        TokenString = tokenHelper.GetToken();

        if(TokenString == null){
            OpenActivity(LoginActivity.class);
        }else{
            setContentView(R.layout.billing);


            txtFullName = (EditText) findViewById(R.id.txt_fullname);
            txtCompanyName = (EditText) findViewById(R.id.txt_companyname);
            txtAddress = (EditText) findViewById(R.id.txt_address);
            txtPhone = (EditText) findViewById(R.id.txt_phone);
            txtCity = (EditText) findViewById(R.id.txt_city);
            txtPostal = (EditText) findViewById(R.id.txt_postal);

            GetUser();
        }

    }

    private void GetUser(){
        try {
            String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOiIyZDU0Y2JlZi1mNmVjLTQ1OGMtOGRlNS1iZGEzMTRhMTg0MDQiLCJ1bmlxdWVfbmFtZSI6ImFkbWlub25lQGdtYWlsLmNvbSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vYWNjZXNzY29udHJvbHNlcnZpY2UvMjAxMC8wNy9jbGFpbXMvaWRlbnRpdHlwcm92aWRlciI6IkFTUC5ORVQgSWRlbnRpdHkiLCJBc3BOZXQuSWRlbnRpdHkuU2VjdXJpdHlTdGFtcCI6IjdiMzgwYjlhLWVjYjQtNDJhMC04Y2M4LTZlMTI2YmMyYWY0NiIsInJvbGUiOlsiQWRtaW4iLCJTdXBlckFkbWluIl0sImlzcyI6Imh0dHA6Ly9vY2ZsZXhhcGkuaW5zaWRlZGVtby5jb20vIiwiYXVkIjoiNDE0ZTE5MjdhMzg4NGY2OGFiYzc5ZjcyODM4MzdmZDEiLCJleHAiOjE1NDgxNTY1OTAsIm5iZiI6MTU0ODA3MDE5MH0.XK5Xp3AIIHhZ37GS3jzqag1exg-iJ4N55g7kcctaDlQ";
            IApiCaller client = ApiClient.createService(IApiCaller.class, token);
            //String token = TokenString;
            //String bearer = "Bearer " + token;
            Call<UserResponse> response = client.GetUser();

            response.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse objResponse = response.body();
                    if(objResponse != null){

                        String fullname = objResponse.getValue().getFullName();
                        String companyname = objResponse.getValue().getCompanyName();
                        String address = objResponse.getValue().getAddressOne();
                        String phone = objResponse.getValue().getPhoneNumber();
                        String city = objResponse.getValue().getCity();
                        String postal = objResponse.getValue().getPostalCode();

                        txtFullName.setText(fullname);
                        txtCompanyName.setText(companyname);
                        txtAddress.setText(address);
                        txtPhone.setText(phone);
                        txtCity.setText(city);
                        txtPostal.setText(postal);

                    }else{
                        Log.d("obj","object is null");
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(BillingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(BillingActivity.this, "Email or password is not correct", Toast.LENGTH_SHORT).show();
        }
    }
}
