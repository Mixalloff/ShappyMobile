package com.example.mikhail.stockstore.Tabs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.CompanyCardAdapter;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StartActivity;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 09.12.15.
 */
public class allCompaniesTab extends Fragment {
    CompanyCardAdapter adapter;
    private List<Company> companies = new ArrayList<>();
    RecyclerView rv;
    int countOfLoadingCompanies = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_companies_tab, container, false);

        rv = (RecyclerView) v.findViewById(R.id.cards);
        initGridView(container,v);
        initializeTestData();

        // Отправляем запрос на получение всех акций
        try {
            ServerResponseHandler.CheckResponse(APIRequestConstructor.getAllCompanies(getActivity()), handler);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return v;
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
            Toast.makeText(getContext().getApplicationContext(), companies.get(position).name, Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(getContext(),StartActivity.class));
        }
    };

    // Инициализация GridView
    public void initGridView(ViewGroup container,View v) {
        GridView gridview = (GridView) v.findViewById(R.id.gridView);
        gridview.setAdapter(new CompanyCardAdapter(companies, this.getContext()));
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private void initializeTestData() {
        String adidas = "http://allstarshoe.ru/wa-data/public/shop/img/Adidas_originals_logo.jpg";
        String nike = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        companies.add(new Company("Adidas", adidas));
        companies.add(new Company("NIKE", nike));
        //companies.add(new Company("NIKE", nike));
        //companies.add(new Company("NIKE", nike));
        companies.add(new Company("Adidas", adidas));
    }

    private ResponseInterface handler = new ResponseInterface() {
        @Override
        public void onInternalServerError(JSONObject response) {

        }

        @Override
        public void onUnknownRequestUri(JSONObject response) {

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
            try {
                // Обновляем список акций
                companies.clear();

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingCompanies ? countOfLoadingCompanies : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject stock = new JSONObject(data.get(i).toString());
                    companies.add(new Company(stock));
                }
                //rv.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
