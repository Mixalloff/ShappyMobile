package com.example.mikhail.stockstore;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mikhail.stockstore.Classes.CommonFunctions;

public class StocksActivity extends ActionBarActivity  implements SearchView.OnQueryTextListener {

    MenuItem searchMenuItem;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);
        CommonFunctions.addNavigationView(this,CommonFunctions.setToolbar(this, R.id.toolbar));
        CommonFunctions.addStockTabs(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_stocks, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
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
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
        return false;
    }
}
