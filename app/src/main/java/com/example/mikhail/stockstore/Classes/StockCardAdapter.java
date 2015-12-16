package com.example.mikhail.stockstore.Classes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StocksActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
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
    public void onBindViewHolder(StocksViewHolder holder, final int position) {
        StocksViewHolder.stockName.setText(stocks.get(position).name);
        StocksViewHolder.stockDescription.setText(stocks.get(position).description);

        CommonFunctions.setPhotoToImageView(stocks.get(position).photo, StocksViewHolder.stockPhoto);

        Locale ru = new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM y",ru);

        StocksViewHolder.stockDate.setText(format.format(stocks.get(position).dateFinish));
        StocksViewHolder.companyName.setText(stocks.get(position).company.name);

        CommonFunctions.setPhotoToImageView(stocks.get(position).company.photo, StocksViewHolder.companyLogo);

        StocksViewHolder.addStockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                //Toast.makeText(v.getContext().getApplicationContext(), stocks.get(position).id, Toast.LENGTH_SHORT).show();
                Activity host = (Activity) v.getContext();
               // APIRequestConstructor.userAddStock(host, stocks.get(position).id);

                ResponseInterface handler;

                handler = new ResponseInterface() {
                    @Override
                    public void onInternalServerError(JSONObject response) {

                    }

                    @Override
                    public void onUnknownRequestUri(JSONObject response) {
                        Toast.makeText(v.getContext().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(JSONObject response) {

                    }

                    @Override
                    public void onRegister(JSONObject response) {

                    }

                    @Override
                    public void onGetToken(JSONObject response) {

                    }

                    @Override
                    public void onUserGetAllStocks(JSONObject response) {

                    }

                    @Override
                    public void onUserGetAllCompanies(JSONObject response) {

                    }

                    @Override
                    public void onUserAddStock(JSONObject response) {

                    }
                };

                try {
                    ServerResponseHandler.CheckResponse(APIRequestConstructor.userAddStock(host, stocks.get(position).id), handler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


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

        public static ImageButton addStockBtn;

        StocksViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cards);

            stockName = (TextView)itemView.findViewById(R.id.stock_name);
            stockDescription = (TextView)itemView.findViewById(R.id.stock_description);
            stockPhoto = (ImageView)itemView.findViewById(R.id.stock_photo);

            companyName = (TextView)itemView.findViewById(R.id.company_name);
            companyLogo = (ImageView)itemView.findViewById(R.id.company_logo);
            stockDate = (TextView)itemView.findViewById(R.id.stock_date);

            addStockBtn = (ImageButton)itemView.findViewById(R.id.add_stock_btn);
        }
    }
}
