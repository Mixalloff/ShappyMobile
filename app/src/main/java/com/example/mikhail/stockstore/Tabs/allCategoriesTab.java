package com.example.mikhail.stockstore.Tabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Adapters.CategoryCardAdapter;
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
public class allCategoriesTab extends Fragment{
    CategoryCardAdapter adapter;
    private List<Category> categories = new ArrayList<>();
    int countOfLoadingCategories = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_categories_tab, container, false);

        initGridView(container,v);
        //initializeTestData();
        AsyncRequestToServer request = new AsyncRequestToServer(getActivity(),  new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(JSONObject result) {
                handler.onUserGetAllCategories(result);
            }
        });
        request.execute(APIConstants.GET_ALL_CATEGORIES);

        return v;
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
            Toast.makeText(getContext().getApplicationContext(), categories.get(position).name, Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(getContext(),LoginActivity.class));
        }
    };

    // Инициализация GridView
    public void initGridView(ViewGroup container,View v) {
        GridView gridview = (GridView) v.findViewById(R.id.category_gridView);
        gridview.setAdapter(new CategoryCardAdapter(categories, this.getContext()));
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
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
    };
}

