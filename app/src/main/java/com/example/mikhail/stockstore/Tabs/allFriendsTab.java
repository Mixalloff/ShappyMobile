package com.example.mikhail.stockstore.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Classes.PersonCardAdapter;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.StockCardAdapter;
import com.example.mikhail.stockstore.Entities.Person;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 17.01.16.
 */
public class allFriendsTab extends Fragment {

    List<Person> persons= new ArrayList<>();
    PersonCardAdapter adapter;
    RecyclerView rv;
    int countOfLoadingPersons = 5;
    AsyncRequestToServer request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        persons = new ArrayList<>();
        initializeTestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_stocks_tab, container, false);

        rv = (RecyclerView) v.findViewById(R.id.cards);
        initRecyclerView(container);
        initSwipe(v);

        //RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        //rv.setItemAnimator(itemAnimator);

        //initializeTestData(v);

        try{
            Intent intent = getActivity().getIntent();
            JSONObject stocksJson = new JSONObject(intent.getStringExtra("stocks"));
            handler.onUserGetAllStocks(stocksJson);
        }catch(Exception e){
            e.printStackTrace();
            request = new AsyncRequestToServer(getActivity(), handler);
            request.execute(APIConstants.GET_ALL_STOCKS);
        }

        return v;

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
              //  request = new AsyncRequestToServer(getActivity(), handler);
                request.setSwipeRefresh(swipe);
                request.execute(APIConstants.GET_ALL_STOCKS);
            }
        });
    }

    private ResponseInterface handler = new ResponseInterface() {

    };

    // Инициализация RecyclerView карточек
    public void initRecyclerView(ViewGroup container){
        Context context = this.getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);
        adapter = new PersonCardAdapter(persons);
        rv.setAdapter(adapter);
    }

    public void initializeTestData(){
        persons.add(new Person("Ivan", "Ivanov", "asds", "sadsada"));
        persons.add(new Person("Petr", "Petrov", "asds", "sadsada"));
        persons.add(new Person("Mikhail", "Mikhalev", "asds", "sadsada"));
    }
}
