package fr.umontpellier.hai926i.tp2_app_1;


import android.hardware.Sensor;

import android.content.Context;
import android.content.res.Resources;

import java.util.List;

public class SensorTypeGroup {
    private final int type;
    private final List<Sensor> sensors;
    private boolean isExpanded;


    public SensorTypeGroup(int type, List<Sensor> sensors) {
        this.type = type;
        this.sensors = sensors;
        this.isExpanded = false;
    }

    public int getType() {
        return type;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    //convert convert a sensor type id (int value defined by the API) to its name defined in the string resources
    public static String sensorTypeName(Context context, int sensorTypeId) {
        Resources appResources = context.getResources();

        //check for vendor-specific sensor first, if it is we can return immediately
        if (sensorTypeId >= 65536) {
            return appResources.getString(R.string.sensor_type_vendor) + " (" + sensorTypeId + ")";
        }

        //getIdentifier() is a way to search for a resource with its name
        //that's how we get our human readable sensor type name text
        String sensorTypeNameResourceName = "sensor_type_" + sensorTypeId;
        int sensorTypeNameResourceId = appResources.getIdentifier(sensorTypeNameResourceName, "string", context.getPackageName());

        if (sensorTypeNameResourceId != 0) {
            return appResources.getString(sensorTypeNameResourceId);
        } else {
            //in case we don't have a resource string defined for this sensor type id
            //but I am fairly sure that there are only standard sensor types defined by the API up to the integer value 42
            return appResources.getString(R.string.sensor_type_unknown) + " (" + sensorTypeId + ")";
        }
    }
}