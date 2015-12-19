package com.example.mikhail.stockstore.Classes;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncGetPhoto;
import com.example.mikhail.stockstore.Modules.SlidingTabLayout;
import com.example.mikhail.stockstore.ProfileActivity;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StartActivity;
import com.example.mikhail.stockstore.StocksActivity;
import com.example.mikhail.stockstore.SubscribesActivity;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        AsyncGetPhoto loader = new AsyncGetPhoto();

        URL newurl = null;
        try {
            newurl = new URL(photoURL);
            Bitmap mIcon_val = loader.execute(photoURL).get(); //BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            imageView.setImageBitmap(mIcon_val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getPhoto(String photoURL){
        // Асинхронное выполнение
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        AsyncGetPhoto loader = new AsyncGetPhoto();

        URL newurl = null;
        try {
            newurl = new URL(photoURL);
            return loader.execute(photoURL).get();//BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
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
    public static void addNavigationView(final AppCompatActivity activity) {
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(0xFF72BB53);//"#72bb53"
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                            activity.startActivity(new Intent(activity.getBaseContext(), ProfileActivity.class));
                            break;
                        }
                        case R.string.drawer_item_stocks: {
                            Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                            activity.startActivity(new Intent(activity.getBaseContext(), StocksActivity.class));
                            break;
                        }
                        case R.string.drawer_item_subscriptions: {
                            Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                            activity.startActivity(new Intent(activity.getBaseContext(), SubscribesActivity.class));
                            break;
                        }
                        case R.string.drawer_item_settings: {
                            Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_friends: {
                            Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_exit: {
                            WorkWithServer.deleteToken();
                            activity.startActivity(new Intent(activity.getBaseContext(), StartActivity.class));

                        }
                    }
                }
            }
        });
    }

    // Добавление вкладок
    public static void addTabs(final AppCompatActivity activity){
        ViewPager pager;
        ViewPagerAdapter adapter;
        SlidingTabLayout tabs;
        // Заголовки вкладок
        CharSequence Titles[]={"Лента","Компании","Категории"};
        // Количество вкладок
        int Numboftabs =3;
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(activity.getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) activity.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) activity.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return activity.getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    // Парсит дату из формата сервера в стандартный
    public static Date dateFormat(String target){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            return sdf.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
