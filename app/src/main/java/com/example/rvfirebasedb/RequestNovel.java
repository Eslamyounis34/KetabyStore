package com.example.rvfirebasedb;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RequestNovel extends AppCompatActivity {

    Toolbar toolbar;

    TextView toolbarTextView;
    ImageView toolbarbackicon;

    EditText bookName,authorName;
    ImageView sendData;

    DatabaseReference reference;

    String userId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_novel);

        toolbarbackicon=findViewById(R.id.backicon);
        toolbarTextView=findViewById(R.id.toolbarbookname);
        toolbar=findViewById(R.id.apptoolbar);
        bookName=findViewById(R.id.novelname);
        authorName=findViewById(R.id.novelauthor);
        sendData=findViewById(R.id.sendrequest);

        toolbarTextView.setText("اطلب كتابك");
        reference= FirebaseDatabase.getInstance().getReference().child("الطلبات");
//        mAuth=FirebaseAuth.getInstance();
//        mUser=mAuth.getCurrentUser();
//        userId=mUser.getUid();


       sendData.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String sendNovelName=bookName.getText().toString();
               String sendAuthorName=authorName.getText().toString();

               reference=FirebaseDatabase.getInstance().getReference().child("الطلبات").child(sendNovelName);
               if (!sendNovelName.equals("")&&!sendAuthorName.equals(""))
               {

                   Map<String, Object> bookData = new HashMap<>();
                   bookData.put("اسم الروايه", sendNovelName);
                   bookData.put("اسم المؤلف",sendAuthorName);
                   reference.setValue(bookData);
                   openDialog();
               }
               else
               {
                   Toast.makeText(RequestNovel.this, "قم بملئ الحقول", Toast.LENGTH_SHORT).show();
               }





           }
       });
       toolbarbackicon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });

       authorName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if (!b) {
                   hideKeyboard(view);
               }
           }
       });

        bookName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("تم ارسال طلبك بنجاح");
        builder.setPositiveButton("تم",null);
        AlertDialog alert=builder.create();
        alert.show();

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
