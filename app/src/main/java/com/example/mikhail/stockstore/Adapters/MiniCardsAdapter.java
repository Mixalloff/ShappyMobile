package com.example.mikhail.stockstore.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.Constants.ElementGroupSpecies;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.Search.SearchActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mikhail on 07.02.16.
 */
public class MiniCardsAdapter extends RecyclerView.Adapter<MiniCardsAdapter.MiniCardViewHolder>{

    List<JSONObject> elements;
    int orientation;

    public MiniCardsAdapter(List<JSONObject> elements){
        this(elements, LinearLayoutManager.HORIZONTAL);
    }

    public MiniCardsAdapter(List<JSONObject> elements, int orientation){
        this.elements = elements;
        this.orientation = orientation;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MiniCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(this.orientation == LinearLayoutManager.HORIZONTAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_card, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card, parent, false);
        }
        return new MiniCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MiniCardViewHolder holder, int position) {
        try {
            if(elements.get(position).has("name")) {
                holder.card_name.setText(elements.get(position).getString("name"));
            }
            if (elements.get(position).has("photo")) {
                ImageLoader.getInstance().displayImage(elements.get(position).getString("photo"), holder.card_photo);
            }
            holder.id = elements.get(position).has("id") ? elements.get(position).getString("id") : "";
            holder.type = elements.get(position).has("id") ? elements.get(position).getString("type") : "";

            //holder.card.getLayoutParams().height = 150;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public static class MiniCardViewHolder extends RecyclerView.ViewHolder{
        public CardView card;
        public TextView card_name;
        public ImageView card_photo;
        public ImageButton card_menu;

        public String id;
        public String type;

        MiniCardViewHolder(final View itemView){
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.card);
            card_name = (TextView)itemView.findViewById(R.id.card_name);
            card_photo = (ImageView)itemView.findViewById(R.id.card_photo);
            card_menu = (ImageButton)itemView.findViewById(R.id.card_menu);
        }
    }
}


