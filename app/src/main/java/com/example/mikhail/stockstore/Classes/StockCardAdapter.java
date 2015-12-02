package com.example.mikhail.stockstore.Classes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikhail.stockstore.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by mikhail on 02.12.15.
 */
public class StockCardAdapter extends RecyclerView.Adapter<StockCardAdapter.StocksViewHolder>{
    List<Stock> stocks;
    public StockCardAdapter(List<Stock> stocks){
        this.stocks = stocks;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public StocksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_card, parent, false);
        StocksViewHolder pvh = new StocksViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(StocksViewHolder holder, int position) {
        StocksViewHolder.stockName.setText(stocks.get(position).name);
        StocksViewHolder.stockDescription.setText(stocks.get(position).description);
        StocksViewHolder.stockPhoto.setImageResource(stocks.get(position).photoId);

        Locale ru = new Locale("ru");
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",ru);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM y",ru);

        StocksViewHolder.stockDate.setText(format.format(stocks.get(position).dateFinish));
        StocksViewHolder.companyName.setText(stocks.get(position).company.name);
        StocksViewHolder.companyLogo.setImageResource(stocks.get(position).company.photoId);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public static class StocksViewHolder extends RecyclerView.ViewHolder {
        public static CardView cv;
        public static TextView stockName;
        public static TextView stockDescription;
        public static ImageView stockPhoto;

        public static TextView companyName;
        public static ImageView companyLogo;
        public static TextView stockDate;

        StocksViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cards);

            stockName = (TextView)itemView.findViewById(R.id.stock_name);
            stockDescription = (TextView)itemView.findViewById(R.id.stock_description);
            stockPhoto = (ImageView)itemView.findViewById(R.id.stock_photo);

            companyName = (TextView)itemView.findViewById(R.id.company_name);
            companyLogo = (ImageView)itemView.findViewById(R.id.company_logo);
            stockDate = (TextView)itemView.findViewById(R.id.stock_date);
        }
    }
}
