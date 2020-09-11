package com.example.ctecka.pridaniKnizky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ctecka.MainActivity;
import com.example.ctecka.R;
import com.example.ctecka.knizniDatabaze.Knizky.Knizka;

import java.util.Date;

public class pridaniI extends AppCompatActivity {
    Knizka Novinka;
    int metoda;
    RadioGroup osatka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridani_i);

        osatka=findViewById(R.id.radioGroup);
        osatka.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                LinearLayout neviditellni=findViewById(R.id.zpusobJedna);
                switch (i){
                    case R.id.rozsah: metoda=0;
                    neviditellni.setVisibility(View.VISIBLE);
                    break;
                    case R.id.radioButton2:
                        neviditellni.setVisibility(View.INVISIBLE);
                        metoda=1;
                }
            }
        });


    }
    public void nextStep(View view){
        Novinka=new Knizka();
        EditText radke=findViewById(R.id.nazevKnizky);
        Novinka.Title=radke.getText().toString();

        radke=findViewById(R.id.urlcko);
        Novinka.UrlZdroj=radke.getText().toString();
        Novinka.ZpusobZpracovani=metoda;

        Novinka.Pridani=new Date(System.currentTimeMillis());
        Novinka.PoslAktualizace=new Date(System.currentTimeMillis());

        if(metoda==0){
            radke=findViewById(R.id.odKapitol);
            Novinka.InstrukcePodleZpusobu=radke.getText().toString();

            radke=findViewById(R.id.doKapitol);
            Novinka.InstrukcePodleZpusobu+=radke.getText().toString();

            radke=findViewById(R.id.specialZnak);
            Novinka.InstrukcePodleZpusobu+=";"+radke.getText().toString();

        }
        Intent dalsiKrok=new Intent(pridaniI.this,pridaniII.class);
        dalsiKrok.putExtra("knizka",Novinka);
        startActivity(dalsiKrok);
        finish();
    }
}
