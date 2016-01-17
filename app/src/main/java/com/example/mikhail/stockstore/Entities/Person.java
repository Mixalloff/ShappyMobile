package com.example.mikhail.stockstore.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mikhail on 18.01.16.
 */
public class Person implements Parcelable {
    public String name;
    public String surname;
    public String photo;
    public String id;

    public boolean isAdded;

    public Person(String name, String surname, String photo, String id){
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.id = id;
    }

    protected Person(Parcel in) {
        name = in.readString();
        surname = in.readString();
        photo = in.readString();
        id = in.readString();
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
    }
}
