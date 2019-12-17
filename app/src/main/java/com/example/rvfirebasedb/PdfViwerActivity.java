package com.example.rvfirebasedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViwerActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viwer);

        pdfView=findViewById(R.id.pdfviewer);

        Intent i=getIntent();
        String getPath=i.getExtras().getString("SendPath");
        Log.e("CheckPath",getPath);

        pdfView.fromFile(new File(getPath))
                .load();
    }
}
