package com.example.rvfirebasedb.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rvfirebasedb.adapters.DownloadsAdapter;
import com.example.rvfirebasedb.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadsActivity extends AppCompatActivity {

    ListView downloadsLs;

    Toolbar toolbar;
    TextView toolbarTextView;
    ImageView toolbarbackicon;

    ArrayList<String> arrayList;
    ArrayList<String> pathList;


    DownloadsAdapter downloadsAdapter;

    private static final int PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        downloadsLs = findViewById(R.id.dowmloadslistview);

        arrayList = new ArrayList<>();
        pathList = new ArrayList<>();

        downloadsAdapter = new DownloadsAdapter(arrayList, pathList, this);




        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/KetabyStore");
                if (dir.exists()) {
                    Log.d("path", dir.toString());
                    File list[] = dir.listFiles();
                    for (int i = 0; i < list.length; i++) {
                        arrayList.add(list[i].getName());
                        pathList.add(list[i].getPath());
                        downloadsLs.setAdapter(downloadsAdapter);
                    }

                } else {
                    requestPermission(); // Code for permission
                }
            } else {
                File dir = new File(Environment.getExternalStorageDirectory() + "/KetabyStore");
                if (dir.exists()) {
                    Log.d("path", dir.toString());
                    File list[] = dir.listFiles();
                    for (int i = 0; i < list.length; i++) {
                        arrayList.add(list[i].getName());
                        pathList.add(list[i].getPath());
                        downloadsLs.setAdapter(downloadsAdapter);
                    }
                }
            }

            downloadsLs = findViewById(R.id.dowmloadslistview);
            toolbar = findViewById(R.id.apptoolbar);
            toolbarbackicon = findViewById(R.id.backicon);
            toolbarTextView = findViewById(R.id.toolbarbookname);

            toolbarbackicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            toolbarTextView.setText("التحميلات");

            downloadsLs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getApplicationContext(), PdfViwerActivity.class);
                    intent.putExtra("SendPath",pathList.get(i));
                    startActivity(intent);
                }
            });

            registerForContextMenu(downloadsLs);



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DownloadsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(DownloadsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(DownloadsActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DownloadsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("اختر");
        getMenuInflater().inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int postion = info.position;
        if (item.getItemId() == R.id.deletbook) {

            File file=new File(pathList.get(postion));
            file.delete();

            downloadsAdapter.notifyDataSetChanged();


            Intent intent = getIntent();
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        }
        return super.onContextItemSelected(item);
    }
}




