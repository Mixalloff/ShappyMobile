package com.example.mikhail.stockstore.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 25.01.16.
 */
public class ErrorsWorker {
    // Ошибки клиента
    public static String BAD_REQUEST = "{\"type\": \"400\", \"data\": \"Bad request!\", \"error\": \"true\"  }";
    public static String FORBIDDEN = "{\"type\": \"403\", \"data\": \"Forbidden!\", \"error\": \"true\"  }";
    public static String NOT_FOUND = "{\"type\": \"404\", \"data\": \"Not found!\", \"error\": \"true\"  }";
    // Ошибки сервера
    public static String INTERNAL_SERVER_ERROR = "{\"type\": \"500\", \"data\": \"Internal Server Error!\", \"error\": \"true\"  }";
    // Неизвестная ошибка по умолчанию
    public static String DEFAULT = "{\"type\": \"error\", \"data\": \"Error!\", \"error\": \"true\" }";

    public static JSONObject ErrorObject(int type){
        try {
            switch (type) {
                case 400: { return new JSONObject(BAD_REQUEST); }
                case 403: { return new JSONObject(FORBIDDEN); }
                case 404: { return new JSONObject(NOT_FOUND); }
                case 500: { return new JSONObject(INTERNAL_SERVER_ERROR); }

                default: { return new JSONObject(DEFAULT);}
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
