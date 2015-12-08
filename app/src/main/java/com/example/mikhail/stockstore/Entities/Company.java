package com.example.mikhail.stockstore.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 02.12.15.
 */
public class Company {
    public String name;
    public String photo;

    private String defaultName = "default name";
    private String defaultPhoto = "http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg";

    public Company(String name, String photo){
        this.name = name;
        this.photo = photo;
    }

    public Company(){
        this.name = defaultName;
        this.photo = defaultPhoto;
    }

    public Company(String str){
        if (str.equals("{}")){
            new Company();
        }
        else{
            try {
                JSONObject json = new JSONObject(str);
                this.name = json.has("name") ? json.getString("name") : defaultName;
                this.photo = json.has("photo") ? json.getString("photo") : defaultPhoto;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
