package com.example.mikhail.stockstore.Classes;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 06.12.15.
 */
// Класс для работы с API системы. Конструирует запросы с задаными параметрами
public class APIRequestConstructor {

    // Метод отравки запроса на регистрацию пользователя
    // Возвращает JSON объект с ответом сервера
    public static JSONObject userRegister(String name, String surname, String email, String phone, String password){
        try {
            return new JSONObject(WorkWithServer.executePost("auth/register/user",
                    "login=" + email +
                    "&password=" + password +
                    "&name=" + name +
                    "&surname=" + surname +
                    "&phone=" + phone
             ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод отравки запроса на авторизацию пользователя
    // Возвращает JSON объект с ответом сервера
    public static JSONObject userAuthorize(String login, String password){
        try {
            return new JSONObject(WorkWithServer.executePost("auth/authorize",
                    "type=user"+
                    "&login="+login+
                    "&password="+password
            ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод отравки запроса на получение всех акций
    // Возвращает JSON объект с ответом сервера
    public static JSONObject getAllStocks(Activity activity){
        try {
            String token = WorkWithServer.getToken(activity);
            String response = WorkWithServer.executeGet("stocks/all?token=" + token);
            return new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
