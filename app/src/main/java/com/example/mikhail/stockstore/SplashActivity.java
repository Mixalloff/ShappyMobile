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
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;

import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

   // private final int SPLASH_DISPLAY_LENGTH = 1000;
    AsyncRequestToServer request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initFonts();

        request = new AsyncRequestToServer(this, handler);

        request.execute(APIConstants.GET_ALL_STOCKS);
    }

    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onUserGetAllStocks(JSONObject response) {
            //Toast.makeText(SplashActivity.this, "yes", Toast.LENGTH_SHORT).show();
            // Передача полученных акций в активити с акциями
            Intent intent = new Intent(SplashActivity.this, StocksActivity.class);
            intent.putExtra("stocks", response.toString());
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

        @Override
        public void onError400(JSONObject response){
            Toast.makeText(SplashActivity.this, "ошибка 400", Toast.LENGTH_SHORT).show();
            OpenLoginActivity();
        }

        @Override
        public void onError403(JSONObject response){
            Toast.makeText(SplashActivity.this, "ошибка 403", Toast.LENGTH_SHORT).show();
            OpenLoginActivity();
        }

        @Override
        public void onError404(JSONObject response){
            Toast.makeText(SplashActivity.this, "ошибка 404", Toast.LENGTH_SHORT).show();
            OpenLoginActivity();
        }

        @Override
        public void onError500(JSONObject response){
            Toast.makeText(SplashActivity.this, "ошибка 500", Toast.LENGTH_SHORT).show();
            OpenLoginActivity();
        }

        @Override
        public void DefaultFunc(JSONObject response) {
            Toast.makeText(SplashActivity.this, "ошибка при запросе", Toast.LENGTH_SHORT).show();
            OpenLoginActivity();
        }

    };

    // Открытие активити авторизации
    private void OpenLoginActivity(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

        private void initFonts(){
        try {
            String custom_font = "fonts/berlin-sans-fb-demi-bold.ttf";
            Typeface CF = Typeface.createFromAsset(getAssets(), custom_font);

            //((TextView) findViewById(R.id.projNameTextView)).setTypeface(CF);
            ((TextView) findViewById(R.id.sloganTextView)).setTypeface(CF);
        }catch(Exception e){
            e.printStackTrace();
        }
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
