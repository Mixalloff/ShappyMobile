package com.example.mikhail.stockstore;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mikhail.stockstore.Classes.ViewPagerAdapter;
import com.example.mikhail.stockstore.Classes.WorkWithServer;
import com.example.mikhail.stockstore.Modules.*;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class SubscribesActivity extends AppCompatActivity {

   // android.support.v7.widget.Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Лента","Компании","Категории"};
    int Numboftabs =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribes);

        addNavigationView();
        addTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscribes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTabs(){
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }



    // Добавление пользовательского Navigation View и тулбара
    private void addNavigationView(){
        // Handle Toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(0xFF72BB53);//"#72bb53"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawer drawer = new Drawer();
        drawer
                .withActivity(this)
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
                            Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.string.drawer_item_exit: {
                            WorkWithServer.deleteToken();
                            Intent intent = new Intent(SubscribesActivity.this, StartActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}
