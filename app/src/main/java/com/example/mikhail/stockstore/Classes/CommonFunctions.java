package com.example.mikhail.stockstore.Classes;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StartActivity;
import com.example.mikhail.stockstore.StocksActivity;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mikhail on 09.12.15.
 */
public class CommonFunctions {

    // Установка картинки в ImageView из Bitmap
    public static void setPhotoToImageView(Bitmap photo, ImageView imageView) {
        imageView.setImageBitmap(photo);
    }

    // Загрузка картинки по URL в заданное ImageView
    public static void setPhotoToImageView(String photoURL, ImageView imageView) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL newurl = null;
        try {
            newurl = new URL(photoURL);
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            imageView.setImageBitmap(mIcon_val);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getPhoto(String src){
        // Асинхронное выполнение
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL newurl = null;
        try {
            newurl = new URL(src);
            return BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Bitmap getPhoto(int resId, Resources resources){
        Bitmap photo = BitmapFactory.decodeResource(resources, resId);
        return photo;
    }

    // Добавление пользовательского Navigation View и тулбара
    /*private void addNavigationView(Activity activity){
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(0xFF72BB53);//"#72bb53"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawer drawer = new Drawer();
        drawer
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_user).withName(R.string.drawer_item_profile),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_friends).withIcon(FontAwesome.Icon.faw_users).withBadge("3").withIdentifier(1).withName(R.string.drawer_item_friends),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_stocks).withIcon(FontAwesome.Icon.faw_rss).withBadge("16").withIdentifier(2).withName(R.string.drawer_item_stocks),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_subscriptions).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(3).withName(R.string.drawer_item_subscriptions),

                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withName(R.string.drawer_item_settings),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_exit).withIcon(FontAwesome.Icon.faw_sign_out).withName(R.string.drawer_item_exit)

                )
                .build();
        drawer.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem iDrawerItem) {
                if (iDrawerItem instanceof Nameable) {
                    switch (((Nameable) iDrawerItem).getNameRes()) {
                        case R.string.drawer_item_profile: {
                            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_stocks: {
                            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_subscriptions: {
                            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_settings: {
                            Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_friends: {
                            Toast.makeText(activity.getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_exit: {
                            WorkWithServer.deleteToken();
                            Intent intent = new Intent(StocksActivity.this, StartActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }*/
}

