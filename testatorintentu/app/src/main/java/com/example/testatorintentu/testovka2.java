package com.example.testatorintentu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class testovka2 extends AppCompatActivity {
    String spravnaOdpoved="123456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testovka2);
        EditText neco=findViewById(R.id.editText);
        neco.setText(getIntent().getStringExtra("odpoved2"));
    }
    public void zpet(View v){
        Intent neco=new Intent(this,testovka1.class);
        neco.putExtra("odpoved1",getIntent().getStringExtra("odpoved1"));
        neco.putExtra("spravnost1",getIntent().getBooleanExtra("spravnost1",false));
        EditText naco=findViewById(R.id.editText);
        neco.putExtra("odpoved2",naco.getText().toString());
        neco.putExtra("spravnost2",getIntent().getBooleanExtra("spravnost2",false));
        startActivity(neco);

    }
    public void hodnotit(View v){
        EditText naco=findViewById(R.id.editText);
        if(naco.getText().toString().equals(spravnaOdpoved)){
            Toast.makeText(this,"prvni tázka byla dobře?  "+getIntent().getBooleanExtra("spravnost1",false)+"odpovedel jsi: "+getIntent().getStringExtra("odpoved1")+
                    "\n Druhá otázka byla dobře a odpovedel jsi: "+spravnaOdpoved+"" +
                    "\n uděluji ti známku 1",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"prvni tázka byla dobře?  "+getIntent().getBooleanExtra("spravnost1",false)+"odpovedel jsi: "+getIntent().getStringExtra("odpoved1")+
                    "\n Druhá otázka byla špatně a odpovedel jsi: "+naco.getText().toString()+"" +
                    "\n uděluji ti známku 3",Toast.LENGTH_LONG).show();
        }

    }
}
