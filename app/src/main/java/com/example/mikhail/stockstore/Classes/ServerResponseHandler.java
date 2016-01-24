package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Класс обработки ответа сервера. Вызывает методы реализованного пользователем интерфейса
public class ServerResponseHandler implements ResponseInterface{

    public void CheckResponse(JSONObject response) throws JSONException {
        switch (response.get("type").toString()){
            case "Internal server error": { this.onInternalServerError(response);break;}
            case "Unknown request url": { this.onUnknownRequestUri(response);break;}
            case "error": { this.onError(response);break;}
            case "register": { this.onRegister(response); break;}
            case "token": { this.onGetToken(response); break;}
            case "stock": { this.onUserGetAllStocks(response); break;}
            case "companies": { this.onUserGetAllCompanies(response); break; }
            case "categories": { this.onUserGetAllCategories(response); break; }
            case "subscribeStock": { this.onUserAddStock(response); break; }
            case "userstocks": { this.onUserGetFeed(response); break; }
            case "stocks": { this.onUserGetStocksByCompany(response); break;}

            case "allfriends": { this.onUserGetAllFriends(response); break;}
            case "addfriend": { this.onUserAddFriend(response); break;}
            case "deletefriend": { this.onUserDeleteFriend(response); break;}
            case "friendsfeed": { this.onUserGetFriendsFeed(response); break;}

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

    @Override
    public void onUserGetAllCategories(JSONObject response) {

    }

    @Override
    public void onUserAddStock(JSONObject response) {

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
