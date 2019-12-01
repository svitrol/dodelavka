package com.example.svita.p20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity {

    Integer[] pole={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4};
    ViewAnimator simpleViewAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleViewAnimator=findViewById(R.id. simpleViewAnimator);
        for(int i=0;i<4;i++){
            ImageView image =new ImageView(getApplicationContext());
            image.setImageResource(pole[i]);
            simpleViewAnimator.addView(image);
        }
        simpleViewAnimator.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        simpleViewAnimator.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));


    }
    public void zpet(View v){
        simpleViewAnimator.showPrevious();

    }
    public void dalsi(View v){
        simpleViewAnimator.showNext();

    }
}
