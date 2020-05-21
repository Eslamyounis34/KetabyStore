package com.example.rvfirebasedb.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.rvfirebasedb.R;
import com.example.rvfirebasedb.adapters.HomeRecyclerAdapter;
import com.example.rvfirebasedb.test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    DatabaseReference searchReference= FirebaseDatabase.getInstance().getReference();
    ProgressDialog progressDialog;
    String getAuthorName = "";
    String getBookPoster = "";
    String bookname = "";
    String getReadLink = "";
    String getSummry="";
    Double getRate=5.5;
    String bookDownloadLink="";

    String getforAuthorName = "";
    String getforBookPoster = "";
    String forbookname = "";
    String getforReadLink = "";
    String getforSummry="";
    Double getForRate=0.0;
    String forbookDownloadLink="";

    ArrayList<String> forbookPoster = new ArrayList<>();
    ArrayList<String> forbookAuthor = new ArrayList<>();
    ArrayList<String> forbooknames = new ArrayList<>();
    ArrayList<String> forbookReadLink = new ArrayList<>();
    ArrayList<String> forbookSummries = new ArrayList<>();
    ArrayList<Double> forBookRates = new ArrayList<>();
    ArrayList<String>forbookDownloadLinks=new ArrayList<>();

    ArrayList<String> bookPoster = new ArrayList<>();
    ArrayList<String> bookAuthor = new ArrayList<>();
    ArrayList<String> booknames = new ArrayList<>();
    ArrayList<String> bookReadLink = new ArrayList<>();
    ArrayList<String> bookSummries = new ArrayList<>();
    ArrayList<Double> bookRates = new ArrayList<>();
    ArrayList<String>bookDownloadLinks=new ArrayList<>();

