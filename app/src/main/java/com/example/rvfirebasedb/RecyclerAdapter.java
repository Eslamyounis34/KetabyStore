package com.example.rvfirebasedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    ArrayList<Integer> bookCovers=new ArrayList<>();
    ArrayList<String>bookAuthors=new ArrayList<>();
    Context mContext ;

    public RecyclerAdapter(ArrayList<Integer> bookCovers, ArrayList<String> bookAuthors, Context mContext) {
        this.bookCovers = bookCovers;
        this.bookAuthors = bookAuthors;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.recycleritem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.image.setImageResource(bookCovers.get(position));
        holder.tx.setText(bookAuthors.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,SelectedCategory.class);
                i.putExtra("Category",bookAuthors.get(position));
                mContext.startActivity(i);

            }
        });
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
