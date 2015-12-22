package com.example.mikhail.stockstore;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;

import com.example.mikhail.stockstore.Classes.GlobalVariables;

/**
 * Created by mikhail on 23.12.15.
 */
public class ApplicationExtended extends Application {

    private static ApplicationExtended singleton;

    public ApplicationExtended getInstance(){
        return singleton;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        GlobalVariables.setDefaultPhoto(R.drawable.default_photo, getResources());
        
        //startActivity(new Intent(this.getBaseContext(), ProfileActivity.class));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
