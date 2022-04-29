package com.example.letgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private ImageView imgGorsel;
    private static int GECİS_SURESİ=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //TANIMLAMALAR

        imgGorsel=(ImageView)findViewById(R.id.imgGorsel);

        //ANİMASYON
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);

        imgGorsel.startAnimation(animation);

        //GEÇİŞ
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gecis=new Intent(SplashScreen.this,GirisActivity.class);
                startActivity(gecis);
                finish();
            }
        },GECİS_SURESİ);
    }
}
