package com.example.rvfirebasedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SearchResults extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTextView;
    ImageView toolbarbackicon;

    ImageView bookImage;
    TextView searchedBookAuthorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent i=getIntent();
        final String getAuthorName=i.getExtras().getString("sendsearchedauthorname");
        final String getBookReadLink=i.getExtras().getString("searchedbookreadlink");
        final String getBookDownloadLink=i.getExtras().getString("searchedbookdownloadlink");
        final String getBookImage=i.getExtras().getString("sendsearchedbookimage");
        final double getBookRate=i.getExtras().getDouble("searchedbookrate");
        final String getBookSumm=i.getExtras().getString("searchedbooksum");
        final String getBookName=i.getExtras().getString("searchedbookname");



        if (getBookName.equals("No Data"))
        {
            setContentView(R.layout.nofoundlayout);

            toolbar=findViewById(R.id.apptoolbar);
            toolbarbackicon=findViewById(R.id.backicon);
            toolbarTextView=findViewById(R.id.toolbarbookname);

            toolbarTextView.setText("نتائج البحث");
            toolbarbackicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });


        }
        else
        {
            setContentView(R.layout.activity_search_results);

            toolbar=findViewById(R.id.apptoolbar);
            toolbarbackicon=findViewById(R.id.backicon);
            toolbarTextView=findViewById(R.id.toolbarbookname);
            bookImage=findViewById(R.id.searchedbookImage);
            searchedBookAuthorName=findViewById(R.id.searchedbookName);


            toolbarTextView.setText("نتائج البحث");
            toolbarbackicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            Picasso.with(this).load(getBookImage).into(bookImage);
            searchedBookAuthorName.setText(getAuthorName);

            bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(SearchResults.this,BookInfo.class);
                    intent.putExtra("sendauthor",getAuthorName);
                    intent.putExtra("sendbookname",getBookName);
                    intent.putExtra("sendbookcover",getBookImage);
                    intent.putExtra("sendreadlink",getBookReadLink);
                    intent.putExtra("sendsum",getBookSumm);
                    intent.putExtra("sendrate",getBookRate);
                    intent.putExtra("senddownloadlink",getBookDownloadLink);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
