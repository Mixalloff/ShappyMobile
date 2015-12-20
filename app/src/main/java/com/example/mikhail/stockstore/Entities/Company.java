package com.example.mikhail.stockstore.Entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 02.12.15.
 */
public class Company implements Parcelable{
    public String name;
    public Bitmap photo;

    private String defaultName = "default name";

    public Company(String name, String photo){
        this.name = name;
        this.photo = CommonFunctions.getPhoto(photo);
    }

    public Company(){
        this.name = defaultName;
        this.photo = GlobalVariables.defaultPhoto;
    }

    public Company(JSONObject companyObj){
        try {
            this.name = companyObj.has("name") ? companyObj.getString("name") : defaultName;
            this.photo = companyObj.has("logo") ? CommonFunctions.getPhoto(GlobalVariables.server + companyObj.getString("logo")) : GlobalVariables.defaultPhoto;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Company(String str){
        if (str.equals("{}")){
            this.name = defaultName;
            this.photo = GlobalVariables.defaultPhoto;
        }
        else{
            try {
                JSONObject json = new JSONObject(str);
                this.name = json.has("name") ? json.getString("name") : defaultName;
                this.photo = json.has("logo") ? CommonFunctions.getPhoto(GlobalVariables.server + json.getString("logo")) : GlobalVariables.defaultPhoto;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
