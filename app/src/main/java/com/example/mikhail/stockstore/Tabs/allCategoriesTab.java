package com.example.mikhail.stockstore.Tabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikhail.stockstore.R;

/**
 * Created by mikhail on 09.12.15.
 */
public class allCategoriesTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.all_categories_tab,container,false);
        return v;
    }
}
