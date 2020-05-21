package com.example.rvfirebasedb.activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rvfirebasedb.adapters.HomeRecyclerAdapter;
import com.example.rvfirebasedb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HumanDevelopmentNovels extends AppCompatActivity {

    DatabaseReference reference;

    String bookName="";
    String bookAuthor="";
    String bookCover="";
    String bookSumm="";
    String bookReadLink="";
    String bookDownloadLink="";
    Double bookRate=5.5;
    ProgressDialog progressDialog;

    ArrayList<String> bookNames=new ArrayList<>();
    ArrayList<String>bookAuthors=new ArrayList<>();
    ArrayList<String>bookCovers=new ArrayList<>();
    ArrayList<String>bookSumms=new ArrayList<>();
    ArrayList<String>bookReadLinks=new ArrayList<>();
    ArrayList<String>bookDownloadLinks=new ArrayList<>();
    ArrayList<Double>bookRates=new ArrayList<>();

    RecyclerView recyclerView;

    Toolbar toolbar;
    TextView toolbarTextView;
    ImageView toolbarbackicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_development_novels);

        recyclerView=findViewById(R.id.recyclerforhumancategory);
        toolbar=findViewById(R.id.apptoolbar);
        toolbarbackicon=findViewById(R.id.backicon);
        toolbarTextView=findViewById(R.id.toolbarbookname);

        toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reference= FirebaseDatabase.getInstance().getReference().child("تنميه بشرية");
        toolbarTextView.setText("روايات تنمية بشرية");
        progressDialog=new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progressDialog.show();
        progressDialog.setMessage("جاري التحميل..");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot bookDataSnapshot : dataSnapshot.getChildren())
                {
                    bookAuthor = bookDataSnapshot.child("المؤلف").getValue(String.class);
                    bookCover = bookDataSnapshot.child("غلاف").getValue(String.class);
                    bookReadLink = bookDataSnapshot.child("قراءة").getValue(String.class);
                    bookSumm= bookDataSnapshot.child("نبذة").getValue(String.class);
                    bookRate=bookDataSnapshot.child("تقييم").getValue(Double.class);
                    bookDownloadLink=bookDataSnapshot.child("تحميل").getValue(String.class);
                    bookName = bookDataSnapshot.getKey();
                    bookCovers.add(bookCover);
                    bookAuthors.add(bookAuthor);
                    bookNames.add(bookName);
                    bookReadLinks.add(bookReadLink);
                    bookSumms.add(bookSumm);
                    bookRates.add(bookRate);
                    bookDownloadLinks.add(bookDownloadLink);
                }
                GridLayoutManager layoutManager = new GridLayoutManager(HumanDevelopmentNovels.this,3, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                HomeRecyclerAdapter recyclerAdapter = new HomeRecyclerAdapter(bookCovers, bookAuthors,bookReadLinks, bookNames, bookSumms,bookRates,bookDownloadLinks,getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
