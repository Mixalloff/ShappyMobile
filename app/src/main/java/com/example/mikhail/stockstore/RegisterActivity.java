package com.example.mikhail.stockstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.WorkWithServer;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void onRegisterBtnClick(View view) {
        String Name =  ((EditText) findViewById(R.id.nameText)).getText().toString();
        String Surname =  ((EditText) findViewById(R.id.surnameText)).getText().toString();
        String Email =  ((EditText) findViewById(R.id.emailText)).getText().toString();
        String Phone =  ((EditText) findViewById(R.id.phoneText)).getText().toString();
        String Password =  ((EditText) findViewById(R.id.passwordText)).getText().toString();

        //String response = WorkWithServer.executePost("auth/register/user", "login="+Email+"&password="+Password);
        //WorkWithServer.saveToken(WorkWithServer.parseToken(response));

        JSONObject response = APIRequestConstructor.userRegister(Name, Surname, Email, Phone, Password);
        try {
            WorkWithServer.saveToken(response.get("data").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(RegisterActivity.this, StocksActivity.class);
        startActivity(intent);
    }
}