//    SearchView searchView;
    TextView retryConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(connected()){
            setContentView(R.layout.activity_home);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            progressDialog=new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
            progressDialog.show();
            progressDialog.setMessage("جاري التحميل..");

            final DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            drawer.openDrawer(GravityCompat.START);
            drawer.closeDrawer(GravityCompat.START);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


            DatabaseReference arabicNovels=reference.child("الاكثر قراءة");

            arabicNovels.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot bookDataSnapshot : dataSnapshot.getChildren()) {
                        getAuthorName = bookDataSnapshot.child("المؤلف").getValue(String.class);
                        getBookPoster = bookDataSnapshot.child("غلاف").getValue(String.class);
                        getReadLink = bookDataSnapshot.child("قراءة").getValue(String.class);
                        getSummry= bookDataSnapshot.child("نبذة").getValue(String.class);
                        getRate=bookDataSnapshot.child("تقييم").getValue(Double.class);
                        bookDownloadLink=bookDataSnapshot.child("تحميل").getValue(String.class);
                        bookname = bookDataSnapshot.getKey();
                        bookPoster.add(getBookPoster);
                        bookAuthor.add(getAuthorName);
                        booknames.add(bookname);
                        bookReadLink.add(getReadLink);
                        bookSummries.add(getSummry);
                        bookRates.add(getRate);
                        bookDownloadLinks.add(bookDownloadLink);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    layoutManager.setReverseLayout(true);
                    RecyclerView recyclerView = findViewById(R.id.mostreadrecycler);
                    HomeRecyclerAdapter recyclerAdapter = new HomeRecyclerAdapter(bookPoster, bookAuthor,bookReadLink, booknames, bookSummries,bookRates,bookDownloadLinks,getApplicationContext());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            DatabaseReference forigenNovels=reference.child("روايات مترجمه");
            forigenNovels.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap:dataSnapshot.getChildren())
                    {

                        getforAuthorName=snap.child("المؤلف").getValue(String.class);
                        getforBookPoster=snap.child("غلاف").getValue(String.class);
                        getforReadLink=snap.child("قراءة").getValue(String.class);
                        getforSummry= snap.child("نبذة").getValue(String.class);
                        getForRate=snap.child("تقييم").getValue(Double.class);
                        forbookDownloadLink=snap.child("تحميل").getValue(String.class);
                        forbookname=snap.getKey();
                        forbookPoster.add(getforBookPoster);
                        forbooknames.add(forbookname);
                        forbookAuthor.add(getforAuthorName);
                        forbookReadLink.add(getforReadLink);
                        forbookSummries.add(getforSummry);
                        forBookRates.add(getForRate);
                        forbookDownloadLinks.add(forbookDownloadLink);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    layoutManager.setReverseLayout(true);
                    RecyclerView recyclerView2 = findViewById(R.id.rewayatmotrgmarecycler);
                    HomeRecyclerAdapter recyclerAdapter = new HomeRecyclerAdapter(forbookPoster, forbookAuthor, forbookReadLink, forbooknames, forbookSummries,forBookRates,forbookDownloadLinks,getApplicationContext());
                    recyclerView2.setAdapter(recyclerAdapter);
                    recyclerView2.setLayoutManager(layoutManager);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        }

    else{
            setContentView(R.layout.noconnection);

            retryConnection=findViewById(R.id.retryconnect);
            retryConnection.setPaintFlags(retryConnection.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            retryConnection.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     finish();
                     startActivity(getIntent());
                 }
             });
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchitem, menu);

        MenuItem searchItem = menu.findItem(R.id.searchview);
        final android.widget.SearchView mSearchView = (android.widget.SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                searchReference.addValueEventListener(new ValueEventListener() {
                        String authorName="";
                        String image="";
                        Double rate=5.0;
                        String read="";
                        String download="";
                        String bookName="";
                        String sum="";
                        Intent i=new Intent(HomeActivity.this, SearchResults.class);

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                for (DataSnapshot getBookSnap : snap.getChildren()) {
                                    if (getBookSnap.getKey().equals(s)) {
                                        authorName = getBookSnap.child("المؤلف").getValue(String.class);
                                        image=getBookSnap.child("غلاف").getValue(String.class);
                                        read=getBookSnap.child("قراءة").getValue(String.class);
                                        download=getBookSnap.child("تحميل").getValue(String.class);
                                        rate=getBookSnap.child("تقييم").getValue(Double.class);
                                        sum=getBookSnap.child("نبذة").getValue(String.class);
                                        bookName=getBookSnap.getKey();

                                        i.putExtra("sendsearchedauthorname",authorName);
                                        i.putExtra("sendsearchedbookimage",image);
                                        i.putExtra("searchedbookrate",rate);
                                        i.putExtra("searchedbookreadlink",read);
                                        i.putExtra("searchedbookdownloadlink",download);
                                        i.putExtra("searchedbooksum",sum);
                                        i.putExtra("searchedbookname",bookName);
                                        break;
                                    }

                                }

                            }
                            if (bookName.equals(""))
                            {
                                i.putExtra("searchedbookname","No Data");
                                startActivity(i);
                            }
                            else
                            {
//
                                startActivity(i);

                            }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                mSearchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.otlobketab) {
            startActivity(new Intent(this, RequestNovel.class));

        } else if (id == R.id.contactus) {
           getOpenFacebookIntent();

        } else if (id == R.id.shareapp) {
            startActivity(new Intent(this, test.class));


        } else if (id == R.id.favourites) {

            startActivity(new Intent(this, Favourites.class));

        } else if (id == R.id.downloads) {
            startActivity(new Intent(this, DownloadsActivity.class));

        }
        else if(id==R.id.arabya){
           startActivity(new Intent(this, ArabicNovels.class));
        }
        else if (id==R.id.motrgma){
            startActivity(new Intent(this, TranslatedNovels.class));

        }
        else if (id==R.id.deneya){
            startActivity(new Intent(this, ReligiousNovels.class));

        }
        else if (id==R.id.tnmya){
            startActivity(new Intent(this, HumanDevelopmentNovels.class));

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public void  getOpenFacebookIntent() {

       try {
           Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("fb://page/102178411177056"));
           startActivity(i);
       }catch (ActivityNotFoundException e)
       {
           Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/Ketaby-Store-102178411177056"));
           startActivity(i);
       }

        }

    private boolean connected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }
    }

