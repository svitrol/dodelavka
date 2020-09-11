package com.example.ctecka.pridaniKnizky;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class StahniMiToHtml extends AsyncTask<String,Void,String> {
    public interface ObdrzenoHtml{
        void zpracovaniHtml(String Html);

    }
    private String urlcko;
    ObdrzenoHtml deleSiStimCoChces;

    public StahniMiToHtml( String urlcko,ObdrzenoHtml deleSiStimCoChces) {

        this.urlcko = urlcko;
        this.deleSiStimCoChces = deleSiStimCoChces;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(urlcko);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            OutputStream outputStream = httpsURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//        String data = URLEncoder.encode("jmeno","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
//                URLEncoder.encode("heslo","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8")+"&"+
//                URLEncoder.encode("hodnota","UTF-8")+"="+URLEncoder.encode(hodnota,"UTF-8")+"&"+
//                URLEncoder.encode("od","UTF-8")+"="+URLEncoder.encode(odCasu,"UTF-8")+"&"+
//                URLEncoder.encode("do","UTF-8")+"="+URLEncoder.encode(doCasu,"UTF-8");

            //bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            //httpsURLConnection.setReadTimeout(10000);
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine())!=null)
            {
                response+= line;
            }
            bufferedReader.close();
            inputStream.close();
            httpsURLConnection.disconnect();
            return response;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
