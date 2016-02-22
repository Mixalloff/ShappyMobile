package com.example.mikhail.stockstore.Tabs;

import android.content.Context;
import android.content.Intent;
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

import com.example.mikhail.stockstore.Adapters.StockCardAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.AsyncClasses.OnTaskCompleted;
import com.example.mikhail.stockstore.Classes.IDifferentMode;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Adapters.CompanyCardAdapter;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.CompanyInfoActivity;
import com.example.mikhail.stockstore.Entities.Company;
import com.example.mikhail.stockstore.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 09.12.15.
 */
public class allCompaniesTab extends Fragment implements IDifferentMode {
    CompanyCardAdapter adapter;
    private List<Company> companies = new ArrayList<>();
    //RecyclerView rv;
    int countOfLoadingCompanies = 10;

    private String mode;

    RecyclerView recyclerView;

    private void requestCompanies(AsyncRequestToServer request){
        switch (this.mode){
            case "All":{
                request.setCallback( new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        handler.onUserGetAllCompanies(result);
                    }
                });
                request.execute(APIConstants.GET_ALL_COMPANIES);
                break;
            }
            case "Subscriptions": {
                request.setCallback( new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(JSONObject result) {
                        handler.onUserGetSubscribedCompanies(result);
                    }
                });
                request.execute(APIConstants.GET_SUBSCRIBED_COMPANIES);
                break;
            }
            default:{}
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_companies_tab, container, false);

        // Установить режим
        this.setMode(((IDifferentMode) getActivity()).getMode());

        recyclerView = (RecyclerView) v.findViewById(R.id.companies_recyclerView);
        initRecyclerView(container);
        initSwipe(v);

        AsyncRequestToServer request = new AsyncRequestToServer(getActivity());
        this.requestCompanies(request);

        return v;
    }

    // Инициализация RecyclerView карточек
    public void initRecyclerView(ViewGroup container){
        Context context = this.getActivity();
        GridLayoutManager llm = new GridLayoutManager(context, 3);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new CompanyCardAdapter(companies, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initializeTestData() {
        String adidas = "http://allstarshoe.ru/wa-data/public/shop/img/Adidas_originals_logo.jpg";
        String nike = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        companies.add(new Company("1","Adidas", adidas));
        companies.add(new Company("1","NIKE", nike));
        companies.add(new Company("1","Adidas", adidas));
    }

    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onUserGetAllCompanies(JSONObject response) {
            refreshCompaniesFromResponse(response);
        }

        @Override
        public void onUserGetSubscribedCompanies(JSONObject response) {
            refreshCompaniesFromResponse(response);
        }
    };

    private void refreshCompaniesFromResponse(JSONObject response){
        try {
            // Обновляем список акций
            companies.clear();

            JSONArray data = new JSONArray(response.get("data").toString());
            int count = data.length() > countOfLoadingCompanies ? countOfLoadingCompanies : data.length();
            for (int i = 0; i < count; i++){
                JSONObject company = new JSONObject(data.get(i).toString());
                companies.add(new Company(company));
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public String getMode() {
        return this.mode;
    }

    @Override
    public void setMode(String mode) {
        this.mode = ((IDifferentMode)getActivity()).getMode();
    }
}
