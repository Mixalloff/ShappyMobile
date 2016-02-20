package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Класс обработки ответа сервера. Вызывает методы реализованного пользователем интерфейса
public class ServerResponseHandler{

    public void CheckResponse(JSONObject response) throws JSONException {
        String responseType = "";
        try {
            responseType = response.get("type").toString();
        }catch(Exception e) {
           // this.DefaultFunc(response);

        }
        switch (responseType) {
            case "register": {
                this.onRegister(response);
                break;
            }
            case "userinfo": {
                this.onAuthorize(response);
                break;
            }
            case "stock": {
                this.onUserGetAllStocks(response);
                break;
            }
            case "companies": {
                this.onUserGetAllCompanies(response);
                break;
            }
            case "categories": {
                this.onUserGetAllCategories(response);
                break;
            }
            case "subscribeStock": {
                this.onUserSubscribeStock(response);
                break;
            }
            case "unsubscribestock": {
                this.onUserUnsubscribeStock(response);
                break;
            }
            case "userstocks": {
                this.onUserGetFeed(response);
                break;
            }
            case "stockinfo": {
                this.onUserGetStocksInfo(response);
                break;
            }
            case "stocks": {
                this.onUserGetStocksByCompany(response);
                this.onUserGetStocksByFilter(response);
                this.onUserGetStocksByWord(response);
                break;
            }

            case "allfriends": {
                this.onUserGetAllFriends(response);
                break;
            }
            case "addfriend": {
                this.onUserAddFriend(response);
                break;
            }
            case "deletefriend": {
                this.onUserDeleteFriend(response);
                break;
            }
            case "friendsfeed": {
                this.onUserGetFriendsFeed(response);
                break;
            }

            case "friendsfilter": {
                this.onUserGetFriendsFilter(response);
                break;
            }

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
                this.DefaultFunc(response);
            }
        }

    }

    // Функция, выполняемая если ни один из известных тиов ответа не подошел
    public void DefaultFunc(JSONObject response){

    }

    public void onError400(JSONObject response) {

    }

    public void onError403(JSONObject response) {

    }

    public void onError404(JSONObject response) {

    }

    public void onError500(JSONObject response) {

    }

    public void onRegister(JSONObject response) {

    }

    public void onAuthorize(JSONObject response) {

    }

    public void onUserGetAllStocks(JSONObject response) {

    }

    public void onUserGetAllCompanies(JSONObject response) {

    }

    public void onUserGetAllCategories(JSONObject response) {

    }

    public void onUserSubscribeStock(JSONObject response) {

    }

    public void onUserUnsubscribeStock(JSONObject response) {

    }

    public void onUserGetFeed(JSONObject response) {

    }

    public void onUserGetStocksByCompany(JSONObject response) {

    }

    public void onUserGetStocksByWord(JSONObject response) {

    }

    public void onUserGetStocksByFilter(JSONObject response) {

    }

    public void onUserGetAllFriends(JSONObject response) {

    }

    public void onUserAddFriend(JSONObject response) {

    }

    public void onUserDeleteFriend(JSONObject response) {

    }

    public void onUserGetFriendsFeed(JSONObject response) {

    }

    public void onUserGetFriendsFilter(JSONObject response) {

    }

    public void onUserGetStocksInfo(JSONObject response) {

    }
}
