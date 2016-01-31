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
public class WorkWithResources {

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
       // saveToken("e58dc32c33ee3d047236443080067af30af5ed5e644ea5f80ad1e866fdaab1f4");
        try {
                return settings.getString("token", "");
            }catch(Exception e){
                return "";
            }
    }

    // Сохранение токена в ресурсы
    public static void saveToken(String token){
        try {
            SharedPreferences.Editor ed = settings.edit();
            ed.clear();
            ed.putString("token", token);
            //e.commit();
            ed.apply();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void saveToken(Activity activity, String token){
        settings = activity.getSharedPreferences("settings", 0);
        saveToken(token);
    }

    // Сохранение информации о пользователе в ресурсы
    public static void saveUserInfo(String surname, String name){
        try {
            SharedPreferences.Editor ed = settings.edit();
           // e.clear();
            ed.putString("name", name);
            ed.putString("surname", surname);
            //e.commit();
            ed.apply();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void saveUserInfo(Activity activity, String surname, String name){
        settings = activity.getSharedPreferences("settings", 0);
        saveUserInfo(surname, name);
    }

    public static String getCurrentUserFIO(){
        //settings = activity.getSharedPreferences("settings", 0);
        // saveToken("e58dc32c33ee3d047236443080067af30af5ed5e644ea5f80ad1e866fdaab1f4");
        try {
            return settings.getString("surname", "defurname") + "\n" + settings.getString("name", "defname");
        }catch(Exception e){
            return "";
        }
    }

    // Удаление токена (при разлогинивании)
    public static void deleteToken(){
        try {
            SharedPreferences.Editor ed = settings.edit();
            ed.clear();
            ed.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteToken(Activity activity){
        try {
            settings = activity.getSharedPreferences("settings", 0);
            deleteToken();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
