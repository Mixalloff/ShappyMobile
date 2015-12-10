package com.example.mikhail.stockstore.Entities;

import android.graphics.Bitmap;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 02.12.15.
 */
public class Company {
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

    public Company(String str){
        if (str.equals("{}")){
            this.name = defaultName;
            this.photo = GlobalVariables.defaultPhoto;
        }
        else{
            try {
                JSONObject json = new JSONObject(str);
                this.name = json.has("name") ? json.getString("name") : defaultName;
                this.photo = json.has("photo") ? CommonFunctions.getPhoto(json.getString("photo")) : GlobalVariables.defaultPhoto;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
