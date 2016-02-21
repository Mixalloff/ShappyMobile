package com.example.mikhail.stockstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mikhail.stockstore.Adapters.MiniCardsAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.AsyncClasses.OnTaskCompleted;
import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Adapters.StockCardAdapter;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Constants.ElementGroupSpecies;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CompanyInfoActivity extends AppCompatActivity {

    private List<JSONObject> stocks = new ArrayList<>();
    MiniCardsAdapter adapter;
    RecyclerView rv;

    ImageView companyPhoto;
    TextView companyName;
    TextView companyDescription ;

    FloatingActionButton subscribedBtn;

    final Activity self = this;

    int countOfLoadingStocks = 5;

    // Данная компания
    Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        //CommonFunctions.addNavigationView(this,CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE));
        CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE);

        rv = (RecyclerView)findViewById(R.id.cards);
        initRecyclerView();

       setInterfaceElements();

        try {
            AsyncRequestToServer request = new AsyncRequestToServer(this,  new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(JSONObject result) {
                    handler.onUserGetStocksByCompany(result);
                }
            });
            // Тест поиска по компании
            request.setParameters("companyID=" + company.id);
            request.execute(APIConstants.USER_GET_STOCKS_BY_COMPANY);

        }catch(Exception e){
            e.printStackTrace();
        }

      //  initializeTestData();
    }

    private void setInterfaceElements(){
        try {
            Intent intent = getIntent();
            company = intent.getParcelableExtra("company");
            companyPhoto = (ImageView) findViewById(R.id.companyPhoto);
            companyName = (TextView) findViewById(R.id.companyName);
            companyDescription = (TextView) findViewById(R.id.companyDescription);
            subscribedBtn = (FloatingActionButton) findViewById(R.id.subscribe_btn);

            companyName.setText(company.name);
            ImageLoader.getInstance().displayImage(company.photo, companyPhoto);

            if(company.isAdded) {
                setSubscribedView();
                // subscribedBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3eba3b")));
            }
            else {
                setUnsubscribedView();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setSubscribedView(){

        subscribedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncRequestToServer request = new AsyncRequestToServer(self, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        setUnsubscribedView();
                    }
                });
                request.setParameters(APIRequestConstructor.userAddStockParameters(company.id));
                request.execute(APIConstants.USER_UNSUBSCRIBE_COMPANY);
            }
        });

        subscribedBtn.setImageDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_done).color(Color.WHITE));
        company.isAdded = true;
    }

    private void setUnsubscribedView(){
        subscribedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncRequestToServer request = new AsyncRequestToServer(self, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        setSubscribedView();
                    }
                });
                request.setParameters(APIRequestConstructor.userAddStockParameters(company.id));
                request.execute(APIConstants.USER_SUBSCRIBE_COMPANY);
            }
        });
        subscribedBtn.setImageDrawable(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_plus).color(Color.WHITE));
        company.isAdded = false;
    }


    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onUserGetStocksByCompany(JSONObject response) {
            try {
                 stocks.clear();

                JSONArray data = new JSONArray(response.get("data").toString());
               // int count = data.length() > countOfLoadingItems ? countOfLoadingItems : data.length();
                for (int i = 0; i < data.length(); i++){
                    JSONObject stock = new JSONObject(data.get(i).toString());

                    String id = stock.getString("id");
                    String name = stock.getString("name");
                    String photo = GlobalVariables.server + stock.getString("thumb");
                    String type = CommonConstants.SEARCH_BLOCK_TYPE_STOCKS;

                    stocks.add(miniCardJSON(id, name, photo, type));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUserSubscribeCompany(JSONObject response) {
            //setSubscribedView();
        }

        @Override
        public void onUserUnsubscribeCompany(JSONObject response) {
           // setUnsubscribedView();
        }
    };

    private JSONObject miniCardJSON(String id, String name, String photo, String type){
        JSONObject miniCard = new JSONObject();
        try{
            miniCard.put("id", id);
            miniCard.put("name", name);
            miniCard.put("photo", photo);
            miniCard.put("type", type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return miniCard;
    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(){
        Context context = this;
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(llm);

       // rv.setHasFixedSize(true);
        adapter = new MiniCardsAdapter(stocks, LinearLayoutManager.HORIZONTAL);
        rv.setAdapter(adapter);
    }

    /*private void initializeTestData() {
        String testPhoto =  "http://sportmax-abakan.ru/upload/medialibrary/9bf/eskiz.png";
        String testCompanyPhoto = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        stocks.add(new Stock("1", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
        stocks.add(new Stock("2", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
        stocks.add(new Stock("3", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
        stocks.add(new Stock("4", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("1","NIKE", testCompanyPhoto), false));
    }*/

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
