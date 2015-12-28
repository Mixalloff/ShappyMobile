package com.example.mikhail.stockstore.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StartActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 09.12.15.
 */
public class allStocksTab extends Fragment {
    StockCardAdapter adapter;
    private List<Stock> stocks = new ArrayList<>();
    RecyclerView rv;
    int countOfLoadingStocks = 5;
    AsyncRequestToServer request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


// Тестовые карточки
       // initializeTestData();

       /* request = new AsyncRequestToServer(getActivity(), handler);
        request.setSwipeRefresh(this.getView());
        request.setSpinnerMessage("Загрузка акций");
        request.execute(APIConstants.GET_ALL_STOCKS);*/

        //Toast.makeText(getContext().getApplicationContext(), "Акции onCreate", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_stocks_tab, container, false);

        // Подгружаем токен, если есть
      // String token = WorkWithServer.getToken(getActivity());


        rv = (RecyclerView) v.findViewById(R.id.cards);
        initRecyclerView(container);
        initSwipe(v);

        //RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        //rv.setItemAnimator(itemAnimator);

        //initializeTestData(v);

        try{
            Intent intent = getActivity().getIntent();
            JSONObject stocksJson = new JSONObject(intent.getStringExtra("stocks"));
            handler.onUserGetAllStocks(stocksJson);
        }catch(Exception e){
            e.printStackTrace();
            request = new AsyncRequestToServer(getActivity(), handler);
            request.execute(APIConstants.GET_ALL_STOCKS);
        }

        return v;

    }

    // Инициализация SwipeLayout
    public void initSwipe(View v){
        // Обновление при скролле вниз
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        request = new AsyncRequestToServer(getActivity(), handler);
                        request.setSwipeRefresh(swipe);
                        request.setSpinnerMessage("Загрузка акций");
                        request.execute(APIConstants.GET_ALL_STOCKS);
                    }
                },0);*/

                request = new AsyncRequestToServer(getActivity(), handler);
                request.setSwipeRefresh(swipe);
                request.setSpinnerMessage("Загрузка акций");
                request.execute(APIConstants.GET_ALL_STOCKS);
            }
        });

    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(ViewGroup container){
        Context context = this.getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);
        adapter = new StockCardAdapter(stocks);
        rv.setAdapter(adapter);
    }

    private void initializeTestData(View v) {
        String testPhoto =  "http://sportmax-abakan.ru/upload/medialibrary/9bf/eskiz.png";
        String testCompanyPhoto = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        Stock testStock = new Stock("1", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("NIKE", testCompanyPhoto), true);
        stocks.add(new Stock("1", "Наушники Nike БЕСПЛАТНО", v.getResources().getString(R.string.big_text), testPhoto, new Company("NIKE", testCompanyPhoto), true));
  // String s = v.getResources().getString(R.string.big_text);
    }

    private ResponseInterface handler = new ResponseInterface() {
        @Override
        public void onInternalServerError(JSONObject response) {

        }

        @Override
        public void onUnknownRequestUri(JSONObject response) {

        }

        @Override
        public void onError(JSONObject response) {
            startActivity(new Intent(getContext(),StartActivity.class));
        }

        @Override
        public void onRegister(JSONObject response) {

        }

        @Override
        public void onGetToken(JSONObject response) {

        }

        @Override
        public void onUserGetAllStocks(JSONObject response) {
            try {
                // Обновляем список акций
                stocks.clear();
               // adapter.notifyItemRemoved(0);

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingStocks ? countOfLoadingStocks : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject stock = new JSONObject(data.get(i).toString());
                    stocks.add(new Stock(stock));
                   // adapter.notifyItemInserted(i);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUserGetAllCompanies(JSONObject response) {

        }

        @Override
        public void onUserAddStock(JSONObject response) {

        }

        @Override
        public void onUserGetFeed(JSONObject response) {

        }
    };

}


