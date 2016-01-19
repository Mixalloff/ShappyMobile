package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 07.12.15.
 */
// Класс обработки ответа сервера. Вызывает методы реализованного пользователем интерфейса
public class ServerResponseHandler extends ResponseInterface{

    public static void CheckResponse(JSONObject response, ResponseInterface handler) throws JSONException {
        switch (response.get("type").toString()){
            case "Internal server error": { handler.onInternalServerError(response);break;}
            case "Unknown request url": { handler.onUnknownRequestUri(response);break;}
            case "error": { handler.onError(response);break;}
            case "register": { handler.onRegister(response); break;}
            case "token": { handler.onGetToken(response); break;}
            case "stock": { handler.onUserGetAllStocks(response); break;}
            case "companies": { handler.onUserGetAllCompanies(response); break; }
            case "subscribeStock": { handler.onUserAddStock(response); break; }
            case "userstocks": { handler.onUserGetFeed(response); break; }
            case "stocks": { handler.onUserGetStocksByCompany(response); break;}

            case "allfriends": { handler.onUserGetAllFriends(response); break;}
            case "addfriend": { handler.onUserAddFriend(response); break;}
            case "deletefriend": { handler.onUserDeleteFriend(response); break;}
            case "friendsfeed": { handler.onUserGetFriendsFeed(response); break;}


            default: {};
        }
    }

}
