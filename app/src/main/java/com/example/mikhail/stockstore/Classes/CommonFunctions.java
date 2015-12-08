package com.example.mikhail.stockstore.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mikhail on 09.12.15.
 */
public class CommonFunctions {

    // Загрузка картинки по URL в заданное ImageView
    public static void getPhotoByURL(String photoURL, ImageView imageView) {
        URL newurl = null;
        try {
            newurl = new URL(photoURL);
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            imageView.setImageBitmap(mIcon_val);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
