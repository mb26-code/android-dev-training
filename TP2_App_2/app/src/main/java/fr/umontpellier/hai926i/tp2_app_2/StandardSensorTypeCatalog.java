package fr.umontpellier.hai926i.tp2_app_2;


import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class StandardSensorTypeCatalog {

    private static final int MAX_STANDARD_SENSOR_TYPE_ID = 42;
    //there is a total 42 standard sensor types defined by the Android API (values 1 to 42, see recap file)


    public static List<SensorType> standardSensorTypesList(Context context) {
        List<SensorType> standardSensorTypesList = new ArrayList<>();
        Resources appResources = context.getResources();

        for (int sensorTypeId = 1; sensorTypeId <= MAX_STANDARD_SENSOR_TYPE_ID; sensorTypeId++) {
            String sensorTypeNameResourceName = "sensor_type_" + sensorTypeId;
            int sensorTypeNameResourceId = appResources.getIdentifier(sensorTypeNameResourceName, "string", context.getPackageName());

            String sensorTypeName;
            if (sensorTypeNameResourceId != 0) {
                sensorTypeName = appResources.getString(sensorTypeNameResourceId);
            } else {
                sensorTypeName = appResources.getString(R.string.sensor_type_unknown) + " (" + sensorTypeId + ")";
            }

            //special case: we don't include reserved and/or undefined sensor types of the API for ids 34 and 39
            //since no smartphone device has them (obviously)
            if (sensorTypeId != 34 && sensorTypeId != 39) {
                standardSensorTypesList.add(new SensorType(sensorTypeId, sensorTypeName));
            }
        }
        return standardSensorTypesList;
    }
}