package com.example.rvfirebasedb;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Integer> categoryCover=new ArrayList<Integer>();
    ArrayList<String>categoryName=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);

        categoryName.add("روايات عربيه");
        categoryName.add("روايات مترجمه");
        categoryName.add("روايات دينيه");
        categoryName.add("تنميه بشرية");
        categoryCover.add(R.drawable.catone);
        categoryCover.add(R.drawable.cattwo);
        categoryCover.add(R.drawable.catthree);
        categoryCover.add(R.drawable.catfour);

        GridLayoutManager manager=new GridLayoutManager(MainActivity.this,2);
        RecyclerAdapter adapter=new RecyclerAdapter(categoryCover,categoryName,MainActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }
}
