package com.example.rozjedprihlasovocku;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Mistnost extends AppCompatActivity {
    TextView textView;
    ImageView obraz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistnost);
        textView=findViewById(R.id.textView);
        obraz=findViewById(R.id.imageView);
        String textovac="";
        File[] files = getFilesDir().listFiles();
        textovac+="Size: "+ files.length;
        for (int i = 0; i < files.length; i++)
        {
            textovac+= "\nFileName:" + files[i].getName()+" jejich velikost: "+files[i].getUsableSpace();
        }
        textView.setText(textovac);
        Uri neco=(Uri)getIntent().getParcelableExtra("obrazek");
        obraz.setImageURI(neco);
        new File(neco.getPath());
        Toast.makeText(this,"soubor na ceste: "+neco.getPath()+" se nechce zobrazit",Toast.LENGTH_LONG).show();

    }
}
