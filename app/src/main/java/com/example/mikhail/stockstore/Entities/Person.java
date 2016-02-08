package com.example.mikhail.stockstore.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.GlobalVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by mikhail on 18.01.16.
 */
public class Person implements Parcelable {
    public String name;
    public String surname;
    public String photo;
    public String id;
    public boolean isFriend;

    //public boolean isAdded;

    public Person(String name, String surname, String photo, String id, boolean isFriend){
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.id = id;
        this.isFriend = isFriend;
    }

    public Person(JSONObject personObj){
        String defaultName = "default name";
        String defaultSurname = "default surname";

        try {
            this.id = personObj.has("id") ? personObj.getString("id") : "0";
            this.name = personObj.has("name") ? personObj.getString("name") : defaultName;
            this.surname = personObj.has("surname") ? personObj.getString("surname") : defaultSurname;
            this.photo = personObj.has("logo") ? GlobalVariables.server + personObj.getString("logo") : null;

            //this.isAdded = personObj.has("subscribed") && (boolean) personObj.get("subscribed");
            this.isFriend = personObj.has("isFriend") && (boolean) personObj.get("isFriend");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Person(Parcel in) {
        name = in.readString();
        surname = in.readString();
        photo = in.readString();
        id = in.readString();
        isFriend = in.readByte() != 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(photo);
        dest.writeString(id);
        dest.writeByte((byte) (this.isFriend ? 1 : 0));
    }
}
