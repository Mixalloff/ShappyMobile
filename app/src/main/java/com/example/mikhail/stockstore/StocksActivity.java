package com.example.mikhail.stockstore;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Classes.WorkWithServer;
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

    private ResponseInterface handler = new ResponseInterface() {
        @Override
        public void onInternalServerError(JSONObject response) {

        }

        @Override
        public void onUnknownRequestUri(JSONObject response) {

        }

        @Override
        public void onError(JSONObject response) {
            Toast.makeText(getApplicationContext(), response.toString(),
                             Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StocksActivity.this, StartActivity.class);
            startActivity(intent);
        }

        @Override
        public void onRegister(JSONObject response) {

        }

        @Override
        public void onGetToken(JSONObject response) {

        }

        @Override
        public void onUserGetAllStocks(JSONObject response) {
            /*Toast.makeText(getApplicationContext(), response.toString(),
                             Toast.LENGTH_SHORT).show();*/
            // Обновляем список акций
            stocks.clear();
            try {
                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingStocks ? countOfLoadingStocks : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject stock = new JSONObject(data.get(i).toString());
                    stocks.add(new Stock(stock));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    StockCardAdapter adapter;

    // Максимальное количество подгружаемых акций
    int countOfLoadingStocks = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Инициализация фото по умолчанию
        GlobalVariables.setDefaultPhoto(R.drawable.default_photo, getResources());
        // Тестовые данные
        initializeTestData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        // Подгружаем токен, если есть
        String token = WorkWithServer.getToken(this);

        //WorkWithServer.deleteToken();

        //JSONObject data = new JSONObject(WorkWithServer.executeGet("stocks/all?token=" + token));
        try {
            ServerResponseHandler.CheckResponse(APIRequestConstructor.getAllStocks(this), handler);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        CommonFunctions.addNavigationView(this);
      //  addTabs();

        loadRecyclerView();

    }

    public void loadRecyclerView(){
        RecyclerView rv = (RecyclerView)findViewById(R.id.cards);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        adapter = new StockCardAdapter(stocks);
        rv.setAdapter(adapter);
    }

    private List<Stock> stocks;
    private void initializeTestData() {
        stocks = new ArrayList<>();
        String defaultPhoto =  "http://sportmax-abakan.ru/upload/medialibrary/9bf/eskiz.png";
        String defaultCompanyPhoto = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        stocks.add(new Stock("Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", defaultPhoto, new Company("NIKE", defaultCompanyPhoto)));
        //stocks.add(new Stock("Играй как Пеле!", "Cобери всю коллекцию от легендарного Пеле", R.drawable.magnit_stock1, new Company("МАГНИТ", R.drawable.magnit_logo)));
        //stocks.add(new Stock("Всегда низкие цены!", "Скидки к чаю", R.drawable.magnit_stock2, new Company("МАГНИТ", R.drawable.magnit_logo)));
        //stocks.add(new Stock("Праздничные будни", "В понедельник в нашем магазине скидки на чай", R.drawable.magnit_stock3, new Company("МАГНИТ", R.drawable.magnit_logo)));
    }

    private void addTabs(){
        try{

            TabLayout tabLayout = new TabLayout(getApplicationContext());
            tabLayout.addTab(tabLayout.newTab().setText("Вкладка 1"));
            tabLayout.addTab(tabLayout.newTab().setText("Вкладка 2"));
        }catch(Exception e){
            e.printStackTrace();
        }

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
