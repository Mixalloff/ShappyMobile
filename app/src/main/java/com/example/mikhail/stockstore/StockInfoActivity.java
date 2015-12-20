package com.example.mikhail.stockstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.Entities.Stock;

public class StockInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

       // Intent intent = getIntent();
       // Stock stock = intent.getParcelableExtra("stock");
        Stock stock = null;
        Bundle b = getIntent().getExtras();
        if(b != null) {
            stock = b.getParcelable("stock");
        }

        /*Bitmap stockPhoto = (Bitmap)intent.getParcelableExtra("stockPhoto");
        String stockName = intent.getStringExtra("stockName");
        String stockDescription = intent.getStringExtra("stockDescription");*/

        ImageView stockPhoto = (ImageView) findViewById(R.id.stockPhoto);
        TextView stockName = (TextView) findViewById(R.id.stockName);
        TextView stockDescription = (TextView) findViewById(R.id.stockDescription);

       // stockPhoto.setImageBitmap(stock.photo);
        stockName.setText(stock.name);
        stockDescription.setText(stock.description);
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
