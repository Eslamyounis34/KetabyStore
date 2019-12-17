package com.example.rvfirebasedb;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TranslatedNovels extends AppCompatActivity {

    DatabaseReference reference;

    String bookName="";
    String bookAuthor="";
    String bookCover="";
    String bookSumm="";
    String bookReadLink="";
    String bookDownloadLink="";
    Double bookRate=5.5;
    ProgressDialog progressDialog;

    ArrayList<String>bookNames=new ArrayList<>();
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
        setContentView(R.layout.activity_translated_novels);

        recyclerView=findViewById(R.id.recyclerforarabiccategory);
        toolbar=findViewById(R.id.apptoolbar);
        toolbarbackicon=findViewById(R.id.backicon);
        toolbarTextView=findViewById(R.id.toolbarbookname);

        reference=FirebaseDatabase.getInstance().getReference().child("روايات مترجمه");
        toolbarTextView.setText("روايات مترجمة");
        progressDialog=new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progressDialog.show();
        progressDialog.setMessage("جاري التحميل..");
        toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                    bookDownloadLinks.add(bookDownloadLink);
                    bookNames.add(bookName);
                    bookReadLinks.add(bookReadLink);
                    bookSumms.add(bookSumm);
                    bookRates.add(bookRate);
                }
                GridLayoutManager layoutManager = new GridLayoutManager(TranslatedNovels.this,3, LinearLayoutManager.VERTICAL, false);
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
