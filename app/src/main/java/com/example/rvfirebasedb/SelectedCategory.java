package com.example.rvfirebasedb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rvfirebasedb.adapters.CategoryRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedCategory extends AppCompatActivity {

    DatabaseReference reference;

    RecyclerView recyclerView;
    ArrayList<String> bookPoster=new ArrayList<>();
    ArrayList<String>bookAuthor=new ArrayList<>();
    ArrayList<String>booknames=new ArrayList<>();
    ArrayList<String>bookReadLink=new ArrayList<>();


    String getAuthorName="";
    String getBookPoster="";
    String bookname="";
    String getReadLink="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        recyclerView= findViewById(R.id.recycler);

        Intent i= getIntent();
        String categoryName=i.getExtras().getString("Category");

        reference=FirebaseDatabase.getInstance().getReference().child(categoryName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookDataSnapshot:dataSnapshot.getChildren())
                {
                    getAuthorName=bookDataSnapshot.child("المؤلف").getValue(String.class);
                    getBookPoster=bookDataSnapshot.child("غلاف").getValue(String.class);
                    getReadLink=bookDataSnapshot.child("قراءة").getValue(String.class);
                    bookname=bookDataSnapshot.getKey();
                    bookPoster.add(getBookPoster);
                    bookAuthor.add(getAuthorName);
                    booknames.add(bookname);
                    bookReadLink.add(getReadLink);

                }
                GridLayoutManager manager=new GridLayoutManager(SelectedCategory.this, 2);
                CategoryRecyclerAdapter recyclerAdapter=new CategoryRecyclerAdapter(bookPoster,booknames,bookAuthor,bookReadLink,SelectedCategory.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        reference= FirebaseDatabase.getInstance().getReference();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String s= dataSnapshot.child("روايات مترجمه").child("الستارة").child("غلاف").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
