package com.example.mikhail.stockstore;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mikhail.stockstore.Adapters.FriendsViewPagerAdapter;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Constants.ElementGroupSpecies;
import com.example.mikhail.stockstore.Fragments.MiniCardContainerFragment;
import com.example.mikhail.stockstore.Modules.SlidingTabLayout;
import com.example.mikhail.stockstore.Tabs.allFriendsTab;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

public class FriendsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ViewPager pager;
    FriendsViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        CommonFunctions.addNavigationView(this, CommonFunctions.setToolbar(this, R.id.toolbar));
        AddFriendsTabs();


           // LinearLayout container = (LinearLayout) adapter.getItem(pager.getCurrentItem()).getView().findViewById(R.id.container_search);
            //replaceTabs(testItems(), "Акции", "1");

    }

    // Добавление вкладок на экран друзей
    public void AddFriendsTabs(){

        // Заголовки вкладок
        CharSequence Titles[]={"Друзья","Новости"};
        // Количество вкладок
        int Numboftabs = 2;
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new FriendsViewPagerAdapter(this.getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) this.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) this.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        final Activity self = this;
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                //return activity.getResources().getColor(R.color.tabsScrollColor);
                return self.getResources().getColor(R.color.default_app_white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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

    public List<JSONObject> testItems(){
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        try {
            obj1.put("name", "1");
            obj1.put("photo", "https://lh4.ggpht.com/PkLr6N7yct9esjBr82C3uP7-QExURQImTTpEFJlGYKCzzonEDEkAvrpKq5gD4SEXiw=w300");
            obj2.put("name", "2");
            obj2.put("photo", "https://lh4.ggpht.com/PkLr6N7yct9esjBr82C3uP7-QExURQImTTpEFJlGYKCzzonEDEkAvrpKq5gD4SEXiw=w300");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list.add(obj1);
        list.add(obj2);
        return list;
    }

    public void replaceTabs(List<JSONObject> blockItems, String blockName, String tag){
        // Очистить контейнер R.id.container_search

       /* Fragment stocksFragment = getSupportFragmentManager().findFragmentById(R.id.);
        getSupportFragmentManager().beginTransaction().remove(stocksFragment).commit();*/

       /* LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container_search);
        if(linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }*/


        // Добавить нужные элементы
        MiniCardContainerFragment block = new MiniCardContainerFragment();
        block.setOrientation(LinearLayoutManager.VERTICAL);
        block.setElems(blockItems);
        block.setBlockName(blockName);
       // allFriendsTab t = new allFriendsTab();t.getSupportFragmentManager()
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.container_search, block, tag)
                .commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
