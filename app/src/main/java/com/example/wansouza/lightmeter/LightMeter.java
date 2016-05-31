package com.example.wansouza.lightmeter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LightMeter extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLight;

    private TextView lightValue;
    private TextView resultText;
    private ImageView imageView;

    private float currentLux = 0;

    private float maxLux;
    private final double MAXALPHA = 1;

    private double alpha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_meter);

        lightValue = (TextView) findViewById(R.id.lightValue);
        resultText = (TextView) findViewById(R.id.resultText);
        imageView = (ImageView) findViewById(R.id.imageView);

        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        maxLux = mLight.getMaximumRange();
        mSensorManager.registerListener((SensorEventListener) this, mLight, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_LIGHT) {
        }
    }

    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            currentLux = (float) event.values[0];

            NumberFormat formatter = new DecimalFormat("#0");
            lightValue.setText( formatter.format(currentLux) );

            alpha = ( (currentLux * MAXALPHA) / maxLux ) * 100;
            imageView.setAlpha((float) alpha);

            resultText.setText( getResultText(currentLux) );

            /* Logs */
            Log.e("LightMeter", "currentLux = " + formatter.format(currentLux));
            Log.e("LightMeter", "Alpha: " + alpha );

        }

    }

    private String getResultText(double luxValue){
        String result = "Mínimo para ";

        if (luxValue < 50){
            result = "Condições de iluminação muito abaixo do mínimo.";
        } else if(luxValue >= 50 && luxValue < 100){
            result += "garagem e luz geral de quarto ou sala.";
        } else if(luxValue >= 100 && luxValue < 150){
            result += "escadaria e luz geral de banheiro.";
        } else if(luxValue >= 150 && luxValue < 200){
            result += "hall e áreas de circulação.";
        } else if(luxValue >= 200 && luxValue < 300){
            result += "iluminar espelho de banheiro.";
        } else if(luxValue >= 300 && luxValue < 400){
            result += "mesa de trabalho, para ler, estudar ou costurar.";
        } else if(luxValue >= 400 && luxValue < 600){
            result += "ambientes de trabalho como cozinha.";
        } else {
            result = "Ambientes com iluminação excessiva.";
        }

        Log.e("LightMeter", "resultText: " + result );

        return result;

    }

}