package com.example.svita.p19;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SeekBar ukazatel;
    TextView kde,celek;
    boolean jede=false;
    MediaPlayer prehravac=new MediaPlayer();
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ukazatel = findViewById(R.id.seekBar);
        kde = findViewById(R.id.textView);
        celek = findViewById(R.id.textView2);
        ukazatel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    prehravac.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private Runnable opakovani = new Runnable() {
        @Override
        public void run() {
            int cas=prehravac.getCurrentPosition();
            ukazatel.setProgress(cas);
            kde.setText(prevod(cas));
            handler.postDelayed(this, 1000);
        }
    };
    static public String prevod(int cas){
        cas=cas/1000;
        int min=cas/60;
        int sec=cas%60;
        return " "+min+" : "+sec;

    }

    public void start(View v){

        if(jede==true){
            prehravac.reset();
        }
        prehravac= MediaPlayer.create(this,R.raw.songac);
        prehravac.start();
        jede=true;
        celek.setText(prevod(prehravac.getDuration()));
        ukazatel.setMax(prehravac.getDuration());
        handler.postDelayed(opakovani, 1000);
    }
    public void pauza(View v){
        if(jede){
            prehravac.pause();
            jede=false;
        }
        else{
            prehravac.start();
            jede=true;
        }
    }
    public void Fposun(View v){
        int neco=prehravac.getCurrentPosition();
        prehravac.seekTo(neco+4000);
        handler.postDelayed(opakovani, 0);
    }
    public void Bposun(View v){
        int neco=prehravac.getCurrentPosition();
        prehravac.seekTo(neco-4000);
        handler.postDelayed(opakovani, 0);
    }



}
