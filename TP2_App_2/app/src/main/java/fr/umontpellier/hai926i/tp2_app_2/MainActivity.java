package fr.umontpellier.hai926i.tp2_app_2;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//the activity linked to the screen displaying a list of all the standard sensors that the smartphone doesn't have
public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private RecyclerView missingSensorTypesRecyclerView;
    //the list view displaying all missing sensor types
    //but it's not a ListView, it's a RecyclerView (better performance, and better handling of displayed views by the system)
    private MissingSensorTypeAdapter adapter;
    //to convert data to view
    private TextView noMissingSensorTypesTextView;


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

        missingSensorTypesRecyclerView = findViewById(R.id.recyclerView);
        noMissingSensorTypesTextView = findViewById(R.id.noMissingSensorTypesTextView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> detectedSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Set<Integer> detectedSensorTypes = new HashSet<>();
        for (Sensor detectedSensor : detectedSensors) {
            detectedSensorTypes.add(detectedSensor.getType());
        }

        List<SensorType> standardSensorTypesList = StandardSensorTypeCatalog.standardSensorTypesList(this);
        List<SensorType> missingStandardSensorTypesList = new ArrayList<>();
        for (SensorType standardSensorType : standardSensorTypesList) {
            if (!detectedSensorTypes.contains(standardSensorType.getId())) {
                missingStandardSensorTypesList.add(standardSensorType);
            }
        }

        if (missingStandardSensorTypesList.isEmpty()) {
            missingSensorTypesRecyclerView.setVisibility(View.GONE);
            noMissingSensorTypesTextView.setVisibility(View.VISIBLE);
        } else {
            missingSensorTypesRecyclerView.setVisibility(View.VISIBLE);
            noMissingSensorTypesTextView.setVisibility(View.GONE);
            fillMissingSensorTypesRecyclerView(missingStandardSensorTypesList);
        }
    }

    private void fillMissingSensorTypesRecyclerView(List<SensorType> missingSensorTypes) {
        missingSensorTypesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MissingSensorTypeAdapter(this, missingSensorTypes);
        missingSensorTypesRecyclerView.setAdapter(adapter);
    }
}