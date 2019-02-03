package com.example.fightersarena.ocflex_costumer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fightersarena.ocflex_costumer.Base.BaseActivity;
import com.example.fightersarena.ocflex_costumer.Helpers.TokenHelper;
import com.example.fightersarena.ocflex_costumer.Models.GeneralResponse;
import com.example.fightersarena.ocflex_costumer.Models.Register;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;
import com.example.fightersarena.ocflex_costumer.Network.ApiClient;
import com.example.fightersarena.ocflex_costumer.Network.IApiCaller;
import com.example.fightersarena.ocflex_costumer.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public TextView tv;
    public ImageView i, imgProfile;
    Bitmap bmp;

    public TokenHelper tokenHelper;
    public String TokenString, mediaPath;

    EditText txtFullName, txtPhone, txtAddressOne, txtEmail;
    TextView txtviewUploadPhoto;
    Button btnUpdateProfile;
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
            txtEmail = (EditText) findViewById(R.id.txt_register_email);
            txtFullName = (EditText) findViewById(R.id.txt_register_fullname);
            txtAddressOne = (EditText) findViewById(R.id.txt_register_address);
            txtPhone = (EditText) findViewById(R.id.txt_register_phone);
            txtviewUploadPhoto = (TextView) findViewById(R.id.txtview_uploadphoto);
            imgProfile = (ImageView) findViewById(R.id.img_register_profile);
            btnUpdateProfile = (Button) findViewById(R.id. btn_updateprofile);

            //Side Menu and toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editprofile);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_editprofile);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_editprofile);
            navigationView.setNavigationItemSelectedListener(this);

            // Listeners
            txtviewUploadPhoto.setOnClickListener(this);
            btnUpdateProfile.setOnClickListener(this);

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

    private String ConvertToBase64() {
        String base64 = "";

        File file = new File(mediaPath);

        // Parsing any Media type file
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        IApiCaller apiCaller = ApiClient.createService(IApiCaller.class);
        Call<GeneralResponse> call = apiCaller.GetBase64(fileToUpload);

        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                GeneralResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getIserror()) {
                        Log.d("error",serverResponse.getMessage());
                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("success",serverResponse.getMessage());
                        //Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
            }
            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                Log.d("ApiError",t.getMessage());
            }
        });

//        try {
//            String token = TokenString;
//            token = "Bearer " + token;
//            IApiCaller apiCaller = ApiClient.createService(IApiCaller.class, true);
//
//            File file = new File("http://192.168.100.2:82/images/dummyuserone.jpg");
//            RequestBody reqFile = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImageUri)), file);
//            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
//
//            Call<GeneralResponse> response = apiCaller.GetBase64(body);
//
//            response.enqueue(new Callback<GeneralResponse>() {
//                @Override
//                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
//                    GeneralResponse objResponse = response.body();
//                    if(objResponse == null){
//                        try {
//                            Toast.makeText(EditProfileActivity.this, "Server error", Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                            Log.d("Exception", e.getMessage());
//                            Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        if(objResponse.getIserror()){
//                            OpenActivity(LoginActivity.class);
//                        }else{
//
//                        }
//                    }
//                }
//                @Override
//                public void onFailure(Call<GeneralResponse> call, Throwable t) {
//                    Toast.makeText(EditProfileActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
////                Log.d("ApiError",t.getMessage());
//                }
//            });
//
//        }catch (Exception e){
//            Log.d("error",e.getMessage());
//            Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//        }
        return base64;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.txtview_uploadphoto:
                OpenGallery();
                break;

            case R.id.btn_updateprofile:
                ConvertToBase64();
                break;
        }
    }

    private void OpenGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 1:
                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    Log.d("filepath", mediaPath);


                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        imgProfile.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultcode, Intent intent)
//    {
//        super.onActivityResult(requestCode, resultcode, intent);
//
//        if (requestCode == 1)
//        {
//            if (intent != null && resultcode == RESULT_OK)
//            {
//
//                Uri selectedImage = intent.getData();
//
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
//
//                if(bmp != null && !bmp.isRecycled())
//                {
//                    bmp = null;
//                }
//
//                bmp = BitmapFactory.decodeFile(filePath);
//                imgProfile.setBackgroundResource(0);
//                imgProfile.setImageBitmap(bmp);
//            }
//            else
//            {
//                Log.d("Status:", "Photopicker canceled");
//            }
//        }
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_editprofile);
        if (id == R.id.menu_home) {
            // Handle the camera action
            mDrawerLayout.closeDrawers();
            // openActivityWithFinish(AboutActivity.class);

        } else if (id == R.id.menu_pro_req) {
            mDrawerLayout.closeDrawers();
            // openActivityProductRequest();
            //MenuHandler.orderHistory(this);

        } else if (id == R.id.menu_profile) {
            mDrawerLayout.closeDrawers();
            // openActivityProfile();
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_shopping) {
            mDrawerLayout.closeDrawers();
            // openActivity(ShoppingListActivity.class);
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_all_cat) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

        }

        return  true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        i =notifCount.findViewById(R.id.actionbar_notifcation_img);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        //tv.setText("12");
        tv.setText("0");
        //   i.setOnClickListener(this);
        //  tv.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

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
