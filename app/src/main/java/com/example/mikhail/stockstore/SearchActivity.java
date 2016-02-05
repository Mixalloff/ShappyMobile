package com.example.mikhail.stockstore;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.mikhail.stockstore.Adapters.HorizontalListAdapter;
import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.example.mikhail.stockstore.Classes.CommonFunctions;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Constants.CommonConstants;
import com.example.mikhail.stockstore.Entities.Person;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Person> search_list_friends = new ArrayList<>();
    HorizontalListAdapter adapter;
    ListView lv;
    int countOfLoadingPersons = 10;
    RelativeLayout searchLayout;
    Toolbar toolbar;

    MenuItem searchMenuItem;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Activity self = this;
        toolbar = CommonFunctions.setToolbar(this, R.id.include, CommonConstants.TOOLBAR_NAV_BACK);
       // toolbar.setNavigationIcon(com.mikepenz.materialdrawer.R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        search_list_friends = new ArrayList<>();
      //  View v = inflater.inflate(R.layout.all_friends_tab, container, false);
        searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        lv = (ListView) findViewById(R.id.search_friends_list);
        initListView();
        initSwipe(searchLayout);

        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        //request.execute(APIConstants.USER_GET_ALL_FRIENDS);
        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
    }

    // Инициализация RecyclerView карточек
    public void initListView(){
        Context context = this;
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //rv.setLayoutManager(llm);
        /*adapter = new PersonCardAdapter(persons);
        rv.setAdapter(adapter);*/

        adapter = new HorizontalListAdapter(this, search_list_friends);
        // настраиваем список
        // ListView friendsList = (ListView) this.getActivity().findViewById(R.id.friends_list);
        lv.setAdapter(adapter);
    }

    // Инициализация SwipeLayout
    public void initSwipe(View v){
        // Обновление при скролле вниз

            final SwipeRefreshLayout swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        final Activity self = this;
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //  request = new AsyncRequestToServer(getActivity(), handler);
                    try {
                        AsyncRequestToServer request = new AsyncRequestToServer(self, handler);
                        request.setSwipeRefresh(swipe);
                        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

    }

    private ServerResponseHandler handler = new ServerResponseHandler() {

        @Override
        public void onUserGetAllFriends(JSONObject response) {
            // super.onUserGetAllFriends(response);
            try {
                search_list_friends.clear();
                // adapter.notifyItemRemoved(0);

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingPersons ? countOfLoadingPersons : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject person = new JSONObject(data.get(i).toString());
                    search_list_friends.add(new Person(person));
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
                search_list_friends.clear();
                // adapter.notifyItemRemoved(0);

                JSONArray data = new JSONArray(response.get("data").toString());
                int count = data.length() > countOfLoadingPersons ? countOfLoadingPersons : data.length();
                for (int i = 0; i < count; i++){
                    JSONObject person = new JSONObject(data.get(i).toString());
                    search_list_friends.add(new Person(person));
                    // adapter.notifyItemInserted(i);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        try {
            searchView = (SearchView) searchMenuItem.getActionView();
            searchView.setOnQueryTextListener(this);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("FIO=" + query);
        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        AsyncRequestToServer request = new AsyncRequestToServer(this, handler);
        request.setParameters("FIO=" + newText);
        request.execute(APIConstants.USER_GET_FRIENDS_FILTER);
        return false;
    }
}
