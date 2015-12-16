package com.example.mikhail.stockstore;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private List<Stock> stocks = new ArrayList<>();
    StockCardAdapter adapter;
    RecyclerView rv;
    int countOfLoadingStocks = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeTestData();
        CommonFunctions.addNavigationView(this);
        setLogos();
        rv = (RecyclerView)findViewById(R.id.cards);

        initRecyclerView();


    }

    public void setLogos(){
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont-4.3.0.ttf");

        TextView email = (TextView) findViewById(R.id.email_logo);
        email.setTypeface(font);

        TextView phone = (TextView) findViewById(R.id.phone_logo);
        phone.setTypeface(font);
    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(){
        Context context = this;
        LinearLayoutManager llm = new LinearLayoutManager(context);

        rv.setLayoutManager(llm);
        adapter = new StockCardAdapter(stocks);
        rv.setAdapter(adapter);
    }

    private void initializeTestData() {
        String testPhoto =  "http://sportmax-abakan.ru/upload/medialibrary/9bf/eskiz.png";
        String testCompanyPhoto = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        stocks.add(new Stock("1", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("NIKE", testCompanyPhoto)));
        stocks.add(new Stock("2", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("NIKE", testCompanyPhoto)));
        stocks.add(new Stock("3", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("NIKE", testCompanyPhoto)));
        stocks.add(new Stock("4", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("NIKE", testCompanyPhoto)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
