package com.example.testatorintentu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class prihlaseni extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prihlaseni);
    }
    public void login(View v){
        EditText emailos=findViewById(R.id.emailos);
        EditText heslak=findViewById(R.id.heslous);
        najdiUzivatele jed=new najdiUzivatele(this,emailos.getText().toString(),heslak.getText().toString());
        jed.execute();
    }

    class najdiUzivatele extends AsyncTask<String,Void,Uzivatel>{
        AlertDialog alertDialog;
        Context ctx;
        private ListView lv;
        String emailos;
        String Heslo;
        public najdiUzivatele(Activity ctx, String emailos, String Heslo)
        {
            this.emailos=emailos;
            this.Heslo=Heslo;
            this.ctx =ctx;
            //lv =  ctx.findViewById(R.id.list);
        }
        @Override
        protected Uzivatel doInBackground(String... strings) {
            Uzivatel Pepan=null;
            Pepan=clientosDatabazosUzivatelos.getInstance(ctx).getAppDatabase().uzivatelousDao().getUzivatelePodleEmailu(emailos);
            if(Pepan!=null){
                if(Pepan.getHeslo().equals(Heslo)){
                    return Pepan;
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Uzivatel sePrihlasuje) {
            if(sePrihlasuje==null){
                Toast.makeText(ctx,"Přístup odepřen",Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(ctx,"Vítej "+sePrihlasuje.getPrijmeni(),Toast.LENGTH_LONG).show();
                Intent intentos=new Intent(ctx,testovka1.class);
                intentos.putExtra("Uzivatelos",sePrihlasuje);
                ctx.startActivity(intentos);
                finish();
            }

        }
    }
}
