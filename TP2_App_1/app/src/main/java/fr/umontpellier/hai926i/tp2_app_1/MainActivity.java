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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//the activity linked to the screen displaying a list of all the sensors that the smartphone has (and basic information)
public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SensorAdapter sensorAdapter;
    private RecyclerView existingSensorsRecyclerView;


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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> existingSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        Map<Integer, List<Sensor>> sensorsGroupedByType = new TreeMap<>();
        for (Sensor sensor : existingSensors) {
            int sensorType = sensor.getType();
            if (!sensorsGroupedByType.containsKey(sensorType)) {
                sensorsGroupedByType.put(sensorType, new ArrayList<>());
            }
            sensorsGroupedByType.get(sensorType).add(sensor);
        }
        List<SensorTypeGroup> sensorTypeGroups = new ArrayList<>();
        for (Map.Entry<Integer, List<Sensor>> entry : sensorsGroupedByType.entrySet()) {
            sensorTypeGroups.add(new SensorTypeGroup(entry.getKey(), entry.getValue()));
        }

        existingSensorsRecyclerView = findViewById(R.id.existingSensorsRecyclerView);
        existingSensorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorAdapter = new SensorAdapter(this, sensorTypeGroups);
        existingSensorsRecyclerView.setAdapter(sensorAdapter);
    }
}