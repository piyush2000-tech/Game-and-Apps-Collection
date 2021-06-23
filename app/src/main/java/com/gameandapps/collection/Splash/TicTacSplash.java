package com.gameandapps.collection.Splash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gameandapps.collection.R;
import com.gameandapps.collection.Activity.TicTacMain;


public class TicTacSplash extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tic_tac_splash);

        getSupportActionBar().hide();

        ImageView counter = (ImageView) findViewById(R.id.imageView);

        counter.setTranslationY(-1500);

        counter.animate().translationYBy(1500).rotation(3600).setDuration(500);
        counter.setImageResource(R.drawable.logo);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TicTacSplash.this, TicTacMain.class);
                startActivity(intent);
                finish();
            }
        },6000);
    }
}
