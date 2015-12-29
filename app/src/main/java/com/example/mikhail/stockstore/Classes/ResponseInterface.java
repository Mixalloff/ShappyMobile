package com.example.mikhail.stockstore.Classes;

import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Интерфейс для обработки ответа сервера. Реализует все возможные ответы
public interface ResponseInterface {
    void onInternalServerError(JSONObject response);
    void onUnknownRequestUri(JSONObject response);
    void onError(JSONObject response);

    void onRegister(JSONObject response);
    void onGetToken(JSONObject response);
    void onUserGetAllStocks(JSONObject response);
    void onUserGetAllCompanies(JSONObject response);
    void onUserAddStock(JSONObject response);
    void onUserGetFeed(JSONObject response);

    void onUserGetStocksByCompany(JSONObject response);
    void onUserGetStocksByWord(JSONObject response);
    void onUserGetStocksByFilter(JSONObject response);
}
