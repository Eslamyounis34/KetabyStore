package com.example.rvfirebasedb;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class BookInfo extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    ImageView bookCover, horizontalBookImage;
    TextView author, bookName, bookSumm, seeMore;
    Toolbar toolbar;
    TextView toolbarTextView;
    ImageView toolbarbackicon;
    Button readNow;
    RatingBar ratingBar;
    ImageView favouritebt, sharebt, aboutauthorbt, downloadbt;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    CircleImageView circleImageView;
    TextView aboutAuthorTx;
    TextView bestNovelsTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);


        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Do next code
        }

        toolbar = findViewById(R.id.apptoolbar);
        bookCover = findViewById(R.id.bookinfoimage);
        author = findViewById(R.id.bookinfoauthor);
        bookName = findViewById(R.id.bookinfobookname);
        toolbarbackicon = findViewById(R.id.backicon);
        toolbarTextView = findViewById(R.id.toolbarbookname);
        horizontalBookImage = findViewById(R.id.horizontalbookimage);
        readNow = findViewById(R.id.readnowbt);
        bookSumm = findViewById(R.id.bookinfobooksumm);
        seeMore = findViewById(R.id.seemore);
        ratingBar = findViewById(R.id.ratingbar);
        favouritebt = findViewById(R.id.bookfavbt);
        sharebt = findViewById(R.id.booksharebt);
        aboutauthorbt = findViewById(R.id.bookautohrbt);
        downloadbt = findViewById(R.id.bookdownloadbutton);
        circleImageView = findViewById(R.id.profile_image);
        aboutAuthorTx = findViewById(R.id.authorinfo);
        bestNovelsTx = findViewById(R.id.bestnovels);
        seeMore.setPaintFlags(seeMore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        Intent i = getIntent();
        final String getReadUrl = i.getExtras().getString("sendreadlink");
        final String getCoverUrl = i.getExtras().getString("sendbookcover");
        final String getAuthor = i.getExtras().getString("sendauthor");
        final String getBookName = i.getExtras().getString("sendbookname");
        final String getBookSumm = i.getExtras().getString("sendsum");
        final Double getBookRate = i.getExtras().getDouble("sendrate");
        final String getDownloadLink = i.getExtras().getString("senddownloadlink");


        toolbarTextView.setText(getBookName);
        ratingBar.setRating(getBookRate.floatValue());

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        author.setText(getAuthor);
        bookName.setText(getBookName);
        bookSumm.setText(getBookSumm);

        Picasso.with(this).load(getCoverUrl).into(horizontalBookImage);

        Picasso.with(this).load(getCoverUrl)
                .transform(new BlurTransformation(getApplicationContext(), 9, 1))
                .into(bookCover);

        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(BookInfo.this);
                LayoutInflater inflater=BookInfo.this.getLayoutInflater();
                View view=inflater.inflate(R.layout.custom_alert_dialog,null);
                TextView sumCustomTx=view.findViewById(R.id.custom_dialog_summ);
                builder.setView(view);
                sumCustomTx.setText(getBookSumm);

                AlertDialog alert = builder.create();
                alert.show();




            }
        });

        sharebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent webIntent = new Intent();
                webIntent.setAction(Intent.ACTION_SEND);
                webIntent.putExtra(Intent.EXTRA_TEXT, getReadUrl);
                webIntent.setType("text/plain");
                startActivity(webIntent);
            }
        });


        toolbarbackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        readNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookInfo.this, ReadBookActivity.class);
                i.putExtra("SendReadBookLink", getReadUrl);
                i.putExtra("SendSelectedBookName", getBookName);
                startActivity(i);
            }
        });

        SqliteDB db = new SqliteDB(BookInfo.this);
        SQLiteDatabase sql = db.getWritableDatabase();
        Cursor c = sql.rawQuery("select * from " + SqliteDB.Table_Name + " where " + SqliteDB.book_Name + "='" + getBookName + "'", null);
        if (c.moveToFirst()) {
            favouritebt.setImageResource(R.drawable.favpressed);
        } else {
            favouritebt.setImageResource(R.drawable.favourites);

        }
        favouritebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SqliteDB db = new SqliteDB(BookInfo.this);
                SQLiteDatabase sql = db.getWritableDatabase();


                Cursor c = sql.rawQuery("select * from " + SqliteDB.Table_Name + " where " + SqliteDB.book_Name + "='" + getBookName + "'", null);
                if (c.moveToFirst()) {
                    String getItemId=c.getString(c.getColumnIndex(SqliteDB.book_id));
                    sql.delete(SqliteDB.Table_Name, SqliteDB.book_id + " =?", new String[]{getItemId});
                    sql.close();

                    Intent intent = getIntent();
                    finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                    Toast.makeText(BookInfo.this, "تم الحذف", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SqliteDB.book_Name, getBookName);
                    contentValues.put(SqliteDB.book_Author, getAuthor);
                    contentValues.put(SqliteDB.book_Rate, getBookRate);
                    contentValues.put(SqliteDB.book_Cover, getCoverUrl);
                    contentValues.put(SqliteDB.book_Overview, getBookSumm);
                    contentValues.put(SqliteDB.book_downloadLink, getDownloadLink);
                    contentValues.put(SqliteDB.book_readingLink, getReadUrl);


                    favouritebt.setImageResource(R.drawable.favpressed);

                    sql.insert(SqliteDB.Table_Name, null, contentValues);
                    Toast.makeText(BookInfo.this, "تم الاضافة الي المفضلة", Toast.LENGTH_SHORT).show();
                }


            }
        });

        downloadbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final Uri Download_Uri = Uri.parse(getDownloadLink);
                File check = new File(Environment.getExternalStorageDirectory()
                        + "/KetabyStore/" + getBookName + ".pdf");
                if (!check.exists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookInfo.this);
                    builder.setMessage("سيتم تحميل الكتاب هل أنت متأكد ؟");
                    builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle("جاري تحميل الكتاب");
                            request.setDescription(getBookName);
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir("/KetabyStore", getBookName + ".pdf");
                            downloadManager.enqueue(request);

                            BroadcastReceiver receiver=new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    Intent i=new Intent(getApplicationContext(),PdfViwerActivity.class);
                                    String filePath=Environment.getExternalStorageDirectory()
                                            + "/KetabyStore/" + getBookName + ".pdf";
                                    Log.e("CheckFilePath",filePath);
                                    i.putExtra("SendPath",filePath);
                                    startActivity(i);
                                }
                            };


                            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                        }
                    }).setNegativeButton("الغاء", null);

                    AlertDialog alert = builder.create();
                    alert.show();


                } else {
                    Toast.makeText(BookInfo.this, "تم التحميل بالفعل مسبقا .. اذهب الي التحميلات ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        aboutauthorbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(BookInfo.this,AuthorInfo.class);
                intent.putExtra("SendAuthorName",getAuthor);

                startActivity(intent);


            }

        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(BookInfo.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(BookInfo.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(BookInfo.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(BookInfo.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
