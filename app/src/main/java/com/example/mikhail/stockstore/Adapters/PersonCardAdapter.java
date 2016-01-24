package com.example.mikhail.stockstore.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikhail.stockstore.Classes.ResponseInterface;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Entities.Person;
import com.example.mikhail.stockstore.R;
import com.example.mikhail.stockstore.StocksActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mikhail on 18.01.16.
 */
public class PersonCardAdapter extends RecyclerView.Adapter<PersonCardAdapter.PersonViewHolder>{
    List<Person> persons;

    // Вертикальное ли расположение recyclerView
    private boolean isVertical = true;

    public PersonCardAdapter(List<Person> persons){
        this.persons = persons;
    }

    public PersonCardAdapter(List<Person> persons, boolean isVertical){
        this.persons = persons;
        this.isVertical = isVertical;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_card, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position) {
        holder.personName.setText(persons.get(position).name);
        holder.personSurname.setText(persons.get(position).surname);

        ImageLoader.getInstance().displayImage(persons.get(position).photo, holder.personPhoto);

        //PersonViewHolder.isAdded = stocks.get(position).isAdded;
        if (persons.get(position).isAdded){
            holder.addPersonBtn.setBackgroundResource(R.drawable.added);
            // holder.cv.setCardBackgroundColor(holder.cardLayout.getContext().getResources().getColor(R.color.default_app_green));
        }

        holder.addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Toast.makeText(v.getContext().getApplicationContext(), stocks.get(position).id, Toast.LENGTH_SHORT).show();
                Activity host = (Activity) v.getContext();
                // APIRequestConstructor.userAddStock(host, stocks.get(position).id);

                // holder.addStockBtn.setBackgroundResource(R.drawable.added);

                ServerResponseHandler handler;

                handler = new ServerResponseHandler() {
                    @Override
                    public void onInternalServerError(JSONObject response) {

                    }

                    @Override
                    public void onUnknownRequestUri(JSONObject response) {
                        try {
                            Toast.makeText(v.getContext().getApplicationContext(), response.getString("data"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(JSONObject response) {
                        try {
                            Toast.makeText(v.getContext().getApplicationContext(), response.getString("data"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                   /* @Override
                    public void onUserAddStock(JSONObject response) {
                        try {
                            Toast.makeText(v.getContext().getApplicationContext(), response.getString("name") + " добавлена на стену", Toast.LENGTH_SHORT).show();
                            changeButtonLabel(holder, stocks.get(position));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }*/

                };

                try {
                    Toast.makeText(v.getContext().getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();
                    /*AsyncRequestToServer request = new AsyncRequestToServer(host, handler);
                    request.setParameters(APIRequestConstructor.userAddStockParameters(host, persons.get(position).id));
                    request.execute(APIConstants.USER_ADD_STOCK);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(v.getContext().getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(holder.itemView.getContext(), StocksActivity.class);
                intent.putExtra("person", persons.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView personName;
        public TextView personSurname;
        public ImageView personPhoto;

        public ImageButton addPersonBtn;
        public boolean isAdded;

        public RelativeLayout cardLayout;

        PersonViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);

            personName = (TextView)itemView.findViewById(R.id.person_name);
            personSurname = (TextView)itemView.findViewById(R.id.person_surname);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);

            addPersonBtn = (ImageButton)itemView.findViewById(R.id.add_friend_btn);

            cardLayout = (RelativeLayout)itemView.findViewById(R.id.card_layout);

            isAdded = false;
        }
    }
}



