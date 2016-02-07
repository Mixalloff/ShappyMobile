package com.example.mikhail.stockstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mikhail.stockstore.Adapters.MiniCardsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail on 07.02.16.
 */
public class MiniCardFragment extends Fragment {
    private List<JSONObject> elements = new ArrayList<>();
    boolean flag = false;

    public MiniCardsAdapter adapter;

    View itemView;
    RecyclerView rv;
    TextView blockHeader;
    String blockName = "default";

    public void setElems(List<JSONObject> elements){
        this.elements = elements;
    }

    public void setBlockName(String name){
        this.blockName = name;
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

        itemView = inflater.inflate(R.layout.list_block_with_header, container, false);
        final FragmentActivity c = getActivity();

        blockHeader = (TextView) itemView.findViewById(R.id.list_header);
        blockHeader.setText(blockName);

        rv = (RecyclerView) itemView.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter = new MiniCardsAdapter(elements);
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
