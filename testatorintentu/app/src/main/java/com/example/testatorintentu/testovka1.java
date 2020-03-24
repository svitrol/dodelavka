package com.example.testatorintentu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class testovka1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testovka1);
        RadioButton ne= findViewById(R.id.radioButton);
        ne.setChecked(!getIntent().getBooleanExtra("spravnost1",false));
    }
    public void jednicka(View v){
        String odpoved;
        boolean spravnoct;
       RadioButton ne= findViewById(R.id.radioButton);
       spravnoct=!ne.isChecked();
       if(spravnoct){
           odpoved="Ano";
       }
       else{
           odpoved="ne";
       }
       Intent neco=new Intent(this,testovka2.class);
       neco.putExtra("odpoved1",odpoved);
       neco.putExtra("spravnost1",spravnoct);
       neco.putExtra("odpoved2",getIntent().getStringExtra("odpoved2"));
       neco.putExtra("spravnost2",getIntent().getBooleanExtra("spravnost2",false));
       startActivity(neco);

    }
}
