package com.example.svita.p22;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    PopupMenu popup;
    TextView textak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textak=findViewById(R.id.textak);
        textak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                popup=new PopupMenu(MainActivity.this,v);
                MenuInflater inflater =popup.getMenuInflater();
                inflater.inflate(R.menu.munu,popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
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
                });
                return true;
            }
        });

    }

}
