package com.example.mikhail.stockstore.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.RootElement;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.CompanyInfoActivity;
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
public class CompanyCardAdapter extends RecyclerView.Adapter<CompanyCardAdapter.CompanyViewHolder>{

    List<Company> companies;
    Activity activity;

    public CompanyCardAdapter(List<Company> companies, Activity activity){
        this.companies = companies;
        this.activity = activity;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_card, parent, false);
        return new CompanyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, final int position) {
        holder.companyName.setText(companies.get(position).name);
        ImageLoader.getInstance().displayImage(companies.get(position).photo, holder.companyLogo);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CompanyInfoActivity.class);
                intent.putExtra("company", companies.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {

        public boolean isAdded;

        public TextView companyName;
        public ImageView companyLogo;
        public RelativeLayout layout;

        CompanyViewHolder(final View itemView) {
            super(itemView);
            companyName = (TextView) itemView.findViewById(R.id.company_name);
            companyLogo = (ImageView) itemView.findViewById(R.id.company_logo);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_companyCard);
            isAdded = false;
        }
    }
}

