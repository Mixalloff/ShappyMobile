package com.example.mikhail.stockstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Entities.Stock;

public class StockInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

        CommonFunctions.addNavigationView(this, CommonFunctions.setToolbar(this, R.id.toolbar));
        setInterfaceElements();
    }

    // Задает значения элементам интерфейса (изображения, текст..)
    private void setInterfaceElements(){
        try {
            Intent intent = getIntent();
            Stock stock = intent.getParcelableExtra("stock");
            ImageView stockPhoto = (ImageView) findViewById(R.id.stockPhoto);
            TextView stockName = (TextView) findViewById(R.id.stockName);
            TextView stockDescription = (TextView) findViewById(R.id.stockDescription);

            stockName.setText(stock.name);
            stockDescription.setText(stock.description);
            stockPhoto.setImageBitmap(stock.photo);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_info, menu);
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
