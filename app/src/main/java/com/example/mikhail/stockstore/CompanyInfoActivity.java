package com.example.mikhail.stockstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Adapters.StockCardAdapter;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CompanyInfoActivity extends AppCompatActivity {

    private List<Stock> stocks = new ArrayList<>();
    StockCardAdapter adapter;
    RecyclerView rv;
    int countOfLoadingStocks = 5;

    // Данная компания
    Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);


        CommonFunctions.addNavigationView(this,CommonFunctions.setToolbar(this, R.id.toolbar));
        rv = (RecyclerView)findViewById(R.id.cards);
        initRecyclerView();

       setInterfaceElements();

        try {
            AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
            // Тест поиска по компании
            //request.setParameters("companyID=" + company.id);
           // request.execute(APIConstants.USER_GET_STOCKS_BY_COMPANY);

            // Тест поиска по нескольким параметрам
            String word = "лучш";
            word = URLEncoder.encode(word, "UTF-8");
            request.setParameters("companyID=" + company.id + "&searchword="+word);
            request.execute(APIConstants.USER_GET_STOCKS_BY_FILTER);

            //Тест поиска по строке
           /* String word = "крос";
            word = URLEncoder.encode(word, "UTF-8");
            //word = Charset.forName("UTF-8").encode(word).toString();
            request.setParameters("searchword=" + word);
            request.execute(APIConstants.USER_GET_STOCKS_BY_WORDPATH);*/
        }catch(Exception e){
            e.printStackTrace();
        }

      //  initializeTestData();
    }

    private void setInterfaceElements(){
        try {
            Intent intent = getIntent();
            company = intent.getParcelableExtra("company");
            ImageView companyPhoto = (ImageView) findViewById(R.id.companyPhoto);
            TextView companyName = (TextView) findViewById(R.id.companyName);

            companyName.setText(company.name);
            ImageLoader.getInstance().displayImage(company.photo, companyPhoto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onUserGetStocksByCompany(JSONObject response) {
            try {
                // Обновляем список акций
                stocks.clear();

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingStocks ? countOfLoadingStocks : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject stock = new JSONObject(data.get(i).toString());
                    stocks.add(new Stock(stock));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // Инициализация RecyclerView карточек
    public void initRecyclerView(){
        Context context = this;
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(llm);

       // rv.setHasFixedSize(true);
        adapter = new StockCardAdapter(stocks, false);
        rv.setAdapter(adapter);
    }

    private void initializeTestData() {
        String testPhoto =  "http://sportmax-abakan.ru/upload/medialibrary/9bf/eskiz.png";
        String testCompanyPhoto = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        stocks.add(new Stock("1", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
        stocks.add(new Stock("2", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
        stocks.add(new Stock("3", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
        stocks.add(new Stock("4", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_company_info, menu);
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
