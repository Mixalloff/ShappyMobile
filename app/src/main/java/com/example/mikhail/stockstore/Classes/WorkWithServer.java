package com.example.mikhail.stockstore.Classes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mikhail on 06.12.15.
 */
public class WorkWithServer {

    //public static String serverURL = GlobalVariables.server;
    private static SharedPreferences settings;

    // Получение токена из строки (ответа сервера)
    public static String parseToken(String data){
        try {
            return new JSONObject(data).get("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение токена из ресурсов
    public static String getToken(Activity activity){
        settings = activity.getSharedPreferences("settings", 0);
        try {
                return settings.getString("token", "");
            }catch(Exception e){
                return "";
            }
    }

    // Сохранение токена в ресурсы
    public static void saveToken(String token){
        try {
            SharedPreferences.Editor e = settings.edit();
            e.clear();
            e.putString("token", token);
            //e.commit();
            e.apply();
        }catch(Exception e){

        }
    }

    // Удаление токена (при разлогинивании)
    public static void deleteToken(){
        try {
            SharedPreferences.Editor e = settings.edit();
            e.clear();
            e.commit();
        }catch(Exception e){

        }
    }


}
