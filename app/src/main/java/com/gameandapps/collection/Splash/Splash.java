package com.gameandapps.collection.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gameandapps.collection.MainActivity.MainActivity;
import com.gameandapps.collection.R;

public class Splash extends AppCompatActivity {

    Handler handler;
    ImageView imageView;
    TextView textView,loadingTextView;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.img_logo);
        textView = findViewById(R.id.textView);
        loadingTextView = findViewById(R.id.loading);

        imageView.setTranslationY(-1500);

        imageView.animate().rotationY(1800).translationYBy(1500).setDuration(1500);

        textView.setTranslationX(-1500);

        textView.animate().rotationY(1800).translationXBy(1500).setDuration(1500);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);

        loadingTextView.startAnimation(animation);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}
