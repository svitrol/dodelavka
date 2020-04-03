package com.example.p01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView neco,asi;
    Sensor svetylko;
    ConstraintLayout plocha;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value=sensorEvent.values[0];
        int shade= Math.max(0,255-Math.min(255,(int)((Math.log(value+1)/7)*255)));
        asi.setText("svetylko: "+shade+"lx");
        plocha.setBackgroundColor(Color.rgb(255-shade,255-shade,255-shade));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plocha=findViewById(R.id.laxoutislav);
        neco=findViewById(R.id.textView);
        neco.setText("Senzory: ");
        asi=findViewById(R.id.textView2);
        SensorManager necky=(SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors=necky.getSensorList(Sensor.TYPE_ALL);
        for(Sensor cmuchal:sensors){
            neco.append(cmuchal.getName()+"\n");
        }
        svetylko=necky.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(svetylko!=null)((SensorManager)getSystemService(SENSOR_SERVICE)).unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(svetylko!=null)((SensorManager)getSystemService(SENSOR_SERVICE)).registerListener(this,svetylko,SensorManager.SENSOR_DELAY_FASTEST);
    }
}
