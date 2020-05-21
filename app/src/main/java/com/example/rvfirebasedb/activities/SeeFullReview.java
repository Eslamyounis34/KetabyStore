package com.example.rvfirebasedb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rvfirebasedb.R;

public class SeeFullReview extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbartx,fullReviewTx;
    ImageView toolbarbackicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_full_review);

        toolbar=findViewById(R.id.apptoolbar);
        toolbartx=findViewById(R.id.toolbarbookname);
        fullReviewTx=findViewById(R.id.fullreview);
        toolbarbackicon=findViewById(R.id.backicon);

        toolbartx.setText("نبذة");
        Intent i=getIntent();
        String getFullSumm=i.getExtras().getString("SendFullSummry");
        fullReviewTx.setText(getFullSumm);

        toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
