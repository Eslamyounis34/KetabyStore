package com.example.rvfirebasedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>{

    ArrayList<String> bookCovers=new ArrayList<>();
    ArrayList<String>bookAuthors=new ArrayList<>();
    ArrayList<String>bookReadLink=new ArrayList<>();
    ArrayList<String>bookName=new ArrayList<>();
    Context mContext ;

    public CategoryRecyclerAdapter(ArrayList<String> bookCovers, ArrayList<String> bookName,ArrayList<String> bookAuthors,ArrayList<String>bookReadLink, Context mContext) {
        this.bookCovers = bookCovers;
        this.bookName=bookName;
        this.bookAuthors = bookAuthors;
        this.bookReadLink=bookReadLink;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.recycleritem,viewGroup,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Picasso.with(mContext).load(bookCovers.get(i)).into(viewHolder.image);
        viewHolder.tx.setText(bookAuthors.get(i));
    }

    @Override
    public int getItemCount() {
        return bookAuthors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tx;
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.bookimage);
            tx= itemView.findViewById(R.id.authorname);
            layout= itemView.findViewById(R.id.relativelayout);
        }
    }
}
