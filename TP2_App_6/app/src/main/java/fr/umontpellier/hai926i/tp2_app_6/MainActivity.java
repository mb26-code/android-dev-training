package fr.umontpellier.hai926i.tp2_app_6;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int ANIMATION_DURATION_MS = 300;
    private static final double FAR_SCALE_FACTOR = 1.0;
    private static final double NEAR_SCALE_FACTOR = 2.0;

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private ImageView proximityImageView;
    private TextView distanceTextView;


    private double proximitySensorMaxRange;
    private boolean objectIsNear = false;


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

        proximityImageView = findViewById(R.id.proximityImageView);
        distanceTextView = findViewById(R.id.distanceTextView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            Toast.makeText(this, R.string.sensor_not_found, Toast.LENGTH_LONG).show();
            finish();
        } else {
            proximitySensorMaxRange = proximitySensor.getMaximumRange();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //nothing to do
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            double objectDistance = event.values[0];
            distanceTextView.setText(getString(R.string.distance_format, objectDistance));
            boolean proximityState = (objectDistance < proximitySensorMaxRange);

            if (proximityState != objectIsNear) {
                objectIsNear = proximityState;
                smoothScaleProximityImage(objectIsNear);
            }
        }
    }

    private void smoothScaleProximityImage(boolean objectIsNear) {
        double targetScaleFactor;
        if (objectIsNear){
            targetScaleFactor = NEAR_SCALE_FACTOR;
        } else {
            targetScaleFactor = FAR_SCALE_FACTOR;
        }

        proximityImageView.animate().scaleX((float) targetScaleFactor)
                .scaleY((float) targetScaleFactor)
                .setDuration(ANIMATION_DURATION_MS)
                .start();
    }
}