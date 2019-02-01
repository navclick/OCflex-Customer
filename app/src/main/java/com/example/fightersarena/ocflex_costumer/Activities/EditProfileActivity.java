package com.example.fightersarena.ocflex_costumer.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.ImageHelper;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Models.Token;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity{

    public TokenHelper tokenHelper;
    public String TokenString;

    EditText txtFullName, txtPhone, txtAddressOne, txtEmail;
    ImageView imgProfile;
    private AsyncTask mMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Initializations
        tokenHelper = new TokenHelper(this);
        TokenString = tokenHelper.GetToken();

        if(TokenString == null){
            OpenActivity(LoginActivity.class);
        }else{
            //Declarations
            txtEmail = (EditText) findViewById(R.id.txt_email);
            txtFullName = (EditText) findViewById(R.id.txt_fullname);
            txtAddressOne = (EditText) findViewById(R.id.txt_address);
            txtPhone = (EditText) findViewById(R.id.txt_phone);
            imgProfile = (ImageView) findViewById(R.id.img_profile);

            //Initializations
            GetProfile();
        }
    }

    private void GetProfile(){
        try {
            String token = TokenString;
            token = "Bearer " + token;
            IApiCaller apiCaller = ApiClient.createService(IApiCaller.class, token);
            Call<UserResponse> response = apiCaller.GetUser();

            response.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse objResponse = response.body();
                    if(objResponse == null){
                        try {
                            Toast.makeText(EditProfileActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(objResponse.getIserror()){
                            OpenActivity(LoginActivity.class);
                        }else{
                            String fullname = objResponse.getValue().getFullName();
                            String email = objResponse.getValue().getEmail();
                            String phone = objResponse.getValue().getPhoneNumber();
                            String addressOne = objResponse.getValue().getAddressOne();
                            String image = objResponse.getValue().getImage();

//                            ImageHelper imgHelper = new ImageHelper();
//                            Bitmap convertedImg = imgHelper.ConvertToBitmap(image);

                            txtEmail.setText(email);
                            txtFullName.setText(fullname);
                            txtAddressOne.setText(addressOne);
                            txtPhone.setText(phone);
                            if(image != null){
                                //imgProfile.setImageBitmap(convertedImg);
                                mMyTask = new AsyncTaskLoadImage().execute(image);
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
                }
            });

        }catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateProfile(){

    }

//    public Bitmap ConvertToBitmap(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
//        private ImageButton imageView;
//        public AsyncTaskLoadImage(ImageButton imageView) {
//            this.imageView = imageView;
//        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgProfile.setImageBitmap(bitmap);
        }
    }
}
