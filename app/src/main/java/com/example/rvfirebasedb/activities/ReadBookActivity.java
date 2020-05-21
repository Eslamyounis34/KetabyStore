package com.example.rvfirebasedb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rvfirebasedb.R;

import java.util.zip.Inflater;

public class ReadBookActivity extends AppCompatActivity {

    WebView webView;
    Toolbar toolbar;
    ImageView toolbarbackicon;
    TextView toolbarTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        toolbar=findViewById(R.id.apptoolbar);
        toolbarbackicon=findViewById(R.id.backicon);
        toolbarTextView=findViewById(R.id.toolbarbookname);
        webView=findViewById(R.id.webview);


        Intent i=getIntent();
        final String getReadUrl=i.getExtras().getString("SendReadBookLink");
        final String getBookName=i.getExtras().getString("SendSelectedBookName");
        toolbarTextView.setText(getBookName);


        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(getReadUrl);

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
