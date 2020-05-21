package com.example.rvfirebasedb.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvfirebasedb.adapters.FavouritesAdapter;
import com.example.rvfirebasedb.R;
import com.example.rvfirebasedb.SqliteDB;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {
    ListView listView;

    ArrayList<String> bookPosters = new ArrayList<>();
    ArrayList<String> bookNames = new ArrayList<>();
    ArrayList<String> bookRates = new ArrayList<>();
    ArrayList<String> bookAuthors = new ArrayList<>();
    ArrayList<String> bookSumms = new ArrayList<>();
    ArrayList<String> bookReadLinks = new ArrayList<>();
    ArrayList<String> bookDownloadLinks = new ArrayList<>();



    String getBookPoster = "";
    String bookTitle = "";
    String bookAuthor="";
    String bookSumm="";
    String bookRate="";
    String bookRead="";
    String bookDownload="";

    Toolbar toolbar;

    TextView toolbarTextView;
    ImageView toolbarbackicon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        listView=findViewById(R.id.favouriteList);
        toolbarbackicon=findViewById(R.id.backicon);
        toolbarTextView=findViewById(R.id.toolbarbookname);
        toolbar=findViewById(R.id.apptoolbar);

        toolbarTextView.setText("المفضلة");


        SqliteDB sqliteDB=new SqliteDB(this);
        SQLiteDatabase db=sqliteDB.getReadableDatabase();


        Cursor c=db.rawQuery("select * from "+SqliteDB.Table_Name,null);
        if (c.moveToFirst())
        {
            do
            {
                bookTitle=c.getString(c.getColumnIndex(SqliteDB.book_Name));
                getBookPoster=c.getString(c.getColumnIndex(SqliteDB.book_Cover));
                bookAuthor=c.getString(c.getColumnIndex(SqliteDB.book_Author));
                bookSumm=c.getString(c.getColumnIndex(SqliteDB.book_Overview));
                bookDownload=c.getString(c.getColumnIndex(SqliteDB.book_downloadLink));
                bookRead=c.getString(c.getColumnIndex(SqliteDB.book_readingLink));
                bookRate=c.getString(c.getColumnIndex(SqliteDB.book_Rate));

                bookPosters.add(getBookPoster);
                bookNames.add(bookTitle);
                bookDownloadLinks.add(bookDownload);
                bookReadLinks.add(bookRead);
                bookRates.add(bookRate);
                bookAuthors.add(bookAuthor);
                bookSumms.add(bookSumm);

            }while (c.moveToNext());
            FavouritesAdapter adp=new FavouritesAdapter(bookPosters,bookNames,this);
            listView.setAdapter(adp);
        }
        registerForContextMenu(listView);

        toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Favourites.this,BookInfo.class);
                intent.putExtra("sendauthor",bookAuthors.get(i));
                intent.putExtra("sendbookname",bookNames.get(i));
                intent.putExtra("sendbookcover",bookPosters.get(i));
                intent.putExtra("sendreadlink",bookReadLinks.get(i));
                intent.putExtra("sendsum",bookSumms.get(i));
                intent.putExtra("sendrate",bookRates.get(i));
                intent.putExtra("senddownloadlink",bookDownloadLinks.get(i));

                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("اختر");
        getMenuInflater().inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int postion=info.position;
        if(item.getItemId()==R.id.deletbook)
        {
            SqliteDB sqliteDB = new SqliteDB(Favourites.this);
            SQLiteDatabase db=sqliteDB.getWritableDatabase();
            Cursor c=db.rawQuery("select * from "+ SqliteDB.Table_Name +" where "+ SqliteDB.book_Name +"='"+bookNames.get(postion)+"'",null );
            if (c.moveToFirst())
            {
                String getItemId=c.getString(c.getColumnIndex(SqliteDB.book_id));
                db.delete(SqliteDB.Table_Name, SqliteDB.book_id + " =?", new String[]{getItemId});
                db.close();

                finish();
                startActivity(getIntent());

            }

            Toast.makeText(this, "تم حذف الرواية من المفضلة ", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}
