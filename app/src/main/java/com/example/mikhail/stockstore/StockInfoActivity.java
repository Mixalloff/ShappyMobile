package com.example.mikhail.stockstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Entities.Stock;
import com.nostra13.universalimageloader.core.ImageLoader;

public class StockInfoActivity extends AppCompatActivity {

    Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

        //CommonFunctions.addNavigationView(this, CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE));
        CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE);
        setInterfaceElements();

        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("id=" + stock.id);
        request.execute(APIConstants.USER_GET_STOCKS_INFO);
    }

    ServerResponseHandler handler = new ServerResponseHandler(){
        
    } ;

    // Задает значения элементам интерфейса (изображения, текст..)
    private void setInterfaceElements(){
        try {
            Intent intent = getIntent();
            stock = intent.getParcelableExtra("stock");
            ImageView stockPhoto = (ImageView) findViewById(R.id.stockPhoto);
            TextView stockName = (TextView) findViewById(R.id.stockName);
            TextView stockDescription = (TextView) findViewById(R.id.stockDescription);

            FloatingActionButton getCodeBtn = (FloatingActionButton)findViewById(R.id.get_code_btn);
            if(!stock.code.equals("")) {
               // getCodeBtn.setVisibility(View.VISIBLE);


                getCodeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StockInfoActivity.this, GeneratedCodeActivity.class);
                        intent.putExtra("code", stock.code);
                        startActivity(intent);
                    }
                });
            }
            else{
                ((CoordinatorLayout)findViewById(R.id.coordinator)).removeView(getCodeBtn);
            }

            stockName.setText(stock.name);
            stockDescription.setText(stock.description);
            ImageLoader.getInstance().displayImage(stock.photo, stockPhoto);
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
