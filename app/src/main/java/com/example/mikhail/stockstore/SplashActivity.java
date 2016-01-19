package com.example.mikhail.stockstore;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Classes.ResponseInterface;

import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

   // private final int SPLASH_DISPLAY_LENGTH = 1000;
    AsyncRequestToServer request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initFonts();

        //WorkWithServer.deleteToken();
      //  WorkWithServer.saveToken("e58dc32c33ee3d047236443080067af30af5ed5e644ea5f80ad1e866fdaab1f4");

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, StocksActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/

        request = new AsyncRequestToServer(this, handler);

        request.execute(APIConstants.GET_ALL_STOCKS);
    }

    private ResponseInterface handler = new ResponseInterface() {
        @Override
        public void onError(JSONObject response) {
            Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

        @Override
        public void onInternalServerError(JSONObject response) {
            Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUnknownRequestUri(JSONObject response) {
            Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUserGetAllStocks(JSONObject response) {
            Toast.makeText(SplashActivity.this, "yes", Toast.LENGTH_SHORT).show();
            // Передача полученных акций в активити с акциями
            Intent intent = new Intent(SplashActivity.this, StocksActivity.class);
            intent.putExtra("stocks", response.toString());
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

    };

        private void initFonts(){
        String custom_font = "fonts/berlin-sans-fb-demi-bold.ttf";
        Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);

        //((TextView) findViewById(R.id.projNameTextView)).setTypeface(CF);
        ((TextView) findViewById(R.id.sloganTextView)).setTypeface(CF);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
