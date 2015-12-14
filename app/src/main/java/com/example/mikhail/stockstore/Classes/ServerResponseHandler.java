package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Класс обработки ответа сервера. Вызывает методы реализованного пользователем интерфейса
public class ServerResponseHandler implements ResponseInterface{

    public static void CheckResponse(JSONObject response, ResponseInterface handler) throws JSONException {
        switch (response.get("type").toString()){
            case "Internal server error": { handler.onInternalServerError(response);break;}
            case "Unknown request url": { handler.onUnknownRequestUri(response);break;}
            case "error": { handler.onError(response);break;}

            case "register": { handler.onRegister(response); break;}
            case "token": { handler.onGetToken(response); break;}

            case "stock": { handler.onUserGetAllStocks(response); break;}

            case "companies": { handler.onUserGetAllCompanies(response); break; }

            default: {};
        }
    }

    @Override
    public void onInternalServerError(JSONObject response) {

    }

    @Override
    public void onUnknownRequestUri(JSONObject response) {

    }

    @Override
    public void onError(JSONObject response) {

    }

    @Override
    public void onRegister(JSONObject response) {

    }

    @Override
    public void onGetToken(JSONObject response) {

    }

    @Override
    public void onUserGetAllStocks(JSONObject response) {

    }

    @Override
    public void onUserGetAllCompanies(JSONObject response) {

    }
}
