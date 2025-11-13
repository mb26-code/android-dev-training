package fr.umontpellier.hai926i.tp2_app_1;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import android.widget.TextView;

import java.util.List;
import java.util.Locale;

//the activity linked to the screen displaying detailed information about a specific sensor
public class SensorDetailsActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sensor_details_activity_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int sensorTypeId = getIntent().getIntExtra("SENSOR_TYPE_ID", -1);
        String sensorTypeName = getIntent().getStringExtra("SENSOR_TYPE_NAME");

        if (sensorTypeId == -1 || sensorTypeName == null) {
            finish();
            return;
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(sensorTypeId);
        for (Sensor sensor : sensors) {
            if (sensor.getName().equals(sensorTypeName)) {
                this.sensor = sensor;
                break;
            }
        }

        if (sensor == null) {
            //sensor not found (not supposed to happen since only existing sensors are on the main screen)
            finish();
            return;
        }

        settingViewText();
    }

    private void settingViewText() {
        //adding information about the sensor by setting the text content of some view components of the activity layout

        ((TextView) findViewById(R.id.tvDetailName)).setText(sensor.getName());
        ((TextView) findViewById(R.id.tvDetailVendor)).setText(sensor.getVendor());
        ((TextView) findViewById(R.id.tvDetailType)).setText(String.valueOf(sensor.getType()));
        ((TextView) findViewById(R.id.tvDetailVersion)).setText(String.valueOf(sensor.getVersion()));
        ((TextView) findViewById(R.id.tvDetailMaxRange)).setText(String.valueOf(sensor.getMaximumRange()));
        ((TextView) findViewById(R.id.tvDetailResolution)).setText(String.valueOf(sensor.getResolution()));
        ((TextView) findViewById(R.id.tvDetailPower)).setText(String.format(Locale.US, "%.3f mA", sensor.getPower()));
        ((TextView) findViewById(R.id.tvDetailMinDelay)).setText(String.format(Locale.US, "%d µs", sensor.getMinDelay()));

        ((TextView) findViewById(R.id.tvDetailStringType)).setText(sensor.getStringType());
        ((TextView) findViewById(R.id.tvDetailMaxDelay)).setText(String.format(Locale.US, "%d µs", sensor.getMaxDelay()));
        ((TextView) findViewById(R.id.tvDetailReportingMode)).setText(reportingModeText(sensor.getReportingMode()));
        ((TextView) findViewById(R.id.tvDetailIsWakeup)).setText(String.valueOf(sensor.isWakeUpSensor()));
        ((TextView) findViewById(R.id.tvDetailIsDynamic)).setText(String.valueOf(sensor.isDynamicSensor()));
        ((TextView) findViewById(R.id.tvDetailId)).setText(String.valueOf(sensor.getId()));

    }

    private String reportingModeText(int reportingMode) {
        switch (reportingMode) {
            case Sensor.REPORTING_MODE_CONTINUOUS:
                return "Continuous";
            case Sensor.REPORTING_MODE_ON_CHANGE:
                return "On Change";
            case Sensor.REPORTING_MODE_ONE_SHOT:
                return "One Shot";
            case Sensor.REPORTING_MODE_SPECIAL_TRIGGER:
                return "Special Trigger";
            default:
                return "Unknown";
        }
    }
}