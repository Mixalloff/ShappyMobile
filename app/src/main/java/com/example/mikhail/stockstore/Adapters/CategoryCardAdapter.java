package com.example.mikhail.stockstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.R;

import java.util.List;

/**
 * Created by mikhail on 16.12.15.
 */
public class CategoryCardAdapter extends BaseAdapter {
    List<Company> categories;
    Context mContext;

    public CategoryCardAdapter(List<Company> categories, Context mContext){
        this.categories = categories;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.company_card, parent, false);
            if (categories != null) {
                TextView categoryName = (TextView) grid.findViewById(R.id.category_name);
                ImageView categoryLogo = (ImageView)grid.findViewById(R.id.category_logo);
                CommonFunctions.setPhotoToImageView(categories.get(position).photo, categoryLogo);
                categoryName.setText(categories.get(position).name);
            }

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}