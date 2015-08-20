package com.example.usemtburn_android_util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<Void, Void, Bitmap>{
    
    private String url;
    private ImageView imageView;
    
    public ImageLoader(String url, ImageView iv) {
        this.url = url;
        this.imageView = iv;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        InputStream input;
        Bitmap bitmap;
        try {
            input = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result == null) {
            return;
        }
        
        imageView.setImageBitmap(result);
    }
}