package com.example.mikhail.stockstore.Tabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.AsyncClasses.OnTaskCompleted;
import com.example.mikhail.stockstore.Classes.IDifferentMode;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Adapters.StockCardAdapter;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.Search.GlobalSearchActivity;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StocksActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 09.12.15.
 */
public class allStocksTab extends Fragment implements IDifferentMode{
    private StockCardAdapter adapter;
    private List<Stock> stocks = new ArrayList<>();
    private RecyclerView rv;
    private int countOfLoadingStocks = 15;
    private String mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Установить режим
        this.setMode(((IDifferentMode)getActivity()).getMode());

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

        rv = (RecyclerView) v.findViewById(R.id.cards);
        initRecyclerView(container);
        initSwipe(v);

        FloatingActionButton flBtn = (FloatingActionButton) v.findViewById(R.id.floating_btn);
        flBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Pushed button", Snackbar.LENGTH_SHORT).show();
               /* Intent intent = new Intent(getActivity(), GlobalSearchActivity.class);
                getActivity().startActivityForResult(intent, 123);*/
                startActivity(new Intent(v.getContext(), GlobalSearchActivity.class));
            }
        });
        //RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        //rv.setItemAnimator(itemAnimator);

        //initializeTestData(v);

        try{
            Intent intent = getActivity().getIntent();
            JSONObject stocksJson = new JSONObject(intent.getStringExtra("stocks"));
            handler.onUserGetAllStocks(stocksJson);
        }catch(Exception e){
            e.printStackTrace();

            AsyncRequestToServer request = new AsyncRequestToServer(getActivity());
            //request.execute(APIConstants.GET_ALL_STOCKS);
            requestStocks(request);
        }

        return v;

    }

    public void requestStocks(AsyncRequestToServer request){
        switch(getMode()){
            case "All": {
                request.setCallback( new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        handler.onUserGetAllStocks(result);
                    }
                });
                request.execute(APIConstants.GET_ALL_STOCKS);
                break;
            }
            case "Subscriptions": {
                request.setCallback( new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        handler.onUserGetSubscriptionsStocks(result);
                    }
                });
                request.execute(APIConstants.USER_GET_SUBSCRIPTIONS_STOCKS);
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        Stock changedStock = data.getParcelableExtra("changed_stock");
        if(changedStock != null){
            //Snackbar.make(this.getView(), changedStock.name, Snackbar.LENGTH_SHORT).show();
            for (Stock stock :
                    stocks) {
                if(stock.id.equals(changedStock.id)) {
                    stocks.set(stocks.indexOf(stock), changedStock);
                    adapter.notifyDataSetChanged();
                }
            }

        }
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
                AsyncRequestToServer request = new AsyncRequestToServer(getActivity());
                request.setSwipeRefresh(swipe);
                requestStocks(request);
                //request.execute(APIConstants.GET_ALL_STOCKS);
            }
        });

    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(ViewGroup container){
        Context context = this.getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);
        adapter = new StockCardAdapter(stocks, getActivity());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    private void initializeTestData(View v) {
     //   String testPhoto =  "http://sportmax-abakan.ru/upload/medialibrary/9bf/eskiz.png";
        //String testCompanyPhoto = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
      //  Stock testStock = new Stock("1", "Наушники Nike БЕСПЛАТНО", "С 1 сентября 2015 года при единовременной покупке товаров-участников акции в магазине \"СпортМакс\" по адресу г.Абакан ул.Стофато 5д, на сумму 1500 (одна тысяча пятьсот) рублей, Покупатель БЕСПЛАТНО получает наушники Nike.", testPhoto, new Company("NIKE", testCompanyPhoto), true);
       // stocks.add(new Stock("1", "Наушники Nike БЕСПЛАТНО", v.getResources().getString(R.string.big_text), testPhoto, new Company("1","NIKE", testCompanyPhoto), true));
  // String s = v.getResources().getString(R.string.big_text);
    }

    private void refreshStocksFromResponse(JSONObject response){
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

    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onUserGetAllStocks(JSONObject response) {
            refreshStocksFromResponse(response);
        }

        @Override
        public void onUserGetSubscriptionsStocks(JSONObject response){
            refreshStocksFromResponse(response);
        }
    };

    @Override
    public String getMode() {
        return this.mode;
    }

    @Override
    public void setMode(String mode) {
        this.mode = ((IDifferentMode)getActivity()).getMode();
    }
}


