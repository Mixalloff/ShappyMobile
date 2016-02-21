package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Класс обработки ответа сервера. Вызывает методы реализованного пользователем интерфейса
public class ServerResponseHandler{

    // Обработка ошибок
    public void errorsHandle(JSONObject response){
        try {
            String type = response.getString("type");
            switch (type){
                case "400": {
                    this.onError400(response);
                    break;
                }
                case "403": {
                    this.onError403(response);
                    break;
                }
                case "404": {
                    this.onError404(response);
                    break;
                }
                case "500": {
                    this.onError500(response);
                    break;
                }
                default: {
                    this.defaultFunc(response);
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Функция, выполняемая если ни один из известных типов ответа не подошел
    public void defaultFunc(JSONObject response){}

    public void onError400(JSONObject response) {}

    public void onError403(JSONObject response) {}

    public void onError404(JSONObject response) {}

    public void onError500(JSONObject response) {}

    public void onRegister(JSONObject response) {}

    public void onAuthorize(JSONObject response) {}

    public void onUserGetAllStocks(JSONObject response) {}

    public void onUserGetAllCompanies(JSONObject response) {}

    public void onUserGetAllCategories(JSONObject response) {}

    public void onUserSubscribeStock(JSONObject response) {}

    public void onUserUnsubscribeStock(JSONObject response) {}

    public void onUserGetFeed(JSONObject response) { }

    public void onUserGetStocksByCompany(JSONObject response) {}

    public void onUserGetStocksByWord(JSONObject response) {}

    public void onUserGetStocksByFilter(JSONObject response) {}

    public void onUserGetAllFriends(JSONObject response) {}

    public void onUserAddFriend(JSONObject response) {}

    public void onUserDeleteFriend(JSONObject response) {}

    public void onUserGetFriendsFeed(JSONObject response) {}

    public void onUserGetFriendsFilter(JSONObject response) {}

    public void onUserGetStocksInfo(JSONObject response) {}

    public void onUserGetSubscriptionsStocks(JSONObject response){}

    public void onUserSubscribeCompany(JSONObject response){}

    public void onUserUnsubscribeCompany(JSONObject response){}

    public void onUserGetSubscribedCompanies(JSONObject response){}
}
