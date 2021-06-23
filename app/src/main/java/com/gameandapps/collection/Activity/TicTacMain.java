package com.gameandapps.collection.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;

import com.gameandapps.collection.Connection.ConnectionManager;
import com.gameandapps.collection.R;

public class TicTacMain extends AppCompatActivity {

    MediaPlayer mediaPlayer,mp1;

    RelativeLayout relativeLayout;

    ProgressBar progress;

    int activePlayer = 0;

    int red=0;

    int yellow=0;

    boolean gameActive = true;

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    int roundsCount=0;

    Uri backSoundUrl = Uri.parse("https://firebasestorage.googleapis.com/v0/b/video-9ba42.appspot.com/o/sound.mp3?alt=media&token=96a68acd-a6ec-49d0-99f6-834defe6f90a");

    ProgressDialog pd;

    public void dropIn(View view){


        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        Log.i("Tag",counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                mediaPlayer.pause();
                mp1.start();
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                mediaPlayer.pause();
                mp1.start();
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            mediaPlayer.start();
            roundsCount++;
            //counter.setImageResource(R.drawable.yellow);

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    String winner;

                    gameActive = false;
                    if (activePlayer == 1) {
                        winner = "Yellow has Won!";
                        yellow++;

                    } else {
                        winner = "Red has Won!";
                        red++;

                    }
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    roundsCount = 0;

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    TextView textView2 = (TextView) findViewById(R.id.textView2);

                    TextView textView3 = (TextView) findViewById(R.id.textView3);

                    TextView t1 = (TextView) findViewById(R.id.t1);

                    TextView t2 = (TextView) findViewById(R.id.t2);

                    winnerTextView.setText(winner);

                    t1.setText(""+red);

                    t2.setText(""+yellow);

                    playAgainButton.setVisibility(View.VISIBLE);

                    winnerTextView.setVisibility(View.VISIBLE);

                    t1.setVisibility(View.VISIBLE);

                    t2.setVisibility(View.VISIBLE);

                    textView2.setVisibility(View.VISIBLE);

                    textView3.setVisibility(View.VISIBLE);

                }
            }
        }else if(roundsCount > 8){
            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

            playAgainButton.setVisibility(View.VISIBLE);

            winnerTextView.setVisibility(View.VISIBLE);

            winnerTextView.setText("Math Draw!");

            mediaPlayer.pause();
        }
    }
    public void playAgain1(View view){

        mediaPlayer.start();

        mediaPlayer.setLooping(true);

        Button playAgainButton = findViewById(R.id.playAgainButton);

        TextView winnerTextView = findViewById(R.id.winnerTextView);

        TextView t1 = findViewById(R.id.t1);

        TextView t2 =  findViewById(R.id.t2);

        TextView textView2 =  findViewById(R.id.textView2);

        TextView textView3 = findViewById(R.id.textView3);

        playAgainButton.setVisibility(View.INVISIBLE);

        winnerTextView.setVisibility(View.INVISIBLE);

        t1.setVisibility(View.INVISIBLE);

        t2.setVisibility(View.INVISIBLE);

        textView2.setVisibility(View.INVISIBLE);

        textView3.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int j=0;j<gridLayout.getChildCount();j++ ){

            ImageView counter = (ImageView) gridLayout.getChildAt(j);

            counter.setImageDrawable(null);
        }

        for(int j=0;j<gameState.length; j++){
            gameState[j] = 2;
        }
        activePlayer = 0;

        gameActive = true;

        roundsCount = 0;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_main);

        relativeLayout = findViewById(R.id.relativeLayout);
        progress = findViewById(R.id.progress);

        getSupportActionBar().hide();

        relativeLayout.setVisibility(View.INVISIBLE);
        if (ConnectionManager.checkConnectivity(this)) {

            try {
                mediaPlayer = new MediaPlayer();
                mp1 = MediaPlayer.create(this, R.raw.buttonsound);

                DownloadAudioToUrl task = new DownloadAudioToUrl();
                task.execute((Void[]) null);
                //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //mediaPlayer.setDataSource(this,backSoundUrl);
                //mediaPlayer.prepare();
                //mediaPlayer.prepareAsync();
            /*mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                        relativeLayout.setVisibility(View.VISIBLE);
                    }else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                        relativeLayout.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
                //mediaPlayer.start();
                //mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        try {
                            if (!mp.isPlaying()) {
                                mp.start();
                                mp.setLooping(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //mp.start();
                        //mp.setLooping(true);
                        //relativeLayout.setVisibility(View.INVISIBLE);
                    }
                });*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Internet connection not found!");
            builder.setIcon(R.drawable.ic_about_app);
            builder.setTitle("Alert!");
            builder.setCancelable(false);

            builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    //ActivityCompat.finishAffinity(YouTubeActivity.class);
                }
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }
    }

    class DownloadAudioToUrl extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(TicTacMain.this);
            pd.setTitle(" Processing ");
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                mediaPlayer = MediaPlayer.create(TicTacMain.this,backSoundUrl);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //mediaPlayer.setDataSource(TicTacMain.this,backSoundUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(pd!=null){
                try {
                    pd.dismiss();
                    //mediaPlayer.prepareAsync();
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (!ConnectionManager.checkConnectivity(this)) {
                Toast.makeText(this, "No Internet Access", Toast.LENGTH_SHORT).show();
            } else {
                mediaPlayer.pause();
            }
        }catch(Exception e){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (ConnectionManager.checkConnectivity(this)) {
                mediaPlayer.start();
            } else {
                Toast.makeText(this, "No Internet Access", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            finish();
        }
    }



    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to Exit Game?");
        builder.setIcon(R.drawable.ic_about_app);
        builder.setTitle("Alert!");
        builder.setCancelable(false);

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}
