package com.example.testatorintentu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void registrujsa(View v){
        Intent neco=new Intent(this,registrace.class);
        startActivity(neco);

    }
    public void prihlassa(View v){
        Intent neco=new Intent(this,prihlaseni.class);
        startActivity(neco);

    }
    public void ukoncisa(View v){
        finish();
    }
}
