package com.example.mikhail.stockstore.Tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Adapters.CategoryCardAdapter;
import com.example.mikhail.stockstore.Adapters.CompanyCardAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.AsyncClasses.OnTaskCompleted;
import com.example.mikhail.stockstore.Classes.IDifferentMode;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Entities.Category;
import com.example.mikhail.stockstore.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 09.12.15.
 */
public class allCategoriesTab extends Fragment implements IDifferentMode {
    CategoryCardAdapter adapter;
    private List<Category> categories = new ArrayList<>();
    int countOfLoadingCategories = 10;

    RecyclerView recyclerView;

    String mode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_categories_tab, container, false);

        // Установить режим
        this.setMode(((IDifferentMode) getActivity()).getMode());
        initSwipe(v);

        recyclerView = (RecyclerView) v.findViewById(R.id.categories_recyclerView);
        //initGridView(container,v);
        initRecyclerView(container);
        AsyncRequestToServer request = new AsyncRequestToServer(getActivity());
        requestCompanies(request);
       // request.execute(APIConstants.GET_ALL_CATEGORIES);

        return v;
    }

    private void requestCompanies(AsyncRequestToServer request){
        switch (this.mode){
            case "All":{
                request.setCallback( new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        handler.onUserGetAllCategories(result);
                    }
                });
                request.execute(APIConstants.GET_ALL_CATEGORIES);
                break;
            }
            case "Subscriptions": {
                request.setCallback( new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        handler.onUserGetSubscribedCategories(result);
                    }
                });
                request.execute(APIConstants.GET_SUBSCRIBED_CATEGORIES);
                break;
            }
            default:{}
        }
    }

    // Инициализация SwipeLayout
    public void initSwipe(View v){
        // Обновление при скролле вниз
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncRequestToServer request = new AsyncRequestToServer(getActivity());
                request.setSwipeRefresh(swipe);
                requestCompanies(request);
                //request.execute(APIConstants.GET_ALL_STOCKS);
            }
        });
    }

    // Инициализация GridView
    public void initGridView(ViewGroup container,View v) {
      /*  GridView gridview = (GridView) v.findViewById(R.id.category_gridView);
        gridview.setAdapter(new CategoryCardAdapter(categories, this.getContext()));
        gridview.setOnItemClickListener(gridviewOnItemClickListener);*/
    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(ViewGroup container){
        Context context = this.getActivity();
        GridLayoutManager llm = new GridLayoutManager(context, 3);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new CategoryCardAdapter(categories, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initializeTestData() {
        String sport = "http://icon-icons.com/icons2/38/PNG/512/dumbbell_sport_5072.png";
        String shoes = "http://icon-icons.com/icons2/28/PNG/256/shoes_adidas_2734.png";
        String products = "http://icon-icons.com/icons2/37/PNG/128/bread_food_3206.png";
        categories.add(new Category("1","Спорт", sport));
        categories.add(new Category("1","Обувь", shoes));
        categories.add(new Category("1","Продукты", products));
    }

    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onUserGetAllCategories(JSONObject response) {
            refreshCategoriesFromResponse(response);
        }

        @Override
        public void onUserGetSubscribedCategories(JSONObject response) {
            refreshCategoriesFromResponse(response);
        }
    };

    private void refreshCategoriesFromResponse(JSONObject response){
        try {
            // Обновляем список акций
            categories.clear();

            JSONArray data = new JSONArray(response.get("data").toString());
            int count = data.length() > countOfLoadingCategories ? countOfLoadingCategories : data.length();
            for (int i = 0; i < count; i++){
                JSONObject category = new JSONObject(data.get(i).toString());
                categories.add(new Category(category));
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMode() {
        return this.mode;
    }

    @Override
    public void setMode(String mode) {
        this.mode = ((IDifferentMode)getActivity()).getMode();
    }
}

