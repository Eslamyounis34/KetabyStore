package com.example.rvfirebasedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rvfirebasedb.activities.BookInfo;
import com.example.rvfirebasedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeRecyclerAdapter  extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>{

    ArrayList<String> bookCovers=new ArrayList<>();
    ArrayList<String>bookAuthors=new ArrayList<>();
    ArrayList<String>bookReadLink=new ArrayList<>();
    ArrayList<String>bookName=new ArrayList<>();
    ArrayList<String>bookSummry=new ArrayList<>();
    ArrayList<Double>bookRates=new ArrayList<>();
    ArrayList<String>bookDownload=new ArrayList<>();
    Context mContext ;

    public HomeRecyclerAdapter(ArrayList<String> bookCovers, ArrayList<String> bookAuthors, ArrayList<String> bookReadLink, ArrayList<String> bookName,ArrayList<String> bookSummry, ArrayList<Double> bookRates,ArrayList<String> bookDownload,Context mContext) {
        this.bookCovers = bookCovers;
        this.bookAuthors = bookAuthors;
        this.bookReadLink = bookReadLink;
        this.bookName = bookName;
        this.bookSummry=bookSummry;
        this.bookRates=bookRates;
        this.bookDownload=bookDownload;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.forrecycleritem,viewGroup,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Picasso.with(mContext).load(bookCovers.get(i)).into(viewHolder.image);
        viewHolder.tx.setText(bookAuthors.get(i));
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, BookInfo.class);
                intent.putExtra("sendauthor",bookAuthors.get(i));
                intent.putExtra("sendbookname",bookName.get(i));
                intent.putExtra("sendbookcover",bookCovers.get(i));
                intent.putExtra("sendreadlink",bookReadLink.get(i));
                intent.putExtra("sendsum",bookSummry.get(i));
                intent.putExtra("sendrate",bookRates.get(i));
                intent.putExtra("senddownloadlink",bookDownload.get(i));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tx;
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.foriegnbookimage);
            tx= itemView.findViewById(R.id.foriegnauthorname);
            layout= itemView.findViewById(R.id.foriegnrelativelayout);
        }
    }
}
