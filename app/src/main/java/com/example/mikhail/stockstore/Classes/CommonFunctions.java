package com.example.mikhail.stockstore.Classes;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Adapters.FriendsViewPagerAdapter;
import com.example.mikhail.stockstore.Adapters.StocksViewPagerAdapter;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.FriendsActivity;
import com.example.mikhail.stockstore.GeneratedCodeActivity;
import com.example.mikhail.stockstore.Modules.SlidingTabLayout;
import com.example.mikhail.stockstore.ProfileActivity;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.LoginActivity;
import com.example.mikhail.stockstore.StocksActivity;
import com.example.mikhail.stockstore.SubscribesActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.callback.CallbackHandler;

/**
 * Created by mikhail on 09.12.15.
 */
public class CommonFunctions {

    public static Bitmap getPhoto(int resId, Resources resources){
        Bitmap photo = BitmapFactory.decodeResource(resources, resId);
        return photo;
    }

    private static View.OnClickListener StandartActionToolbarNav(final Activity activity, String typeNavBtn){
        switch (typeNavBtn){
            case CommonConstants.TOOLBAR_NAV_CLOSE:
            {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                };
            }
            case CommonConstants.TOOLBAR_NAV_BACK:
            {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onBackPressed();
                    }
                };
            }
            default:
            {
                return null;
            }
        }
    }

    // Пустой тулбар
    public static Toolbar setToolbar(final AppCompatActivity activity, int resourseToolbar){
        Toolbar toolbar = (Toolbar) activity.findViewById(resourseToolbar);
        activity.setSupportActionBar(toolbar);
        return toolbar;
    }

    // Установка тулбара, иконки навигации и стандартного действия при клике
    public static Toolbar setToolbar(final AppCompatActivity activity, int resourseToolbar, String typeNavBtn)
    {
        Toolbar toolbar = CommonFunctions.setToolbar(activity, resourseToolbar);
        int resourseId;
        switch (typeNavBtn){
            case CommonConstants.TOOLBAR_NAV_BACK:
            {
                resourseId = R.drawable.abc_ic_ab_back_mtrl_am_alpha;
                break;
            }
            case CommonConstants.TOOLBAR_NAV_CLOSE:
            {
                resourseId = R.drawable.abc_ic_clear_mtrl_alpha;
                break;
            }
            default:
            {
                return toolbar;
            }
        }
        toolbar.setNavigationIcon(resourseId);
        toolbar.setNavigationOnClickListener(StandartActionToolbarNav(activity, typeNavBtn));
        return toolbar;
    }

    // Установка иконки навигации на тулбаре и определение действия при нажатии
    public static Toolbar setToolbar(final AppCompatActivity activity, int resourseToolbar, String typeNavBtn, View.OnClickListener onNavBtnClicked){
        Toolbar toolbar = setToolbar(activity, resourseToolbar, typeNavBtn);
        toolbar.setNavigationOnClickListener(onNavBtnClicked);
        return toolbar;
    }

    // Добавление пользовательского Navigation View и тулбара
    public static void addNavigationView(final AppCompatActivity activity, Toolbar toolbar) {
        // Handle Toolbar
       // Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
       // activity.setSupportActionBar(toolbar);

      //  toolbar.setBackgroundColor(0xFF72BB53);//"#72bb53"
        final String PROFILE = "Профиль";
        final String FRIENDS = "Друзья";
        final String STOCKS = "Акции";
        final String SUBSCRIPTIONS = "Подписки";
        final String SETTINGS = "Настройки";
        final String EXIT = "Выход";

        try {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Добавление ФИО пользователя в navigationView
            // TextView userFio = (TextView) activity.findViewById(R.id.person_fio);
            //userFio.setText(WorkWithResources.getCurrentUserFIO());

            // Create the AccountHeader
            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            new ProfileDrawerItem().withName(WorkWithResources.getCurrentUserFIO()).withEmail("mikhail.mikhalev37@gmail.com").withIcon(activity.getResources().getDrawable(R.drawable.default_photo))
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            return false;
                        }
                    })
                    .build();

            Drawer drawer = new DrawerBuilder()
                    .withActivity(activity)
                    .withToolbar(toolbar)
                    .withAccountHeader(headerResult)
                    .withSelectedItem(-1)
                    .withActionBarDrawerToggle(true)
                    .withHeader(R.layout.drawer_header)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName(PROFILE).withIcon(FontAwesome.Icon.faw_user).withIdentifier(R.string.drawer_item_profile),
                            new PrimaryDrawerItem().withName(R.string.drawer_item_friends).withIcon(FontAwesome.Icon.faw_users).withBadge("3").withIdentifier(R.string.drawer_item_friends),
                            new PrimaryDrawerItem().withName(R.string.drawer_item_stocks).withIcon(FontAwesome.Icon.faw_rss).withBadge("16").withIdentifier(R.string.drawer_item_stocks),
                            // new PrimaryDrawerItem().withName(R.string.drawer_item_subscriptions).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(3).withName(R.string.drawer_item_subscriptions),
                            new PrimaryDrawerItem().withName(R.string.drawer_item_subscriptions).withIcon(GoogleMaterial.Icon.gmd_loyalty).withBadge("6").withIdentifier(R.string.drawer_item_subscriptions),

                            new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(R.string.drawer_item_settings),
                            new PrimaryDrawerItem().withName(R.string.drawer_item_exit).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(R.string.drawer_item_exit)

                    )
                    .withOnDrawerItemClickListener(
                            new Drawer.OnDrawerItemClickListener() {
                                @Override
                                public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                                    // Toast.makeText(activity.getApplicationContext(), i+"", Toast.LENGTH_SHORT).show();
                                    if (iDrawerItem instanceof Nameable) {
                                        switch (iDrawerItem.getIdentifier()) {
                                            case R.string.drawer_item_profile: {
                                                activity.startActivity(new Intent(activity.getBaseContext(), ProfileActivity.class));
                                                activity.finish();
                                                break;
                                            }
                                            case R.string.drawer_item_friends: {
                                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                                activity.startActivity(new Intent(activity.getBaseContext(), FriendsActivity.class));
                                                activity.finish();
                                                break;
                                            }
                                            case R.string.drawer_item_stocks: {
                                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                                activity.startActivity(new Intent(activity.getBaseContext(), StocksActivity.class));
                                                activity.finish();
                                                break;
                                            }
                                            case R.string.drawer_item_subscriptions: {
                                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                                activity.startActivity(new Intent(activity.getBaseContext(), SubscribesActivity.class));
                                                activity.finish();
                                                break;
                                            }
                                            case R.string.drawer_item_settings: {
                                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                                activity.startActivity(new Intent(activity.getBaseContext(), GeneratedCodeActivity.class));
                                                activity.finish();
                                                break;
                                            }
                                            case R.string.drawer_item_exit: {
                                                WorkWithResources.deleteToken(activity);
                                                activity.finish();
                                                activity.startActivity(new Intent(activity.getBaseContext(), LoginActivity.class));
                                            }
                                        }
                                    }
                                    return true;
                                }
                            }
                            // return true;
                            //  }
                            // return false;
                    )
                    .build();

            /*drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                    if (iDrawerItem instanceof Nameable) {
                        switch (((Nameable) iDrawerItem).getName().toString()) {
                            case PROFILE: {
                                activity.startActivity(new Intent(activity.getBaseContext(), ProfileActivity.class));
                                break;
                            }
                            case STOCKS: {
                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                activity.startActivity(new Intent(activity.getBaseContext(), StocksActivity.class));
                                break;
                            }
                            case SUBSCRIPTIONS: {
                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                activity.startActivity(new Intent(activity.getBaseContext(), SubscribesActivity.class));
                                break;
                            }
                            case SETTINGS: {
                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case FRIENDS: {
                                //Toast.makeText(activity.getApplicationContext(), ((Nameable) iDrawerItem).getName(), Toast.LENGTH_SHORT).show();
                                activity.startActivity(new Intent(activity.getBaseContext(), FriendsActivity.class));
                                break;
                            }
                            case EXIT: {
                                WorkWithResources.deleteToken(activity);
                                activity.startActivity(new Intent(activity.getBaseContext(), LoginActivity.class));
                            }
                        }
                        return true;
                    }
                    return false;
                }

                // Добавление ФИО пользователя в navigationView
                // TextView userFio = (TextView) activity.findViewById(R.id.person_fio);
                //userFio.setText(WorkWithResources.getCurrentUserFIO());

            });*/
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Добавление вкладок с акциями
    public static void addStockTabs(final AppCompatActivity activity){
        ViewPager pager;
        StocksViewPagerAdapter adapter;
        SlidingTabLayout tabs;
        // Заголовки вкладок
        CharSequence Titles[]={"Лента","Компании","Категории"};
        // Количество вкладок
        int Numboftabs = 3;
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new StocksViewPagerAdapter(activity.getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) activity.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) activity.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                //return activity.getResources().getColor(R.color.tabsScrollColor);
                return activity.getResources().getColor(R.color.default_app_white);
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
