package com.example.mikhail.stockstore.Classes;

import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Интерфейс для обработки ответа сервера. Реализует все возможные ответы
public interface ResponseInterface {
    void onError400(JSONObject response);
    void onError403(JSONObject response);
    void onError404(JSONObject response);
    void onError500(JSONObject response);

    void onRegister(JSONObject response);

    void onAuthorize(JSONObject response);

    void onUserGetAllStocks(JSONObject response);

    void onUserGetAllCompanies(JSONObject response);

    void onUserGetAllCategories(JSONObject response);

    void onUserSubscribeStock(JSONObject response);

    void onUserUnsubscribeStock(JSONObject response);

    void onUserGetFeed(JSONObject response);

    void onUserGetStocksByCompany(JSONObject response);

    void onUserGetStocksByWord(JSONObject response);

    void onUserGetStocksByFilter(JSONObject response);

    void onUserGetAllFriends(JSONObject response);

    void onUserAddFriend(JSONObject response);

    void onUserDeleteFriend(JSONObject response);

    void onUserGetFriendsFeed(JSONObject response);

    void onUserGetFriendsFilter(JSONObject response);
}
