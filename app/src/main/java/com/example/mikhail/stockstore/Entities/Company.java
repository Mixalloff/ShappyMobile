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
    public String photo;
    public String id;
    private String defaultName = "default name";

    public Company(String id, String name, String photo){
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public Company(){
        this.id = null;
        this.name = defaultName;
        this.photo = null;
    }

    public Company(JSONObject companyObj){
        try {
            this.id = companyObj.has("id") ? companyObj.getString("id") : null;
            this.name = companyObj.has("name") ? companyObj.getString("name") : defaultName;
            this.photo = companyObj.has("logo") ? GlobalVariables.server + companyObj.getString("logo") : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Company(String str){
        if (str.equals("{}")){
            this.id = null;
            this.name = defaultName;
            this.photo = null;
        }
        else{
            try {
                JSONObject json = new JSONObject(str);
                this.id = json.has("id") ? json.getString("id") : null;
                this.name = json.has("name") ? json.getString("name") : defaultName;
                this.photo = json.has("logo") ? GlobalVariables.server + json.getString("logo") : null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected Company(Parcel in) {
        id = in.readString();
        name = in.readString();
        photo = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(photo);
    }
}
