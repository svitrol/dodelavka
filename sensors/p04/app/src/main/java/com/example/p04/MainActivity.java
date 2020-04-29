package com.example.p04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    Sensor gyroskopista;
    SensorManager master;
    TextView label,body;
    ImageView android,cil;
    int celkovyBody=0;
    int px,py,pz,maxx,maxy;


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String text="";

        float chtenaPozicex=px-9-sensorEvent.values[0]*1000;
        float chetnaPoziceY=py-22-sensorEvent.values[1]*1000;

        android.setX(chtenaPozicex);
        android.setY(chetnaPoziceY);
        android.setZ(pz-258-sensorEvent.values[2]*1000);
        text+=chtenaPozicex+"\n";
        text+=chetnaPoziceY+"\n";
        text+=pz-258-sensorEvent.values[2]*1000;
        label.setText(text);

        if(prekryvajSeObjekty(android,cil)){
            celkovyBody++;
            body.setText(""+celkovyBody);
            Random cislo=new Random();
            cil.setX(cislo.nextInt(maxx));
            cil.setY(cislo.nextInt(maxy));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        master=(SensorManager)getSystemService(SENSOR_SERVICE);
        gyroskopista=master.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        label=findViewById(R.id.hovadina);
        android=findViewById(R.id.imageView);
        cil=findViewById(R.id.imageView2);
        body=findViewById(R.id.textView);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        px= size.x/2;
        py = size.y/2;
        pz=0;
        maxx=size.x;
        maxy=size.y;
    }

    @Override
    protected void onResume() {
        super.onResume();
        master.registerListener(this,gyroskopista,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(master!=null)master.unregisterListener(this);
    }
    public boolean prekryvajSeObjekty(View jedna,View dva){
        Interval jednaX=new Interval(jedna.getX(),jedna.getX()+jedna.getWidth());
        Interval jednay=new Interval(jedna.getY(),jedna.getY()+jedna.getHeight());
        Interval dvax=new Interval(dva.getX(),dva.getX()+dva.getWidth());
        Interval dvay=new Interval(dva.getY(),dva.getY()+dva.getHeight());
        return jednaX.prekryvaSeS(dvax)&&jednay.prekryvaSeS(dvay);


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    class Interval{
        Float start,konec;

        public Interval(float start, float konec) {
            this.start = start;
            this.konec = konec;
        }
        public boolean prekryvaSeS(Interval dalsi){
            if (dalsi == null) return false; // for readability's sake, this condition is pulled out

            // overlap happens ONLY when this's end is on the right of other's start
            // AND this's start is on the left of other's end.
            return (((this.konec == null) || (dalsi.start == null) || (this.konec.intValue() >= dalsi.start.intValue())) &&
                    ((this.start == null) || (dalsi.konec == null) || (this.start.intValue() <= dalsi.konec.intValue())));
        }
    }
}
