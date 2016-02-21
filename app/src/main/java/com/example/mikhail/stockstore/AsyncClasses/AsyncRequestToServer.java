package com.example.mikhail.stockstore.AsyncClasses;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.ErrorsWorker;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Classes.WorkWithResources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mikhail on 19.12.15.
 */
public class AsyncRequestToServer extends AsyncTask<String, Integer, JSONObject>
{
    SwipeRefreshLayout swipe = null;

    // Callback
    private OnTaskCompleted callback;

    // Параметры запроса
    private static String urlParams = "";
    // Активити в которой создан объект
    Activity activity;
    // Выполняющий действия обработчик ответа
    private ServerResponseHandler handler = new ServerResponseHandler();

    public void setHandler(ServerResponseHandler handler){ this.handler = handler; }

    /*public AsyncRequestToServer(Activity activity){
        this.activity = activity;
    }*/

    public void setSwipeRefresh(SwipeRefreshLayout swipe){
        this.swipe = swipe;
    }

    public AsyncRequestToServer(Activity activity, ServerResponseHandler handler){
        this(activity, handler, null);
    }

    public AsyncRequestToServer(Activity activity, ServerResponseHandler handler, OnTaskCompleted callback){
        this.activity = activity;
        this.handler = handler;
        this.callback = callback;
    }

    // Установка параметров с подпиской токеном
    public void setParameters(String urlParams){
       String token = WorkWithResources.getToken(activity);
       this.urlParams = token != null ? "token=" + token + "&" + urlParams : urlParams;
    }

    // Установка токена в параметры
    public void setParameters(){
        setParameters("");
    }


    public void setActivity(Activity activity){
        this.activity = activity;
    }

    // Метод выполняется перед началом doInBackground()
    protected void onPreExecute(){
        /*if(swipe != null){
            swipe.setRefreshing(true);
        }*/
    }

    // Проверяет, есть ли соединение
    public static boolean hasConnection(final Context context)
    {
        try {
            ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return (connectManager.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
           // return internetAvailable;
        }catch(Exception e){
            return false;
        }
    }

    // Отправляет GET запрос
    private String sendGetRequest(String targetURL) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        int code = 0;

        try {

            //String encodedUrl = URLEncoder.encode(targetURL, "UTF-8");
            // String utf8String= new String(targetURL.getBytes("Unicode"), "UTF-8");

            url = new URL(GlobalVariables.server + "/" + targetURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            code = conn.getResponseCode();

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorsWorker.ErrorObject(code).toString();
        }
        return result;
    }

        // Отправляет POST запрос
    private String sendPostRequest(String targetURL) {
        HttpURLConnection connection = null;
        int code = 0;

        try{
            URL url = new URL(GlobalVariables.server + "/" + targetURL);


            connection = (HttpURLConnection)url.openConnection();
           // code = connection.getResponseCode();
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
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return ErrorsWorker.ErrorObject(connection.getResponseCode()).toString();
            } catch (IOException e1) {
                e1.printStackTrace();
                return ErrorsWorker.ErrorObject(code).toString();
            }
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    // Здесь делайте всю длительную по времени работу.
    protected JSONObject doInBackground(String... targetURL)
    {
        if (AsyncRequestToServer.hasConnection(activity)) {
            for (String url : targetURL) {
                try {
                    if(urlParams.equals("")){
                        setParameters();
                    }
                    switch (url) {
                        case APIConstants.GET_ALL_STOCKS: {
                            return new JSONObject(sendGetRequest(APIConstants.GET_ALL_STOCKS + "?" + urlParams));
                        }
                        case APIConstants.GET_ALL_COMPANIES: {
                            return new JSONObject(sendGetRequest(APIConstants.GET_ALL_COMPANIES + "?" + urlParams));
                        }
                        case APIConstants.USER_AUTH: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_AUTH));
                        }
                        case APIConstants.USER_REGISTER: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_REGISTER));
                        }

                        case APIConstants.GET_ALL_CATEGORIES: {
                            return new JSONObject(sendGetRequest(APIConstants.GET_ALL_CATEGORIES + "?" + urlParams));
                        }

                        case APIConstants.USER_SUBSCRIBE_STOCK: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_SUBSCRIBE_STOCK));
                        }
                        case APIConstants.USER_UNSUBSCRIBE_STOCK: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_UNSUBSCRIBE_STOCK));
                        }

                        case APIConstants.USER_GET_FEED: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_FEED + "?" + urlParams));
                        }

                        case APIConstants.USER_GET_STOCKS_INFO: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_STOCKS_INFO + "?" + urlParams));
                        }
                        case APIConstants.USER_GET_STOCKS_BY_COMPANY: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_STOCKS_BY_COMPANY + "?" + urlParams));
                        }
                        case APIConstants.USER_GET_STOCKS_BY_WORDPATH: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_STOCKS_BY_WORDPATH + "?" + urlParams));
                        }
                        case APIConstants.USER_GET_STOCKS_BY_FILTER: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_STOCKS_BY_FILTER + "?" + urlParams));
                        }
                        case APIConstants.USER_GET_ALL_FRIENDS: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_ALL_FRIENDS) + "?" + urlParams);
                        }
                        case APIConstants.USER_ADD_FRIEND: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_ADD_FRIEND));
                        }
                        case APIConstants.USER_DELETE_FRIEND: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_DELETE_FRIEND));
                        }
                        case APIConstants.USER_GET_FRIENDS_FEED: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_FRIENDS_FEED + "?" + urlParams));
                        }
                        case APIConstants.USER_GET_FRIENDS_FILTER: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_FRIENDS_FILTER + "?" + urlParams));
                        }

                        case APIConstants.USER_GET_SUBSCRIPTIONS_STOCKS: {
                            return new JSONObject(sendGetRequest(APIConstants.USER_GET_SUBSCRIPTIONS_STOCKS + "?" + urlParams));
                        }

                        case APIConstants.USER_SUBSCRIBE_COMPANY: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_SUBSCRIBE_COMPANY));
                        }

                        case APIConstants.USER_UNSUBSCRIBE_COMPANY: {
                            return new JSONObject(sendPostRequest(APIConstants.USER_UNSUBSCRIBE_COMPANY));
                        }

                        default: {
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                // Ранний выход, если был вызван cancel().
                if (isCancelled())
                    break;
            }
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
        try {
            // Если есть обработчик, выполняем соответствующую функцию
            if (handler != null)
                handler.CheckResponse(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (swipe != null) { swipe.setRefreshing(false); }

        // Вызов колбэка
        if(callback != null) {
            callback.onTaskCompleted(result);
        }

        //showNotification("Downloaded " + result + " bytes");
    }

}