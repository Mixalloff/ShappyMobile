package com.example.mikhail.stockstore.Search;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.mikhail.stockstore.Adapters.MiniCardsAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.MiniCardFragment;
import com.example.mikhail.stockstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GlobalSearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    int countOfLoadingItems = 10;
    Toolbar toolbar;

    MenuItem searchMenuItem;
    SearchView searchView;
   // private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_search);

        toolbar = CommonFunctions.setToolbar(this, R.id.include, CommonConstants.TOOLBAR_NAV_BACK);
     //   initTest();

    }

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

    ServerResponseHandler handler = new ServerResponseHandler(){
        @Override
        public void onUserGetStocksByFilter(JSONObject response) {
            // Удаление существующего блока
            Fragment stocksFragment = getSupportFragmentManager().findFragmentByTag(CommonConstants.SEARCH_BLOCK_TYPE_STOCKS);
            if(stocksFragment != null)
                getSupportFragmentManager().beginTransaction().remove(stocksFragment).commit();

            List<JSONObject> stocks = new ArrayList<>();
            try {
               // stocks.clear();

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingItems ? countOfLoadingItems : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject stock = new JSONObject(data.get(i).toString());

                    String id = stock.getString("id");
                    String name = stock.getString("name");
                    String photo = GlobalVariables.server + stock.getString("thumb");
                    String type = CommonConstants.SEARCH_BLOCK_TYPE_STOCKS;

                    stocks.add(miniCardJSON(id, name, photo, type));
                }
                AddSearchBlock(stocks, "Stocks", CommonConstants.SEARCH_BLOCK_TYPE_STOCKS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // Запрос акций по искомому слову
    public void requestStocks(String searchWord){
        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("searchword=" + searchWord);
        request.execute(APIConstants.USER_GET_STOCKS_BY_WORDPATH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_global_search, menu);

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

    // Добавляет блок результатов поиска
    public void AddSearchBlock(List<JSONObject> blockItems, String blockName, String tag){
        MiniCardFragment block = new MiniCardFragment();
        block.setElems(blockItems);
        block.setBlockName(blockName);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.container_search, block, tag)
                .commit();
    }

    // Удаляет все блоки
    public void DeleteAllBlocks(){
        Fragment stocksFragment = getSupportFragmentManager().findFragmentByTag(CommonConstants.SEARCH_BLOCK_TYPE_STOCKS);
        Fragment companiesFragment = getSupportFragmentManager().findFragmentByTag(CommonConstants.SEARCH_BLOCK_TYPE_COMPANIES);
        Fragment categoriesFragment = getSupportFragmentManager().findFragmentByTag(CommonConstants.SEARCH_BLOCK_TYPE_CATEGORIES);

        if(stocksFragment != null)
            getSupportFragmentManager().beginTransaction().remove(stocksFragment).commit();
        if(companiesFragment != null)
            getSupportFragmentManager().beginTransaction().remove(companiesFragment).commit();
        if(categoriesFragment != null)
            getSupportFragmentManager().beginTransaction().remove(categoriesFragment).commit();
    }

    public void initTest(){
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
        AddSearchBlock(list, "Companies", CommonConstants.SEARCH_BLOCK_TYPE_COMPANIES);
        AddSearchBlock(list, "Stocks", CommonConstants.SEARCH_BLOCK_TYPE_STOCKS);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       // DeleteAllBlocks();
        if (query != null){
            searchView.clearFocus();
            requestStocks(query);
        }

        Snackbar.make(this.findViewById(R.id.search_layout), query, Snackbar.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
