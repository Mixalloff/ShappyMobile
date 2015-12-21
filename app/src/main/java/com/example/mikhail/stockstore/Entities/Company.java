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

    protected Company(Parcel in) {
        name = in.readString();
        //photo = in.readParcelable(Bitmap.class.getClassLoader());
        defaultName = in.readString();

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readByteArray(bytes);
        this.photo = CommonFunctions.uncompressedImage(bytes);
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
        dest.writeString(name);
        //dest.writeParcelable(photo, flags);
        dest.writeString(defaultName);

        byte[] photoBytes = CommonFunctions.compressImage(this.photo);
        dest.writeInt(photoBytes.length);
        dest.writeByteArray(photoBytes);
    }
}
