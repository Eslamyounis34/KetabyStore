package com.example.rvfirebasedb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthorInfo extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView aboutAuthorTx;
    TextView bestNovelsTx;

    Toolbar toolbar;
    TextView toolbarTextView;
    ImageView toolbarbackicon;

    String getAuthorPoster="";
    String getAuthorbestNovels="";
    String getAuthorInfo="";
    ProgressDialog progressDialog;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);

        circleImageView = findViewById(R.id.profile_image);
        aboutAuthorTx = findViewById(R.id.authorinfo);
        bestNovelsTx = findViewById(R.id.bestnovels);

        toolbarbackicon = findViewById(R.id.backicon);
        toolbarTextView = findViewById(R.id.toolbarbookname);
        toolbar = findViewById(R.id.apptoolbar);


        Intent i=getIntent();
        progressDialog=new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progressDialog.show();
        progressDialog.setMessage("جاري التحميل..");
        String authorName=i.getExtras().getString("SendAuthorName");

        bestNovelsTx.setText(authorName);

        DatabaseReference author=reference.child("المؤلفين").child(authorName);
        author.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAuthorbestNovels=dataSnapshot.child("اعماله").getValue(String.class);
                getAuthorPoster=dataSnapshot.child("غلاف").getValue(String.class);
                getAuthorInfo=dataSnapshot.child("معلومات").getValue(String.class);

                Picasso.with(AuthorInfo.this).load(getAuthorPoster).into(circleImageView);
                       aboutAuthorTx.setText(getAuthorInfo);
                       bestNovelsTx.setText(getAuthorbestNovels);
                       progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbarTextView.setText(authorName);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
