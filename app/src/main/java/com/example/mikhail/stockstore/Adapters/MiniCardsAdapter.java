package com.example.mikhail.stockstore.Adapters;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mikhail on 07.02.16.
 */
public class MiniCardsAdapter extends RecyclerView.Adapter<MiniCardsAdapter.MiniCardViewHolder>{

    List<JSONObject> elements;

    public MiniCardsAdapter(List<JSONObject> elements){
        this.elements = elements;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MiniCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_card, parent, false);
        return new MiniCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MiniCardViewHolder holder, int position) {
        try {
            holder.card_name.setText(elements.get(position).getString("name"));
            ImageLoader.getInstance().displayImage(elements.get(position).getString("photo"), holder.card_photo);
            holder.id = elements.get(position).getString("id");
            holder.type = elements.get(position).getString("type");
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


