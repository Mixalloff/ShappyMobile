package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Класс обработки ответа сервера. Вызывает методы реализованного пользователем интерфейса
public class ServerResponseHandler implements ResponseInterface{

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
            case "stocks": {
                this.onUserGetStocksByCompany(response);
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

    @Override
    public void onError400(JSONObject response) {

    }

    @Override
    public void onError403(JSONObject response) {

    }

    @Override
    public void onError404(JSONObject response) {

    }

    @Override
    public void onError500(JSONObject response) {

    }

    @Override
    public void onRegister(JSONObject response) {

    }

    @Override
    public void onAuthorize(JSONObject response) {

    }

    @Override
    public void onUserGetAllStocks(JSONObject response) {

    }

    @Override
    public void onUserGetAllCompanies(JSONObject response) {

    }

    @Override
    public void onUserGetAllCategories(JSONObject response) {

    }

    @Override
    public void onUserSubscribeStock(JSONObject response) {

    }

    @Override
    public void onUserUnsubscribeStock(JSONObject response) {

    }

    @Override
    public void onUserGetFeed(JSONObject response) {

    }

    @Override
    public void onUserGetStocksByCompany(JSONObject response) {

    }

    @Override
    public void onUserGetStocksByWord(JSONObject response) {

    }

    @Override
    public void onUserGetStocksByFilter(JSONObject response) {

    }

    @Override
    public void onUserGetAllFriends(JSONObject response) {

    }

    @Override
    public void onUserAddFriend(JSONObject response) {

    }

    @Override
    public void onUserDeleteFriend(JSONObject response) {

    }

    @Override
    public void onUserGetFriendsFeed(JSONObject response) {

    }
}
