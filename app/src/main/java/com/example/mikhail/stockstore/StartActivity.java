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

import com.example.mikhail.stockstore.Classes.WorkWithServer;

import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Подгружаем токен, если есть
        WorkWithServer.getToken(this);
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
       // WorkWithServer.executePost("auth/register/user", "login=aaaaaa&password=bbbbbb");


        String Login = ((EditText)findViewById(R.id.loginField)).getText().toString();
        String Password = ((EditText)findViewById(R.id.passwordField)).getText().toString();
        String authString = WorkWithServer.executePost("auth/authorize", "login="+Login+"&password="+Password);
        WorkWithServer.saveToken(WorkWithServer.parseToken(authString));



        Toast.makeText(getApplicationContext(), WorkWithServer.getToken(this),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(StartActivity.this, StocksActivity.class);
        startActivity(intent);
    }

    public void OnRegisterBtnClick(View view) {
        Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
