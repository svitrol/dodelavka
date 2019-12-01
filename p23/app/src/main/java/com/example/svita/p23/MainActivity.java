package com.example.svita.p23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText jmeno,heslo;
    TextView pokusy;
    byte pocet=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jmeno=findViewById(R.id.editText);
        heslo=findViewById(R.id.editText2);
        pokusy=findViewById(R.id.textView3);
        pokusy.setText("počet zbývajících pokusů: "+pocet);
    }
    public void prihlaska(View v){
        String jm=jmeno.getText().toString(),hes=heslo.getText().toString();
        if(jm.equals("jmeno")&&hes.equals("heslo")){
            Toast.makeText(this,"Jméno a heslo jsou správné",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(".www");
            startActivity(intent);

        }
        else{
            Toast.makeText(this,"Jméno a heslo nejsou správné",Toast.LENGTH_SHORT).show();
            pocet--;
            pokusy.setText("počet zbývajících pokusů: "+pocet);
            String vse = "Jméno: " + jm + "\nHeslo: " + hes;
            final Intent intent = new Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT,vse);
            startActivity(intent);


        }
    }
}
