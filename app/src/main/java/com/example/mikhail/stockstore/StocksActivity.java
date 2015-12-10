package com.example.mikhail.stockstore;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Classes.ViewPagerAdapter;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Classes.WorkWithServer;
import com.example.mikhail.stockstore.Modules.SlidingTabLayout;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StocksActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Инициализация фото по умолчанию
        GlobalVariables.setDefaultPhoto(R.drawable.default_photo, getResources());
        // Тестовые данные
       // initializeTestData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        // Подгружаем токен, если есть
        String token = WorkWithServer.getToken(this);

        CommonFunctions.addNavigationView(this);
        CommonFunctions.addTabs(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stocks, menu);
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
}
