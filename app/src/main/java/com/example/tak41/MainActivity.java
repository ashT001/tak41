package com.example.tak41;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.LocaleData;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText initialduration;
    EditText restduration;
    TextView timeleft;
    TextView resttimeleft;
    Button starttimer;
    Button stoptimer;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        timeleft = findViewById(R.id.timeleft);
        starttimer = findViewById(R.id.starttimer);
        stoptimer = findViewById(R.id.stoptimer);
        progress = findViewById(R.id.progressBar2);
        resttimeleft = findViewById(R.id.timeleft2);


        starttimer.setOnClickListener(new View.OnClickListener() { // flow of code is start_button>startcount(initialduramt)>fin>restcount(initialrestamt)
                                                                    // if at any moment stopbutton is called, chain is broken and all vals reset based off initial enetered params.

            @Override
            public void onClick(View view) {
                restduration = findViewById(R.id.restdur);
                String restduramtStr = String.valueOf(restduration.getText()); //assign base value of the initial rest duration
                long restduramt = Long.parseLong(restduramtStr);                //assign to this long

                initialduration = findViewById(R.id.initialdur);
                String initialduramtStr = String.valueOf(initialduration.getText()); //assign base value of the initial amount duration
                long initialduramt = Long.parseLong(initialduramtStr);                  //assign to this long

                progress.setMax((int) initialduramt*1000);
                progress.setProgress(progress.getMax());

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



                Log.d("tag1", String.valueOf(restduramt));
                CountDownTimer restcount = new CountDownTimer(restduramt*1000, 1000) { //assign restcount and startcount to variables so x.cancel() can be called on finbutton call and x.start can be called at the end of the setcount

                    @Override
                    public void onTick(long l) {
                        resttimeleft.setText("Time until finished rest:: "+(l/1000));
                        progress.setProgress((int) l);
                        Log.d("mytag", String.valueOf(l));

                    }

                    @Override

                    public void onFinish() {
                        v.vibrate(400);
                    }
                };



                CountDownTimer startcount = new  CountDownTimer(initialduramt*1000, 1000) {

                    @Override
                    public void onTick(long l) {
                        timeleft.setText("Time remaining: "+(l/1000));
                        progress.setProgress((int) l);
                        Log.d("mytag", String.valueOf(l));

                    }

                    @Override
                    public void onFinish() {
                        timeleft.setText("done!");
                        progress.setMax((int) restduramt*1000);
                        progress.setProgress(progress.getMax());
                        restcount.start();
                    }
                }.start();


                stoptimer.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        startcount.cancel();
                        restcount.cancel();
                    }
                });



            }
        });
    }
}



