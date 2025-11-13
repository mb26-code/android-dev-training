package fr.umontpellier.hai926i.tp2_app_4;


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

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final double LINEAR_ACCELERATION_NORM_THRESHOLD = 0.8;
    //if the norm (or magnitude) of the linear acceleration vector exceeds this value
    //then the app will consider the smartphone is moving


    private static final double ALPHA = 0.6;
    //a smoothing factor making it so that the change of direction of the arrow is smoother
    //by taking into account the last measured values of linear acceleration
    //0.6 = 60% old value, 20% new value.
    //the closer to 1.0, the smoother the switch in direction is but the response will be slower.

    private double smoothedLinearAccelerationX = 0.0;
    private double smoothedLinearAccelerationY = 0.0;


    private SensorManager sensorManager;
    private Sensor linearAccelerationSensor;

    private View dotView;
    //the fixed dot at the center of the screen when the smartphone is not moving
    private ImageView arrowView;
    //the arrow at the center of the screen pointing in the direction towards which the smartphone is moving

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

        dotView = findViewById(R.id.dotView);
        arrowView = findViewById(R.id.arrowView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        if (linearAccelerationSensor == null) {
            //if there is no linear acceleration sensor available, we inform the user with a toast message
            Toast.makeText(this, R.string.sensor_not_found, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (linearAccelerationSensor != null) {
            sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_UI);
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
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            //we only care about the movement direction in the plane of the smartphone's screen surface
            //therefore, we only consider the measurements of the sensor on the X and Y axes
            //(see coordinate system of the Sensor API figure)

            //we apply a low-pass filter to smooth out the unstable and noisy raw sensor data.
            //if we don't do this the arrow goes nuts during each movement which isn't pleasing to look at
            smoothedLinearAccelerationX = smoothedLinearAccelerationX * ALPHA + event.values[0] * (1.0 - ALPHA);
            smoothedLinearAccelerationY = smoothedLinearAccelerationY * ALPHA + event.values[1] * (1.0 - ALPHA);
            double linearAccelerationX = smoothedLinearAccelerationX;
            double linearAccelerationY = smoothedLinearAccelerationY;

            double linearAccelerationNorm = Math.sqrt(linearAccelerationX * linearAccelerationX + linearAccelerationY * linearAccelerationY);

            if (linearAccelerationNorm < LINEAR_ACCELERATION_NORM_THRESHOLD) {
                //we consider the smartphone to not be moving
                //so we set the arrow not visible and the center dot visible
                dotView.setVisibility(View.VISIBLE);
                arrowView.setVisibility(View.GONE);
            } else {
                //we consider the smartphone to be moving
                //so we set the arrow visible and the center dot not visible
                dotView.setVisibility(View.GONE);
                arrowView.setVisibility(View.VISIBLE);

                //and then we rotate the arrow accordingly
                double arrowDirectionAngle = (90.0 - Math.toDegrees(Math.atan2(-linearAccelerationY, -linearAccelerationX)));
                arrowView.setRotation((float) arrowDirectionAngle);
            }
        }
    }
}