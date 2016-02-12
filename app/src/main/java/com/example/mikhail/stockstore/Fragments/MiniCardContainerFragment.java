package com.example.mikhail.stockstore.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mikhail.stockstore.Adapters.MiniCardsAdapter;
import com.example.mikhail.stockstore.Constants.ElementGroupSpecies;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.Search.SearchActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 07.02.16.
 */
public class MiniCardContainerFragment extends Fragment {
    private List<JSONObject> elements = new ArrayList<>();
    boolean flag = false;
    Activity activity = this.getActivity();
    ElementGroupSpecies elemType = ElementGroupSpecies.STOCKS;

    public MiniCardsAdapter adapter;

    View itemView;
    RecyclerView rv;
    TextView blockHeader;
    String blockName = "default";
    String query;
    int orientation = LinearLayoutManager.HORIZONTAL;

    public void setOrientation(int orientation){
        this.orientation = orientation;
    }

    public void setElems(List<JSONObject> elements){
        this.elements = elements;
    }

    public void setBlockName(String name){
        this.blockName = name;
    }

    public void setQuery(String query){ this.query = query; }

    public void setElementsType(ElementGroupSpecies type){
        this.elemType = type;
        setMoreBtnType();
    }

    // Установка обработчика кнопки "ЕЩЕ"
    private void setMoreBtnType(){
        try {
            Button more_btn = (Button) itemView.findViewById(R.id.more_button);
            more_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SearchActivity.class);
                    intent.putExtra("type", elemType);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
      //  initTestData();

        itemView = inflater.inflate(R.layout.mini_card_container, container, false);
        final FragmentActivity c = getActivity();
        setMoreBtnType();

        blockHeader = (TextView) itemView.findViewById(R.id.list_header);
        blockHeader.setText(blockName);

        rv = (RecyclerView) itemView.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(orientation);
        rv.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter = new MiniCardsAdapter(elements, orientation);
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv.setAdapter(adapter);
                    }
                });
            }
        }).start();

       // final MiniCardsAdapter adapter = new MiniCardsAdapter(elements);
        //rv.setAdapter(adapter);
        return itemView;
    }
}
