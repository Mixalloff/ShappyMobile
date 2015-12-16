package com.example.mikhail.stockstore.Entities;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mikhail on 02.12.15.
 */
public class Stock {
    public String name;
    public Date dateStart;
    public Date dateFinish;
    public Bitmap photo;
    public String description;
    public String id;
    public Company company;


    public Stock(String id, String name, String description, String photo, Company company){
        this.id = id;
        this.name = name;
        this.dateStart = new Date();
        this.dateFinish = new Date();
        this.photo = CommonFunctions.getPhoto(photo);
        this.description = description;
        this.company = company;
    }

    public Stock(String id, String name, String description, String photo, Company company, Date dateStart, Date dateFinish) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.photo = CommonFunctions.getPhoto(photo);
        this.description = description;
        this.company = company;
    }

    public Stock(JSONObject stockObj){
        String defaultName = "default name";
        Date defaultDateStart = new Date();
        Date defaultDateFinish = new Date();
        String defaultDescription = "default name";

        try {
            this.id = stockObj.has("id") ? stockObj.getString("id") : "0";
            this.name = stockObj.has("name") ? stockObj.getString("name") : defaultName;

            this.dateStart = stockObj.has("startDate") ? CommonFunctions.dateFormat(stockObj.getString("startDate")) : defaultDateStart;
            this.dateFinish = stockObj.has("endDate") ? CommonFunctions.dateFormat(stockObj.getString("endDate")) : defaultDateFinish;

            this.photo = stockObj.has("logo") ? CommonFunctions.getPhoto(GlobalVariables.server + stockObj.getString("logo")) : GlobalVariables.defaultPhoto;
            this.description =  stockObj.has("description") ? stockObj.getString("description") : defaultDescription;

            this.company = new Company(stockObj.get("company").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
