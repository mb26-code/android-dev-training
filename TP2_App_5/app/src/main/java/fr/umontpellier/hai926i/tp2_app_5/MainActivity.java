package fr.umontpellier.hai926i.tp2_app_5;


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
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.view.View;
import android.widget.Toast;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor linearAccelerationSensor;

    private CameraManager cameraManager;
    private String cameraId;

    private View mainActivityView;

    private boolean isFlashOn = false;

    private static final double SHAKE_ACCELERATION_THRESHOLD = 10.0;
    private static final long SHAKE_EFFECT_COOLDOWN_MS = 1000;
    private long lastShakeTime = 0;


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

        mainActivityView = findViewById(R.id.main_activity_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        if (linearAccelerationSensor == null) {
            Toast.makeText(this, R.string.sensor_not_found, Toast.LENGTH_LONG).show();
        }

        prepareCameraFlash();
    }

    private void prepareCameraFlash() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Toast.makeText(this, R.string.camera_access_error, Toast.LENGTH_SHORT).show();
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
        if (isFlashOn) {
            //if the user goes off the app and the flash is on, we turn it off (no need to waste battery here)
            turnFlashOff();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //nothing to do
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            double linearAccelerationX = event.values[0];
            double linearAccelerationY = event.values[1];
            double linearAccelerationZ = event.values[2];

            double linearAccelerationNorm = Math.sqrt(linearAccelerationX * linearAccelerationX +
                    linearAccelerationY * linearAccelerationY +
                    linearAccelerationZ * linearAccelerationZ
            );

            //we need to have a cooldown for the shake to have an effect, otherwise things get messy
            //with the flash going on and off uncontrollably
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastShakeTime) > SHAKE_EFFECT_COOLDOWN_MS) {
                //and for acceleration we need to exceed a minimum value to have a true shake
                if (linearAccelerationNorm > SHAKE_ACCELERATION_THRESHOLD) {
                    lastShakeTime = currentTime;

                    if (isFlashOn) {
                        turnFlashOff();
                    } else {
                        turnFlashOn();
                    }
                    isFlashOn = !isFlashOn;
                }
            }
        }
    }

    private void turnFlashOn() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            mainActivityView.setBackgroundColor(ContextCompat.getColor(this, R.color.flash_on_background_color));
            //when turning the flash on, we also change the screen's background color
        } catch (CameraAccessException e) {
            Toast.makeText(this, R.string.camera_access_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void turnFlashOff() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            mainActivityView.setBackgroundColor(ContextCompat.getColor(this, R.color.flash_off_background_color));
            //likewise for turning the flash off
        } catch (CameraAccessException e) {
            Toast.makeText(this, R.string.camera_access_error, Toast.LENGTH_SHORT).show();
        }
    }
}