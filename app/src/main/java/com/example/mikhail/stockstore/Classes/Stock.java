package com.example.mikhail.stockstore.Classes;

import com.example.mikhail.stockstore.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mikhail on 02.12.15.
 */
public class Stock {
    String name;
    Date dateStart;
    Date dateFinish;
    int photoId;
    String description;
    Company company;

    public Stock(String name, String description, int photoId, Company company){
        this.name = name;
        this.dateStart = new Date();
        this.dateFinish = new Date();
        this.photoId = photoId;
        this.description = description;
        this.company = company;
    }

    public Stock(String name, String description, int photoId, Company company, Date dateStart, Date dateFinish) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.photoId = photoId;
        this.description = description;
        this.company = company;
    }
}
