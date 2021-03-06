package com.example.jsonreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by prabeesh on 7/14/2015.
 */
public class HttpHandler extends AsyncTask<String,Void,String> {
    private String TAG = MainActivity.class.getSimpleName();
    AlertDialog alertDialog;
    Context ctx;
    private ListView lv;
    LineChart Graf;
    ArrayList<HashMap<String, String>> contactList;
    HttpHandler(Activity ctx,LineChart graf)
    {
        this.ctx =ctx;
        contactList = new ArrayList<>();
        Graf=graf;
        //lv =  ctx.findViewById(R.id.list);
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information....");
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://svitacek-antonin.mzf.cz/teplomer/teploty.php";
        String method = params[0];
        if(method.equals("login"))
        {
            String login_name = "svitaceka";
            String login_pass = "ef5002711";
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("jmeno","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("heslo","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8")+"&"+
                        URLEncoder.encode("hodnota","UTF-8")+"="+URLEncoder.encode("vlhkost","UTF-8")+"&"+
                        URLEncoder.encode("od","UTF-8")+"="+URLEncoder.encode("2019-11-29 11:44:31","UTF-8")+"&"+
                        URLEncoder.encode("do","UTF-8")+"="+URLEncoder.encode("2019-12-02 16:44:31","UTF-8");

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
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String data) {
        Log.e(TAG, "Response from url: " + data);
        ArrayList<Entry>yValues=new ArrayList<>();
        String []pole=data.split(";");
        int velikost=pole.length;
        String[] casy=new String[velikost];
        for(int i=0;i<velikost;i++){
            String[] housky=(pole[i]).split(",");
            /*Hodnota hodnotisk=new Hodnota(housky[0],housky[1],housky[2]);*/
            casy[i]=housky[2];
            yValues.add(new Entry(i,Float.parseFloat(housky[1])));
        }
        for (String radek:pole) {

        }
        Graf.setDragEnabled(true);
        Graf.setScaleEnabled(true);
        Graf.setDragEnabled(true);
        Graf.setPinchZoom(true);
        XAxis osaX=Graf.getXAxis();
        YAxis osaY=Graf.getAxisLeft();
        osaY.setValueFormatter(new MyValueFormatter("vlhkost"));

        LineDataSet set1=new LineDataSet(yValues,"Dtata set 1");
        set1.setFillAlpha(110);

        set1.setColor(Color.RED);
        set1.setLineWidth(3f);

        ArrayList<ILineDataSet>dataSets=new ArrayList<>();
        dataSets.add(set1);
        LineData datovaLajna=new LineData(dataSets);
        Graf.setData(datovaLajna);
        osaX.setValueFormatter(new  MyXAxisFormatter(casy));
        osaX.setLabelRotationAngle(15);
        Graf.invalidate();




        // adding contact to contact list

       /* ListAdapter adapter = new SimpleAdapter(ctx, contactList,
                R.layout.list_item, new String[]{ "teplota","cas"},
                new int[]{R.id.email, R.id.mobile});
        lv.setAdapter(adapter);*/
    }
    /*public class FormatProOsu implements IndexAxisValueFormatter{
        private String[] pole;
        public FormatProOsu(String[] pole){
            this.pole=pole;
        }

        @Override
        public String getFormattedValue(float value) {
            return pole[(int)value];
        }


    }*/
    class MyXAxisFormatter extends ValueFormatter {
        private String []pole;
        public MyXAxisFormatter(String[] pole){
            this.pole=pole;
        }


        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return pole[(int)value];
        }
    }
    class MyValueFormatter extends ValueFormatter {
        boolean Teplotu=true;
        public MyValueFormatter(String hodnoty){
            Teplotu=hodnoty.equals("teplota");
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            if(Teplotu)return String.format("%1.2f°C",value);
            return String.format("%1.0f%c",value,'%');
        }

       /* // override this for custom formatting of XAxis or YAxis labels
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return format.format(value)
        }*/
        // ... override other methods for the other chart types
    }

}