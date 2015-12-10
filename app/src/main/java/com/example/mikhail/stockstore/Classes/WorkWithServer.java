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
    public static String serverURL = "https://obscure-headland-5700.herokuapp.com/";
    private static SharedPreferences settings;

    // Отправляет запрос GET
    public static String executeGet(String targetURL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(serverURL+targetURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Отправляет POST запрос по адресу targetURL с параметрами urlParameters
    public static String executePost(String targetURL, String urlParameters){
        HttpURLConnection connection = null;

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL(serverURL + targetURL);

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            // Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = rd.readLine()) != null){
                response.append(line);
            }
            rd.close();
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

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
