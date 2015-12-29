package com.example.mikhail.stockstore;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Classes.WorkWithServer;

import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends ActionBarActivity {
    private ResponseInterface handler = new ResponseInterface() {
        @Override
        public void onInternalServerError(JSONObject response) {

        }

        @Override
        public void onUnknownRequestUri(JSONObject response) {

        }

        @Override
        public void onError(JSONObject response) {
            Toast.makeText(getApplicationContext(), response.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRegister(JSONObject response) {

        }

        @Override
        public void onGetToken(JSONObject response) {
            try {
                WorkWithServer.saveToken(response.get("data").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Токен получен!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUserGetAllStocks(JSONObject response) {

        }

        @Override
        public void onUserGetAllCompanies(JSONObject response) {

        }

        @Override
        public void onUserAddStock(JSONObject response) {

        }

        @Override
        public void onUserGetFeed(JSONObject response) {

        }

        @Override
        public void onUserGetStocksByCompany(JSONObject response) {

        }

        @Override
        public void onUserGetStocksByWord(JSONObject response) {

        }

        @Override
        public void onUserGetStocksByFilter(JSONObject response) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    public void onEnterBtnClick(View view) {
        String Login = ((EditText)findViewById(R.id.loginField)).getText().toString();
        String Password = ((EditText)findViewById(R.id.passwordField)).getText().toString();

        try {
            AsyncRequestToServer request = new AsyncRequestToServer(this);
            request.setParameters(APIRequestConstructor.authParameters(Login, Password));
            ServerResponseHandler.CheckResponse(request.execute(APIConstants.USER_AUTH).get(), handler);
            //ServerResponseHandler.CheckResponse(APIRequestConstructor.userAuthorize(Login, Password), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), WorkWithServer.getToken(this),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(StartActivity.this, StocksActivity.class);
        startActivity(intent);
        StartActivity.this.finish();
    }

    public void OnRegisterBtnClick(View view) {
        Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
