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
import java.util.Date;

/**
 * Created by mikhail on 02.12.15.
 */
public class Stock {
    public String name;
    public Date dateStart;
    public Date dateFinish;
    public Bitmap photo;
    public String description;
    public Company company;

    public Stock(String name, String description, String photo, Company company){
        this.name = name;
        this.dateStart = new Date();
        this.dateFinish = new Date();
        this.photo = CommonFunctions.getPhoto(photo);
        this.description = description;
        this.company = company;
    }

    public Stock(String name, String description, String photo, Company company, Date dateStart, Date dateFinish) {
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
            this.name = stockObj.has("name") ? stockObj.getString("name") : defaultName;
            this.dateStart = stockObj.has("dateStart") ? new Date(Date.parse(stockObj.getString("dateStart"))) : defaultDateStart;
            this.dateFinish = stockObj.has("dateFinish") ? new Date(Date.parse(stockObj.getString("dateFinish"))) : defaultDateFinish;
            this.photo = stockObj.has("photo") ? CommonFunctions.getPhoto(stockObj.getString("photo")) : GlobalVariables.defaultPhoto;
            this.description =  stockObj.has("description") ? stockObj.getString("description") : defaultDescription;

            this.company = new Company(stockObj.get("company").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
