package com.example.svita.p21;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textak=findViewById(R.id.textak);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menus){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menus);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.barva:{
                int barvak=textak.getCurrentTextColor();
                String barva=String.format("#%06X",(0xffffff &barvak));
                Toast.makeText(MainActivity.this,"Barva: "+barva,Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.velikost:{
                int vel=(int)textak.getTextSize()/3;
                Toast.makeText(MainActivity.this,"velikost: "+vel,Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.text:{
                String text=textak.getText().toString();
                Toast.makeText(MainActivity.this,"text: "+text,Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.konec:{
                finish();

            }
        }
        return true;
    }


}
