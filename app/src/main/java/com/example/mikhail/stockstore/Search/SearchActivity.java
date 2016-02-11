package com.example.mikhail.stockstore.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.mikhail.stockstore.Adapters.HorizontalListAdapter;
import com.example.mikhail.stockstore.Adapters.MiniCardsAdapter;
import com.example.mikhail.stockstore.Adapters.StockCardAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Constants.ElementGroupSpecies;
import com.example.mikhail.stockstore.Entities.Category;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Person;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

   // ArrayList<Person> friends = new ArrayList<>();
    ArrayList<Stock> stocks = new ArrayList<>();
    ArrayList<Company> companies = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();
    List<JSONObject> elements = new ArrayList<>();


    MiniCardsAdapter adapter;
    StockCardAdapter stocksAdapter;

    RecyclerView rv;
    int countOfLoadingElems = 10;
    RelativeLayout searchLayout;
    Toolbar toolbar;

    MenuItem searchMenuItem;
    SearchView searchView;

    ElementGroupSpecies typeSpecies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_search);
            final Activity self = this;
            toolbar = CommonFunctions.setToolbar(this, R.id.include, CommonConstants.TOOLBAR_NAV_BACK);

            //   friends = new ArrayList<>();
            //  View v = inflater.inflate(R.layout.all_friends_tab, container, false);
            searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
            rv = (RecyclerView) findViewById(R.id.search_list);
            // initListView();

            // Получение типа объекта для поиска
            Intent intent = getIntent();
            typeSpecies = (ElementGroupSpecies) intent.getSerializableExtra("type");
        //elements = testItems();
            initLayoutByType(typeSpecies);
            initSwipe(searchLayout);
        }catch(Exception e){
            e.printStackTrace();
        }
        /*AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);*/
    }



    public void RequestNeededSpecies(ElementGroupSpecies type, String query){

        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        switch(type){
            case FRIENDS:
            {
                request.setParameters("FIO=" + query);
                request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
                break;
            }
            case STOCKS:
            {
                request.setParameters("searchword=" + query);
                request.execute(APIConstants.USER_GET_STOCKS_BY_WORDPATH);
                break;
            }
            case COMPANIES:
            {
                break;
            }
            case CATEGORIES:
            {
                break;
            }
            default:{
                break;
            }
        }
    }

    public void initLayoutByType(ElementGroupSpecies type) {
        switch (type) {
            case FRIENDS: {
                initListView();
                break;
            }
            case STOCKS: {
                initRecyclerView();
                break;
            }
            case COMPANIES: {
                break;
            }
            case CATEGORIES: {
                break;
            }
            default: {
                break;
            }
        }
    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(){
        Context context = this;
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        stocksAdapter = new StockCardAdapter(stocks);
        rv.setAdapter(stocksAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    // Инициализация ListView карточек для друзей
    public void initListView(){
        Context context = this;
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);
        adapter = new MiniCardsAdapter(elements, LinearLayoutManager.VERTICAL);
        rv.setAdapter(adapter);
    }

    // Инициализация SwipeLayout
    public void initSwipe(View v){
        // Обновление при скролле вниз

            final SwipeRefreshLayout swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        final Activity self = this;
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //  request = new AsyncRequestToServer(getActivity(), handler);
                    try {
                        AsyncRequestToServer request = new AsyncRequestToServer(self, handler);
                        request.setSwipeRefresh(swipe);
                        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
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

    private ServerResponseHandler handler = new ServerResponseHandler() {

        @Override
        public void onUserGetFriendsFilter(JSONObject response) {
            try {
                elements.clear();
                // adapter.notifyItemRemoved(0);

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingElems ? countOfLoadingElems : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject item = new JSONObject(data.get(i).toString());
                    String id = item.has("id") ? item.getString("id") : "";
                    String name = item.has("name") ? item.getString("name") : "";
                    String photo = item.has("photo") ? GlobalVariables.server + item.getString("photo") : "";
                    String type = CommonConstants.SEARCH_BLOCK_TYPE_PERSONS;

                    elements.add(miniCardJSON(id, name, photo, type));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUserGetStocksByWord(JSONObject response) {
            try {
                stocks.clear();
                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingElems ? countOfLoadingElems : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject item = new JSONObject(data.get(i).toString());
                    stocks.add(new Stock(item));
                }
                stocksAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        try {
            searchView = (SearchView) searchMenuItem.getActionView();
            searchView.setOnQueryTextListener(this);
        }
        catch(Exception e){
            e.printStackTrace();
        }

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

    @Override
    public boolean onQueryTextSubmit(String query) {
       /* AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("FIO=" + query);
        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);*/
        RequestNeededSpecies(this.typeSpecies, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        /*AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("FIO=" + newText);
        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);*/
        return false;
    }
}
