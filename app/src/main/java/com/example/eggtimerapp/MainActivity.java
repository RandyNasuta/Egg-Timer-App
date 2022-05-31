package com.example.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerText;
    SeekBar timerSeekbar;
    Button button;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    int timeNow = 0;

    public void resetTimer(){
        counterIsActive = false;
        timerSeekbar.setEnabled(true);
        button.setText("GO");
        countDownTimer.cancel();
        timerSeekbar.setProgress(30);
        timerText.setText("0:30");
    }

    public void buttonPress(View view){

        if(counterIsActive){
            resetTimer();
        }else {
            counterIsActive = true;
            button.setText("STOP");
            timerSeekbar.setEnabled(false);

            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);

                }

                @Override
                public void onFinish() {
                    MediaPlayer audio = MediaPlayer.create(getApplicationContext(), R.raw.air_horn_sound);
                    audio.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondLeft){
        int minutes = secondLeft/60;
        int seconds = secondLeft - (minutes*60);

        String secondString = Integer.toString(seconds);

        if(seconds <= 9){
            secondString = "0" + secondString;
        }

        timerText.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.mySeekBar);
        timerText = (TextView) findViewById(R.id.timerTextView);
        button = (Button) findViewById(R.id.myButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}