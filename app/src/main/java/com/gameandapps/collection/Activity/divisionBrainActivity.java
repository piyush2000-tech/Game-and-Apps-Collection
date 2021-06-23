package com.gameandapps.collection.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gameandapps.collection.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class divisionBrainActivity extends AppCompatActivity {
    Button goButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    TextView resultTextView;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView divideTextView;
    TextView timerTextView;
    Button playAgainButton;
    ConstraintLayout gameLayout;
    MediaPlayer mp1,mp2;
    CountDownTimer countDownTimer;
    long secondsLeft1;

    public void playAgain(View view) {
        try {
            score = 0;
            numberOfQuestions = 0;
            divideTextView.setText("");
            //timerTextView.setText("30s");
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            newQuestion();
            playAgainButton.setVisibility(View.INVISIBLE);
            resultTextView.setText("");
            secondsLeft1 = 30;
            countDown();
        }catch (Exception e){
            finish();
        }
    }

    public void countDown(){
        try {
            countDownTimer = new CountDownTimer((secondsLeft1 * 1000) + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                    secondsLeft1 = l / 1000;
                    button0.setEnabled(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                }

                @Override
                public void onFinish() {
                    resultTextView.setText("Done!");
                    button0.setEnabled(false);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }.start();
        }catch (Exception e){
            finish();
        }
    }

    public void chooseAnswer(View view) {
        try {


            if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
                resultTextView.setText("Correct:)");
                mp1.start();
                score++;
            } else {
                resultTextView.setText("Wrong :(");
                mp2.start();
            }
            numberOfQuestions++;
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            newQuestion();
        }catch (Exception e){
            finish();
        }
    }

    public void start(View view) {
        try {
            goButton.setVisibility(View.INVISIBLE);
            gameLayout.setVisibility(View.VISIBLE);
            playAgain(findViewById(R.id.timerTextView));
        }catch (Exception e){
            finish();
        }

    }

    public void newQuestion() {
        try {
            Random rand = new Random();
            int a = rand.nextInt(21);
            int b = rand.nextInt(21);

            divideTextView.setText(Integer.toString(a) + " %  " + Integer.toString(b));

            locationOfCorrectAnswer = rand.nextInt(4);

            answers.clear();

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers.add(a % b);
                } else {
                    int wrongAnswer = rand.nextInt(41);

                    try {
                        while (wrongAnswer == a % b) {
                            wrongAnswer = rand.nextInt(41);
                        }
                    } catch (Exception e) {
                        Log.i("Error", "Divide by zero");
                        finish();
                    }

                    answers.add(wrongAnswer);
                }

            }

            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));
        }catch (Exception e){
            Log.i("Error", "Divide by zero");
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_brain);

        getSupportActionBar().setTitle("Reminder Game");

        try {
            divideTextView = findViewById(R.id.sumTextView);
            button0 = findViewById(R.id.button0);
            button1 = findViewById(R.id.button1);
            button2 = findViewById(R.id.button2);
            button3 = findViewById(R.id.button3);
            resultTextView = findViewById(R.id.resultTextView);
            scoreTextView = findViewById(R.id.scoreTextView);
            timerTextView = findViewById(R.id.timerTextView);
            playAgainButton = findViewById(R.id.playAgainButton);
            gameLayout = findViewById(R.id.gameLayout);
            goButton = findViewById(R.id.goButton);

            goButton.setVisibility(View.VISIBLE);
            gameLayout.setVisibility(View.INVISIBLE);

            goButton.setBackgroundColor(Color.parseColor("#3F51B5"));

            gameLayout.setBackgroundColor(Color.parseColor("#00574B"));

            mp1 = MediaPlayer.create(this, R.raw.correct);
            mp2 = MediaPlayer.create(this, R.raw.wrong);
        }catch (Exception e){
            finish();
        }
    }

    public void updateTimer(int secondsLeft){
        //s = secondsLeft;
        try {
            int minutes = secondsLeft / 60;
            int seconds = secondsLeft - (minutes * 60);
            String secondsString = Integer.toString(seconds);

            if (seconds <= 9) {
                secondsString = "0" + secondsString;
            }
            timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
        }catch (Exception e){
            Log.i("Error", Objects.requireNonNull(e.getMessage()));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(!goButton.isShown()) {
            try {
                countDownTimer.cancel();
                updateTimer((int) secondsLeft1);
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
                        countDown();
                        //ActivityCompat.finishAffinity(YouTubeActivity.class);
                    }
                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }catch (Exception e){
                finish();
            }
        }else{
            super.onBackPressed();
        }
    }
}
