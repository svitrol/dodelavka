package com.example.testatorintentu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class registrace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrace);
    }
    public void register(View v){
        EditText emailos=findViewById(R.id.emailos);
        EditText heslak=findViewById(R.id.heslous);
        EditText jmeno=findViewById(R.id.jmeno);
        EditText prijmeni=findViewById(R.id.prijmeni);
        Uzivatel Pepan=new Uzivatel(jmeno.getText().toString(),
                prijmeni.getText().toString(),
                emailos.getText().toString(),
                heslak.getText().toString());
        registracka neco=new registracka(this,Pepan);
        neco.execute();

    }
    class registracka extends AsyncTask<String,Void,Uzivatel>{
        Context ctx;
        Uzivatel novy;

        public registracka(Context ctx, Uzivatel novy) {
            this.ctx = ctx;
            this.novy = novy;
        }

        @Override
        protected Uzivatel doInBackground(String... strings) {
            Uzivatel Pepan=null;
            Pepan=clientosDatabazosUzivatelos.getInstance(ctx).getAppDatabase().uzivatelousDao().getUzivatelePodleEmailu(novy.getEmail());
            if(Pepan==null){
                clientosDatabazosUzivatelos.getInstance(ctx).getAppDatabase().uzivatelousDao().insert(novy);
                return novy;
            }
            return null;
        } @Override
        protected void onPostExecute(Uzivatel seRegistruje) {
            if(seRegistruje==null){
                Toast.makeText(ctx,"Email již existuje",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(ctx,"Vítej novy uzivateli",Toast.LENGTH_LONG).show();
                Intent intentos=new Intent(ctx,MainActivity.class);
                ctx.startActivity(intentos);
                finish();
            }

        }
    }
}
