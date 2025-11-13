package fr.umontpellier.hai926i.tp2_app_6_alt;


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

    private static final double ILLUMINANCE_THRESHOLD = 10;

    private static final int ANIMATION_DURATION_MS = 300;
    private static final double FAR_SCALE_FACTOR = 1.0;
    private static final double NEAR_SCALE_FACTOR = 2.0;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private ImageView illuminanceImageView;
    private TextView illuminanceTextView;


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

        illuminanceImageView = findViewById(R.id.illuminanceImageView);
        illuminanceTextView = findViewById(R.id.illuminanceTextView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, R.string.sensor_not_found, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            double illuminance = event.values[0];
            illuminanceTextView.setText(getString(R.string.illuminance_format, illuminance));

            boolean illuminanceState = (illuminance <= ILLUMINANCE_THRESHOLD);
            if (illuminanceState != objectIsNear) {
                objectIsNear = illuminanceState;
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

        illuminanceImageView.animate().scaleX((float) targetScaleFactor)
                .scaleY((float) targetScaleFactor)
                .setDuration(ANIMATION_DURATION_MS)
                .start();
    }
}