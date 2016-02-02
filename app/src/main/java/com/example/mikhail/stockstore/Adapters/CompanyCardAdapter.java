package com.example.mikhail.stockstore.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by mikhail on 15.12.15.
 */
public class CompanyCardAdapter extends BaseAdapter{
    List<Company> companies;
    Context mContext;

    public CompanyCardAdapter(List<Company> companies, Context mContext){
        this.companies = companies;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Object getItem(int position) {
        return companies.get(position);
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
            if (companies != null) {
                TextView companyName = (TextView) grid.findViewById(R.id.company_name);
                ImageView companyLogo = (ImageView)grid.findViewById(R.id.company_logo);
                ImageLoader.getInstance().displayImage(companies.get(position).photo, companyLogo);
               // CommonFunctions.setPhotoToImageView(companies.get(position).photo, companyLogo);
                companyName.setText(companies.get(position).name);
            }

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}

