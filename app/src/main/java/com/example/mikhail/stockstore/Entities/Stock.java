package com.example.mikhail.stockstore.Entities;

import java.util.Date;

/**
 * Created by mikhail on 02.12.15.
 */
public class Stock {
    public String name;
    public Date dateStart;
    public Date dateFinish;
    public int photoId;
    public String description;
    public Company company;

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
