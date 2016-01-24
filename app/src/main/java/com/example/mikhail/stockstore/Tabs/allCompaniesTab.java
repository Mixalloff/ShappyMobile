package com.example.mikhail.stockstore.Tabs;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Adapters.CompanyCardAdapter;
import com.example.mikhail.stockstore.Classes.ResponseInterface;
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
public class allCompaniesTab extends Fragment {
    CompanyCardAdapter adapter;
    private List<Company> companies = new ArrayList<>();
    //RecyclerView rv;
    int countOfLoadingCompanies = 10;
    AsyncRequestToServer request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

       // request = new AsyncRequestToServer(getActivity(), handler);
       // request.execute(APIConstants.GET_ALL_COMPANIES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_companies_tab, container, false);

        //rv = (RecyclerView) v.findViewById(R.id.cards);
        initGridView(container, v);
     //   initializeTestData();

        AsyncRequestToServer request = new AsyncRequestToServer(getActivity(), handler);
        request.execute(APIConstants.GET_ALL_COMPANIES);

        return v;
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
            Toast.makeText(getContext().getApplicationContext(), companies.get(position).name, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getContext(), CompanyInfoActivity.class);
            intent.putExtra("company", companies.get(position));
            startActivity(intent);
        }
    };

    // Инициализация GridView
    public void initGridView(ViewGroup container,View v) {
        GridView gridview = (GridView) v.findViewById(R.id.companies_gridView);
        this.adapter = new CompanyCardAdapter(companies, this.getContext());
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private void initializeTestData() {
        String adidas = "http://allstarshoe.ru/wa-data/public/shop/img/Adidas_originals_logo.jpg";
        String nike = "https://img.grouponcdn.com/coupons/gMH7PGJwA4KdS3teZNvpXD/nike-highres-500x500";
        companies.add(new Company("1","Adidas", adidas));
        companies.add(new Company("1","NIKE", nike));
        //companies.add(new Company("NIKE", nike));
        //companies.add(new Company("NIKE", nike));
        companies.add(new Company("1","Adidas", adidas));
    }

    private ResponseInterface handler = new ResponseInterface() {
        @Override
        public void onUserGetAllCompanies(JSONObject response) {
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
    };
}
