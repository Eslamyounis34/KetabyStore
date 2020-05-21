package com.example.rvfirebasedb.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rvfirebasedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouritesAdapter extends BaseAdapter {

    ArrayList<String> bookCovers=new ArrayList<>();
    ArrayList<String>bookName=new ArrayList<>();
    Activity ac ;

    public FavouritesAdapter(ArrayList<String> bookCovers, ArrayList<String> bookName, Activity ac) {
        this.bookCovers = bookCovers;
        this.bookName = bookName;
        this.ac = ac;
    }

    @Override
    public int getCount() {
        return bookName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=ac.getLayoutInflater();
        View v=inflater.inflate(R.layout.favouriteitemrow,null);

        ImageView imageView=v.findViewById(R.id.favourite_book_image);
        Picasso.with(ac).load(bookCovers.get(position)).into(imageView);

        TextView textView=v.findViewById(R.id.favourite_book_text);
        textView.setText(bookName.get(position));

        return v;
    }
}
