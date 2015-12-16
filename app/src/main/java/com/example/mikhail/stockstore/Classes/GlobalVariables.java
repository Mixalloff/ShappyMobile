package com.example.mikhail.stockstore.Classes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

/**
 * Created by mikhail on 10.12.15.
 */
public class GlobalVariables {
    public static Bitmap defaultPhoto;

    public static String server = "http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080";

    public static void setDefaultPhoto(int resID, Resources resources){
        defaultPhoto = CommonFunctions.getPhoto(resID, resources);
    }
}
