package com.example.mikhail.stockstore.Entities;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
public class Stock implements Parcelable{
    public String name;
    public Date dateStart;
    public Date dateFinish;
    public String photo;
    public String description;
    public String id;
    public Company company;

    public boolean isAdded;


    public Stock(String id, String name, String description, String photo, Company company, boolean isAdded){
        this.id = id;
        this.name = name;
        this.dateStart = new Date();
        this.dateFinish = new Date();
        this.photo = photo;
        this.description = description;
        this.company = company;
        this.isAdded = isAdded;
    }

    public Stock(String id, String name, String description, String photo, Company company, Date dateStart, Date dateFinish, boolean isAdded) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.photo = photo;
        this.description = description;
        this.company = company;
        this.isAdded = isAdded;
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
            this.photo = stockObj.has("logo") ? GlobalVariables.server + stockObj.getString("logo") : null;
            this.description =  stockObj.has("description") ? stockObj.getString("description") : defaultDescription;
            this.company = new Company(stockObj.get("company").toString());

            this.isAdded = stockObj.has("isAdded") && (boolean) stockObj.get("isAdded");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeString(this.id);
            dest.writeString(name);
            dest.writeLong(this.dateStart.getTime());
            dest.writeLong(this.dateFinish.getTime());
            dest.writeString(description);
            dest.writeByte((byte) (this.isAdded ? 1 : 0));
            dest.writeParcelable(this.company, flags);
            dest.writeString(this.photo);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Stock(Parcel source){
        this.id = source.readString();
        this.name = source.readString();
        this.dateStart = new Date(source.readLong());
        this.dateFinish = new Date(source.readLong());
        this.description = source.readString();
        this.isAdded = source.readByte() != 0;
        this.company = source.readParcelable(Company.class.getClassLoader());
        this.photo = source.readString();
    }


    public static final Parcelable.Creator<Stock> CREATOR = new Parcelable.Creator<Stock>() {

        @Override
        public Stock createFromParcel(Parcel source) {
            return new Stock(source);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };
}
