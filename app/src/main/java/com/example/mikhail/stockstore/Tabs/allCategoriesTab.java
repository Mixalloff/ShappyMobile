package com.example.mikhail.stockstore.Tabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.CategoryCardAdapter;
import com.example.mikhail.stockstore.Classes.CompanyCardAdapter;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 09.12.15.
 */
public class allCategoriesTab extends Fragment {
    CategoryCardAdapter adapter;
    private List<Company> categories = new ArrayList<>();
    int countOfLoadingCategories = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_categories_tab, container, false);

        initGridView(container,v);
        initializeTestData();

        // Отправляем запрос на получение всех акций
       /* try {
            ServerResponseHandler.CheckResponse(APIRequestConstructor.getAllCategories(getActivity()), handler);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }*/

        return v;
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
            Toast.makeText(getContext().getApplicationContext(), categories.get(position).name, Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(getContext(),StartActivity.class));
        }
    };

    // Инициализация GridView
    public void initGridView(ViewGroup container,View v) {
        GridView gridview = (GridView) v.findViewById(R.id.category_gridView);
        gridview.setAdapter(new CompanyCardAdapter(categories, this.getContext()));
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private void initializeTestData() {
        String sport = "http://icon-icons.com/icons2/38/PNG/512/dumbbell_sport_5072.png";
        String shoes = "http://icon-icons.com/icons2/28/PNG/256/shoes_adidas_2734.png";
        String products = "http://icon-icons.com/icons2/37/PNG/128/bread_food_3206.png";
        categories.add(new Company("Спорт", sport));
        categories.add(new Company("Обувь", shoes));
        categories.add(new Company("Продукты", products));
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

        }
    };
}

