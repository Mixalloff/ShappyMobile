package com.example.mikhail.stockstore.Classes;

import android.app.Activity;

/**
 * Created by mikhail on 06.12.15.
 */
// Класс для работы с API системы. Конструирует запросы с задаными параметрами
public class APIRequestConstructor {

    final static String GET = "GET";
    final static String POST = "POST";

    public static String authParameters(String login, String password){
        return "type=user"+
               "&login="+login+
               "&password="+password;
    }

    public static String registerParameters(String name, String surname, String email, String phone, String password){
        return "login=" + email +
                "&password=" + password +
                "&FIO=" + name + " " + surname +
                "&phone=" + phone;
    }

    public static String userAddStockParameters(String stockId){
        return "id="+stockId;
    }


    // Метод отравки запроса на регистрацию пользователя
    // Возвращает JSON объект с ответом сервера
  /*  public static JSONObject userRegister(String name, String surname, String email, String phone, String password){
        AsyncRequestToServer request = new AsyncRequestToServer();
        request.setParameters(POST,
                "login=" + email +
                "&password=" + password +
                "&name=" + name +
                "&surname=" + surname +
                "&phone=" + phone);
        try {
            return request.execute("auth/register/user").get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод отравки запроса на авторизацию пользователя
    // Возвращает JSON объект с ответом сервера
    public static JSONObject userAuthorize(String login, String password){
        AsyncRequestToServer request = new AsyncRequestToServer();
        request.setParameters(POST,
                "type=user"+
                "&login="+login+
                "&password="+password);
        try {
            return request.execute("auth/authorize/user").get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод отравки запроса на получение всех акций
    // Возвращает JSON объект с ответом сервера
    public static JSONObject getAllStocks(Activity activity){
        AsyncRequestToServer request = new AsyncRequestToServer();
        request.setParameters(GET,"");
        try {
            String token = WorkWithServer.getToken(activity);
            return new JSONObject(request.execute("stocks/all?token=" + token).get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Получение всех категорий
    public static JSONObject getAllCompanies(Activity activity){
        AsyncRequestToServer request = new AsyncRequestToServer();
        request.setParameters(GET,"");
        try {
            String token = WorkWithServer.getToken(activity);
            return request.execute("companies/all?token=" + token).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Подписка на акцию с id = stockId
    public static JSONObject userSubscribeStock(Activity activity, String stockId){
        String token = WorkWithServer.getToken(activity);
        AsyncRequestToServer request = new AsyncRequestToServer();
        request.setParameters(POST,
                "token=" + token +
                "&id="+stockId);
        try {
            return request.execute("users/addstock").get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Получение всех акций на стене пользователя
    public static JSONObject userGetFeed(Activity activity){
        AsyncRequestToServer request = new AsyncRequestToServer();
        request.setParameters(GET,"");
        try {
            String token = WorkWithServer.getToken(activity);
            return request.execute("users/feed?token=" + token).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
