package com.example.mikhail.stockstore.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.CompanyInfoActivity;
import com.example.mikhail.stockstore.Entities.Category;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by mikhail on 16.12.15.
 */
public class CategoryCardAdapter extends RecyclerView.Adapter<CategoryCardAdapter.CategoryViewHolder>{

    List<Category> categories;
    Activity activity;

    public CategoryCardAdapter(List<Category> categories, Activity activity){
        this.categories = categories;
        this.activity = activity;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.categoryName.setText(categories.get(position).name);
        ImageLoader.getInstance().displayImage(categories.get(position).photo, holder.categoryLogo);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(activity, CompanyInfoActivity.class);
                intent.putExtra("company", categories.get(position));
                activity.startActivity(intent);*/

                // Обработка клика по категории
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public boolean isAdded;

        public TextView categoryName;
        public ImageView categoryLogo;
        public RelativeLayout layout;

        CategoryViewHolder(final View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            categoryLogo = (ImageView) itemView.findViewById(R.id.category_logo);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_categoryCard);
            isAdded = false;
        }
    }
}