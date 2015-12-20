package com.example.mikhail.stockstore.AsyncClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;

import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Classes.WorkWithServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mikhail on 19.12.15.
 */
public class AsyncRequestToServer extends AsyncTask<String, Integer, JSONObject>
{

    //private static String method = "";
    private static String urlParams = "";
    Activity activity;
    private ResponseInterface handler;

    public void setHandler(ResponseInterface handler){ this.handler = handler; }

    public AsyncRequestToServer(Activity activity){
        this.activity = activity;
    }

    public AsyncRequestToServer(Activity activity, ResponseInterface handler){
        this.activity = activity;
        this.handler = handler;
    }

    private static String spinnerMessage = "Загрузка";

    public void setParameters(String urlParams){
     //   this.method = method;
       this.urlParams = urlParams;
    }

    public void setSpinnerMessage(String message){
        this.spinnerMessage = message;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    ProgressDialog spinner;

    // Метод выполняется перед началом doInBackground()
    protected void onPreExecute(){
        spinner = new ProgressDialog(activity);
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setMessage(spinnerMessage);
        spinner.setIndeterminate(true); // выдать значок ожидания
        spinner.setCancelable(true);
        spinner.show();
    }

    // Отправляет GET запрос
    private String sendGetRequest(String targetURL) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";

        try {
            url = new URL(GlobalVariables.server + "/" + targetURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    // Отправляет POST запрос
    private String sendPostRequest(String targetURL) {
        HttpURLConnection connection = null;

        try{
            URL url = new URL(GlobalVariables.server + "/" + targetURL);

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParams.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParams);
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

    // Здесь делайте всю длительную по времени работу.
    protected JSONObject doInBackground(String... targetURL)
    {
        for (int i = 0; i < targetURL.length; i++)
        {

            switch(targetURL[i]){
                case APIConstants.GET_ALL_STOCKS: { return getAllStocks(); }
                case APIConstants.GET_ALL_COMPANIES: { return getAllCompanies(); }
                case APIConstants.USER_AUTH: { return userAuthorize(); }
                case APIConstants.USER_REGISTER: { return userRegister(); }
             //   case APIConstants.GET_ALL_CATEGORIES: { return getAllCategories; }
                case APIConstants.USER_ADD_STOCK: { return userAddStock(); }
                case APIConstants.USER_GET_FEED: { return userGetFeed(); }

                default:{}
            }

            // Ранний выход, если был вызван cancel().
            if (isCancelled())
                break;
        }
        return null;

    }

    // Этот метод будет вызван каждый раз, когда в потоке
    // выполнится publishProgress().
    protected void onProgressUpdate(Integer... progress)
    {
        //setProgressPercent(progress[0]);
    }

    // Этот метод будет вызван, когда doInBackground() завершится
    protected void onPostExecute(JSONObject result)
    {
        spinner.cancel();
        try {
            // Если есть обработчик, выполняем соответствующую функцию
            if (handler != null)
                ServerResponseHandler.CheckResponse(result, handler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //showNotification("Downloaded " + result + " bytes");
    }



    // Метод отравки запроса на регистрацию пользователя
    // Возвращает JSON объект с ответом сервера
    public JSONObject userRegister(){

        try {
            String token = WorkWithServer.getToken(activity);
            return new JSONObject(sendPostRequest(APIConstants.USER_REGISTER_ROUTE));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод отравки запроса на авторизацию пользователя
    // Возвращает JSON объект с ответом сервера
    public JSONObject userAuthorize(){

        try {
            String token = WorkWithServer.getToken(activity);
            return new JSONObject(sendPostRequest(APIConstants.USER_AUTH_ROUTE));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод отравки запроса на получение всех акций
    // Возвращает JSON объект с ответом сервера
    public JSONObject getAllStocks(){
        String result;
        try {
            String token = WorkWithServer.getToken(activity);
            result = sendGetRequest(APIConstants.GET_ALL_STOCKS_ROUTE + "?token=" + token);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();return null;
        }
    }

    // Получение всех категорий
    public JSONObject getAllCompanies(){
        try {
            String token = WorkWithServer.getToken(activity);
            return new JSONObject(sendGetRequest(APIConstants.GET_ALL_COMPANIES_ROUTE + "?token=" + token));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Подписка на акцию с id = stockId
    public JSONObject userAddStock(){

        try {
            String token = WorkWithServer.getToken(activity);
            return new JSONObject(sendPostRequest(APIConstants.USER_ADD_STOCK_ROUTE));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Получение всех акций на стене пользователя
    public JSONObject userGetFeed(){
        try {
            String token = WorkWithServer.getToken(activity);
            return new JSONObject(sendGetRequest(APIConstants.USER_GET_FEED_ROUTE + "?token=" + token));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}