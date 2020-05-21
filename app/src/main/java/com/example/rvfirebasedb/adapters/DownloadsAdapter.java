package com.example.rvfirebasedb.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvfirebasedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DownloadsAdapter extends BaseAdapter {

    ArrayList<String>bookName=new ArrayList<>();
    ArrayList<String>pathList=new ArrayList<>();

    Activity ac ;

    public DownloadsAdapter(ArrayList<String> bookName,ArrayList<String> pathList, Activity ac) {
        this.bookName = bookName;
        this.pathList=pathList;
        this.ac = ac;
    }

    @Override
    public int getCount() {
        return bookName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final LayoutInflater inflater=ac.getLayoutInflater();
        final View v=inflater.inflate(R.layout.downloadsrowitem,null);

        ImageView imageView=v.findViewById(R.id.download_book_image);
        RelativeLayout rv=v.findViewById(R.id.downloadrelativelayout);
        TextView textView=v.findViewById(R.id.download_book_text);
        textView.setText(bookName.get(i));
        imageView.setImageResource(R.drawable.pdficon);


        return v;
    }
 }
