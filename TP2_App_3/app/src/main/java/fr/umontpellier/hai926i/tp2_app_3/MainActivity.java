package fr.umontpellier.hai926i.tp2_app_3;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final double ACCELERATION_HIGH_THRESHOLD = 20;
    private static final double ACCELERATION_LOW_THRESHOLD = 11.5;

    private int lowAccelerationBackgroundColor;
    private int mediumAccelerationBackgroundColor;
    private int highAccelerationBackgroundColor;
    private int lowHighAccelerationTextColor;
    private int mediumAccelerationTextColor;


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView accelerometerValueTextView;
    private View mainActivityView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accelerometerValueTextView = findViewById(R.id.accelerometerValueTextView);
        mainActivityView = findViewById(R.id.main_activity_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            accelerometerValueTextView.setText(R.string.sensor_not_found);
            Toast.makeText(this, R.string.sensor_not_found, Toast.LENGTH_LONG).show();
        }

        loadColors();
    }

    private void loadColors() {
        lowAccelerationBackgroundColor = ContextCompat.getColor(this, R.color.low_acceleration_background_color);
        mediumAccelerationBackgroundColor = ContextCompat.getColor(this, R.color.medium_acceleration_background_color);
        highAccelerationBackgroundColor = ContextCompat.getColor(this, R.color.high_acceleration_background_color);
        lowHighAccelerationTextColor = ContextCompat.getColor(this, R.color.low_high_acceleration_text_color);
        mediumAccelerationTextColor = ContextCompat.getColor(this, R.color.medium_acceleration_text_color);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            //we register a listener for the accelerometer when the app screen is displayed for the user
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerometer != null) {
            //we unregister a listener for the accelerometer when the app screen isn't displayed anymore
            sensorManager.unregisterListener(this, accelerometer);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //nothing to do
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //this is were the interesting stuff happens, each time the sensor has a new reading
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //we take the measurements of acceleration
            double accelerationX = event.values[0];
            double accelerationY = event.values[1];
            double accelerationZ = event.values[2];

            double accelerationNorm = Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY + accelerationZ * accelerationZ);

            accelerometerValueTextView.setText(getString(R.string.accelerometer_value_format, accelerationNorm));

            if (accelerationNorm > ACCELERATION_HIGH_THRESHOLD) {
                mainActivityView.setBackgroundColor(highAccelerationBackgroundColor);
                accelerometerValueTextView.setTextColor(lowHighAccelerationTextColor);
            } else if (accelerationNorm < ACCELERATION_LOW_THRESHOLD) {
                mainActivityView.setBackgroundColor(lowAccelerationBackgroundColor);
                accelerometerValueTextView.setTextColor(lowHighAccelerationTextColor);
            } else {
                mainActivityView.setBackgroundColor(mediumAccelerationBackgroundColor);
                accelerometerValueTextView.setTextColor(mediumAccelerationTextColor);
            }
        }
    }


}