package com.example.mikhail.stockstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_of_list, parent, false);
        }

        Person p = getPerson(position);

        // заполняем View в пункте списка данными
        ((TextView) view.findViewById(R.id.fio)).setText(p.surname + "\n" + p.name);
       // ((ImageView) view.findViewById(R.id.person_photo)).setImageResource(p.photo);
        ImageLoader.getInstance().displayImage(p.photo, (ImageView) view.findViewById(R.id.person_photo));

        // пишем позицию
        //cbBuy.setTag(position);
        return view;
    }

    Person getPerson(int position) {
        return ((Person) getItem(position));
    }

    ArrayList<Person> getAllPersons() {
        return persons;
    }
}
