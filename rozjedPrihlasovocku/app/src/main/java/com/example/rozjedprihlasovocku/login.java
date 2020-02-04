package com.example.rozjedprihlasovocku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login(View view){
        EditText emailos=findViewById(R.id.emailos);
        EditText heslak=findViewById(R.id.heslous);
        HttpHandler handeler=new HttpHandler(this,emailos.getText().toString(),heslak.getText().toString());
        handeler.execute("http://svitacek-antonin.mzf.cz/teplomer/prihlaseni.php");

    }

    class HttpHandler extends AsyncTask<String,Void,Uzivatel> {
        AlertDialog alertDialog;
        Context ctx;
        private ListView lv;
        String emailos;
        String Heslo;
        public HttpHandler(Activity ctx,String emailos,String Heslo)
        {
            this.emailos=emailos;
            this.Heslo=Heslo;
            this.ctx =ctx;
            //lv =  ctx.findViewById(R.id.list);
        }
        String hodnota="";
        private String zkusToNaNetu(String login_url){
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("inputEmail","UTF-8")+"="+URLEncoder.encode(emailos,"UTF-8")+"&"+
                        URLEncoder.encode("inputPassword","UTF-8")+"="+URLEncoder.encode(Heslo,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //httpURLConnection.setReadTimeout(10000);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected Uzivatel doInBackground(String... params) {
            Uzivatel Pepan=null;
            Pepan=clientosDatabazosUzivatelos.getInstance(ctx).getAppDatabase().uzivatelousDao().getUzivatelePodleEmailu(emailos);
            if(Pepan!=null){
                if(Pepan.getHeslo().equals(Heslo)){
                    return Pepan;
                }
                String data=zkusToNaNetu(params[0]);
                if(data.equals("no")){
                    return null;
                }
                String []udaje=data.split(";");
                String mistnosti=Pepan.getMistnosti();
                Pepan=new Uzivatel(Integer.parseInt(udaje[0]),udaje[1],udaje[2],emailos,Heslo);
                Pepan.setMistnosti(mistnosti);
                clientosDatabazosUzivatelos.getInstance(ctx).getAppDatabase().uzivatelousDao().update(Pepan);

            }
            else {
                String data=zkusToNaNetu(params[0]);
                if(data.equals("no")){
                    return null;
                }
                String []udaje=data.split(";");
                Pepan=new Uzivatel(Integer.parseInt(udaje[0]),udaje[1],udaje[2],emailos,Heslo);
                clientosDatabazosUzivatelos.getInstance(ctx).getAppDatabase().uzivatelousDao().insert(Pepan);
                Toast.makeText(ctx,"Uzivatel byl přidán",Toast.LENGTH_LONG).show();
            }
            return Pepan;

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Uzivatel sePrihlasuje) {
            if(sePrihlasuje==null){
                Toast.makeText(ctx,"Přístup odepřen",Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(ctx,"Vítej "+sePrihlasuje.getPrijmeni(),Toast.LENGTH_LONG).show();
                Intent intentos=new Intent(ctx,MainActivity.class);
                intentos.putExtra("Uzivatelos",sePrihlasuje);
                ctx.startActivity(intentos);
                finish();
            }

        }
    }
}
