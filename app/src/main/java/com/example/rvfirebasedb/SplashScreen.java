package com.example.rvfirebasedb;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    ImageView logoImage;
    TextView slogan;
    RelativeLayout appNameRv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        logoImage=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);
        appNameRv=findViewById(R.id.relativeappname);

        Animation myanime= AnimationUtils.loadAnimation(this,R.anim.fromtopanime);
        Animation anime=AnimationUtils.loadAnimation(this,R.anim.textfrombottom);
        logoImage.startAnimation(anime);
        appNameRv.startAnimation(myanime);
        slogan.startAnimation(myanime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                finish();
            }
        },7000);



    }

}
