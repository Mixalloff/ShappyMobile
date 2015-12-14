package com.example.mikhail.stockstore.Classes;

import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Интерфейс для обработки ответа сервера. Реализует все возможные ответы
public interface ResponseInterface {
    public void onInternalServerError(JSONObject response);
    public void onUnknownRequestUri(JSONObject response);
    public void onError(JSONObject response);

    public void onRegister(JSONObject response);
    public void onGetToken(JSONObject response);

    public void onUserGetAllStocks(JSONObject response);

    public void onUserGetAllCompanies(JSONObject response);
}
