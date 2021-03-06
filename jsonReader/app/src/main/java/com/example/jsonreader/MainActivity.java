package com.example.jsonreader;

import android.arch.lifecycle.AndroidViewModel;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    HttpHandler rozjedTo;
    private LineChart Graf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Graf=findViewById(R.id.graf);
        //Graf.setOnChartGestureListener(MainActivity.this);
        //Graf.setOnChartValueSelectedListener(MainActivity.this);



        rozjedTo=new HttpHandler(this, Graf);
        rozjedTo.execute("login");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            //getActionBar().hide();
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
    }

}