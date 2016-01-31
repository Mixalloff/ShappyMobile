package com.example.mikhail.stockstore.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.Classes.APIConstants;
import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Entities.Person;
import com.example.mikhail.stockstore.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by mikhail on 31.01.16.
 */
public class HorizontalListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<Person> persons;

    public HorizontalListAdapter(Context context, ArrayList<Person> persons) {
        this.context = context;
        this.persons = persons;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return persons.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_of_list, parent, false);
        }

        RelativeLayout cardItem = (RelativeLayout) view.findViewById(R.id.friends_list_item);
        TextView fio = (TextView) view.findViewById(R.id.fio);
        ImageView friendPhoto = (ImageView) view.findViewById(R.id.person_photo);
        final ImageButton cardMenu = (ImageButton) view.findViewById(R.id.card_menu);

        Person p = getPerson(position);

        // заполняем View в пункте списка данными
        fio.setText(p.surname + "\n" + p.name);
       // ((ImageView) view.findViewById(R.id.person_photo)).setImageResource(p.photo);
        ImageLoader.getInstance().displayImage(p.photo, friendPhoto);

        cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    AddMenuWithItems(cardMenu, position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(v);
            }
        });
        return view;
    }

    // Обработчик клика по элементу из списка
    private void onItemClicked(View v){
        Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();
    }

    // Добавление меню и пунктов меню
    private void AddMenuWithItems(final View v, final int position){
        //final View v = holder.cardMenu;
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.empty_popup_menu, popup.getMenu());

        popup.getMenu().add(0, 1, 1, "Убрать из друзей");

        /*if (persons.get(position).isAdded){
            popup.getMenu().add(0, 1, 1,"Убрать из друзей");
        }
        else{
            popup.getMenu().add(0, 1, 1,"Добавить в друзья");
        }*/
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1: {
                        // Заглушка
                        Toast.makeText(v.getContext(), "friend deleted!", Toast.LENGTH_SHORT).show();
                    }
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }



    Person getPerson(int position) {
        return ((Person) getItem(position));
    }

    ArrayList<Person> getAllPersons() {
        return persons;
    }
}
