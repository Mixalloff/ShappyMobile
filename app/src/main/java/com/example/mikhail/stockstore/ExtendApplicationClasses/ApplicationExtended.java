package com.example.mikhail.stockstore.ExtendApplicationClasses;

import android.app.Application;
import android.content.res.Configuration;

import com.example.mikhail.stockstore.Classes.GlobalVariables;
import com.example.mikhail.stockstore.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

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

        // Инициализация элементов для работы с картинками
        File cacheDir = StorageUtils.getCacheDirectory(this);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_photo)
                .showImageForEmptyUri(R.drawable.default_photo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);


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
