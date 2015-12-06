package com.example.mikhail.stockstore;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mikhail.stockstore.Classes.Company;
import com.example.mikhail.stockstore.Classes.Stock;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Classes.WorkWithServer;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StocksActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);

        // Подгружаем токен, если есть
        String token = WorkWithServer.getToken(this);

        //WorkWithServer.deleteToken();
        try {
            JSONObject data = new JSONObject(WorkWithServer.executeGet("stocks/all?token=" + token));
            // Проверка типа ответа. Если не stock, значит перекидываем на страницу авторизации
            if (!data.get("type").toString().equals("stock")){
                Intent intent = new Intent(StocksActivity.this, StartActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Необходимо авторизоваться!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addToolbar();

        initializeTestData();
        RecyclerView rv = (RecyclerView)findViewById(R.id.cards);
       // rv.setHasFixedSize(true); // если не изменяется размер
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        StockCardAdapter adapter = new StockCardAdapter(stocks);
        rv.setAdapter(adapter);
    }


    private List<Stock> stocks;
    private void initializeTestData() {
        stocks = new ArrayList<>();
        stocks.add(new Stock("Играй как Пеле!", "Cобери всю коллекцию от легендарного Пеле", R.drawable.magnit_stock1, new Company("МАГНИТ", R.drawable.magnit_logo)));
        stocks.add(new Stock("Всегда низкие цены!", "Скидки к чаю", R.drawable.magnit_stock2, new Company("МАГНИТ", R.drawable.magnit_logo)));
        stocks.add(new Stock("Праздничные будни", "В понедельник в нашем магазине скидки на чай", R.drawable.magnit_stock3, new Company("МАГНИТ", R.drawable.magnit_logo)));
    }

    // Добавление пользовательского тулбара
    private void addToolbar(){
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(0xFF72BB53);//"#72bb53"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_user),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_friends).withIcon(FontAwesome.Icon.faw_users).withBadge("3").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_stocks).withIcon(FontAwesome.Icon.faw_rss).withBadge("16").withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_subscriptions).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(3),

                        //new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SectionDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog)

                        /*new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)*/
                )
                .build();
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
