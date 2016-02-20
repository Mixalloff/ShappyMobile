package com.example.mikhail.stockstore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Entities.Stock;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class StockInfoActivity extends AppCompatActivity {

    Stock stock;
    Intent intent;
    ImageView stockPhoto;
    TextView stockName;
    TextView stockDescription ;

    FloatingActionButton subscribedBtn;
    FloatingActionButton getCodeBtn;

    final Activity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

        //CommonFunctions.addNavigationView(this, CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE));
        CommonFunctions.setToolbar(this, R.id.toolbar, CommonConstants.TOOLBAR_NAV_CLOSE, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setInterfaceElements();

        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("id=" + stock.id);
        request.execute(APIConstants.USER_GET_STOCKS_INFO);
    }

    ServerResponseHandler handler = new ServerResponseHandler(){
        @Override
        public void onUserSubscribeStock(JSONObject response) {
            try {
                stock.code = response.get("data").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSubscribedView();
        }

        @Override
        public void onUserUnsubscribeStock(JSONObject response) {
            stock.code = "";
            setUnsubscribedView();
        }
    } ;

    // Задает значения элементам интерфейса (изображения, текст..)
    private void setInterfaceElements(){
        try {
            intent = getIntent();
            stock = intent.getParcelableExtra("stock");
            stockPhoto = (ImageView) findViewById(R.id.stockPhoto);
            stockName = (TextView) findViewById(R.id.stockName);
            stockDescription = (TextView) findViewById(R.id.stockDescription);

            subscribedBtn = (FloatingActionButton) findViewById(R.id.subscribe_btn);
            getCodeBtn = (FloatingActionButton)findViewById(R.id.get_code_btn);

            if(stock.isAdded) {
               setSubscribedView();
               // subscribedBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3eba3b")));
            }
            else {
                setUnsubscribedView();
            }

            stockName.setText(stock.name);
            stockDescription.setText(stock.description);
            ImageLoader.getInstance().displayImage(stock.photo, stockPhoto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setSubscribedView(){
        if (findViewById(R.id.get_code_btn) == null) {
              ((CoordinatorLayout)findViewById(R.id.coordinator)).addView(getCodeBtn);
        }
        getCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockInfoActivity.this, GeneratedCodeActivity.class);
                intent.putExtra("code", stock.code);
                startActivity(intent);
            }
        });

        subscribedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncRequestToServer request = new AsyncRequestToServer(self, handler);
                request.setParameters(APIRequestConstructor.userAddStockParameters(stock.id));
                request.execute(APIConstants.USER_UNSUBSCRIBE_STOCK);
            }
        });

        subscribedBtn.setImageDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_done).color(Color.WHITE));
        stock.isAdded = true;
    }

    public void setUnsubscribedView(){
        ((CoordinatorLayout)findViewById(R.id.coordinator)).removeView(getCodeBtn);
       // getCodeBtn.setVisibility(View.GONE);
        subscribedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncRequestToServer request = new AsyncRequestToServer(self, handler);
                request.setParameters(APIRequestConstructor.userAddStockParameters(stock.id));
                request.execute(APIConstants.USER_SUBSCRIBE_STOCK);
            }
        });
        subscribedBtn.setImageDrawable(new IconicsDrawable(this).icon(FontAwesome.Icon.faw_plus).color(Color.WHITE));
        stock.isAdded = false;
    }

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("changed_stock", stock);
        setResult(RESULT_OK, intent);
        finish();
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
