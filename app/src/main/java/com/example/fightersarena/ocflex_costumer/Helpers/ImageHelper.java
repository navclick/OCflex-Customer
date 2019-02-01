package com.example.fightersarena.ocflex_costumer.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageHelper extends AsyncTask<String, Void, Bitmap> {

//    private String ImageSrc;
//    public ImageHelper(String imgsrc){
//        this.ImageSrc = imgsrc;
//    }

    public Bitmap ConvertToBitmap(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap img = ConvertToBitmap(strings[0]);
        return img;
    }
}
