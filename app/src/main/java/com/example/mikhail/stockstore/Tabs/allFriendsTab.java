package com.example.mikhail.stockstore.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mikhail.stockstore.Adapters.HorizontalListAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Adapters.PersonCardAdapter;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Entities.Person;
import com.example.mikhail.stockstore.Entities.Stock;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.SearchActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 17.01.16.
 */
public class allFriendsTab extends Fragment {

    ArrayList<Person> persons= new ArrayList<>();
    //PersonCardAdapter adapter;
    //RecyclerView rv;
    HorizontalListAdapter adapter;
    ListView lv;
    int countOfLoadingPersons = 5;
    AsyncRequestToServer request;
    FloatingActionButton friendSearchsBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        persons = new ArrayList<>();
       // initializeTestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_friends_tab, container, false);

        //rv = (RecyclerView) v.findViewById(R.id.cards);

        lv = (ListView) v.findViewById(R.id.friends_list);
        initListView(container);
        initSwipe(v);

        friendSearchsBtn = (FloatingActionButton)v.findViewById(R.id.search_friends_btn);
        friendSearchsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SearchActivity.class));
            }
        });

        request = new AsyncRequestToServer(getActivity(), handler);
        request.execute(APIConstants.USER_GET_ALL_FRIENDS);
        //request.setParameters("");
       // request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
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
                request = new AsyncRequestToServer(getActivity(), handler);
                request.setSwipeRefresh(swipe);
                request.execute(APIConstants.USER_GET_ALL_FRIENDS);
            }
        });
    }

    private ServerResponseHandler handler = new ServerResponseHandler() {

        @Override
        public void onUserGetAllFriends(JSONObject response) {
           // super.onUserGetAllFriends(response);
            try {
                persons.clear();
                // adapter.notifyItemRemoved(0);

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingPersons ? countOfLoadingPersons : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject person = new JSONObject(data.get(i).toString());
                    persons.add(new Person(person));
                    // adapter.notifyItemInserted(i);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUserGetFriendsFilter(JSONObject response) {
            try {
                persons.clear();
                // adapter.notifyItemRemoved(0);

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingPersons ? countOfLoadingPersons : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject person = new JSONObject(data.get(i).toString());
                    persons.add(new Person(person));
                    // adapter.notifyItemInserted(i);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // Инициализация RecyclerView карточек
    public void initListView(ViewGroup container){
        Context context = this.getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //rv.setLayoutManager(llm);
        /*adapter = new PersonCardAdapter(persons);
        rv.setAdapter(adapter);*/

        adapter = new HorizontalListAdapter(this.getContext(), persons);
        // настраиваем список
       // ListView friendsList = (ListView) this.getActivity().findViewById(R.id.friends_list);
        lv.setAdapter(adapter);
    }

    public void initializeTestData(){
        persons.add(new Person("Ivan", "Ivanov", "asds", "sadsada"));
        persons.add(new Person("Petr", "Petrov", "asds", "sadsada"));
        persons.add(new Person("Mikhail", "Mikhalev", "asds", "sadsada"));
    }
}
