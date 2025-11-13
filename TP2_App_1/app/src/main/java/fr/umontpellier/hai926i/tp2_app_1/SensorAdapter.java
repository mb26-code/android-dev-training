package fr.umontpellier.hai926i.tp2_app_1;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private final List<SensorTypeGroup> sensorTypeGroups;
    private final Context context;


    public SensorAdapter(Context context, List<SensorTypeGroup> sensorTypeGroups) {
        this.context = context;
        this.sensorTypeGroups = sensorTypeGroups;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sensor_type_group_item, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        SensorTypeGroup sensorTypeGroup = sensorTypeGroups.get(position);
        List<Sensor> sensors = sensorTypeGroup.getSensors();

        Sensor defaultSensor = sensors.get(0);

        //the information of the default sensor of this type
        holder.sensorTypeTextView.setText(context.getString(R.string.sensor_type_label,
                SensorTypeGroup.sensorTypeName(context, sensorTypeGroup.getType())));
        holder.sensorNameTextView.setText(context.getString(R.string.sensor_name_label, defaultSensor.getName()));
        holder.sensorVendorTextView.setText(context.getString(R.string.sensor_vendor_label, defaultSensor.getVendor()));

        holder.sensorClickableArea.setOnClickListener(v -> {
            startDetailsActivity(defaultSensor.getType(), defaultSensor.getName());
        });

        //expand button logic, if there is one
        boolean isExpandable = sensors.size() > 1;
        holder.expandButton.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.expandableLayout.setVisibility(sensorTypeGroup.isExpanded() ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.expandButton.setRotation(sensorTypeGroup.isExpanded() ? 180f : 0f);

            holder.expandButton.setOnClickListener(v -> {
                sensorTypeGroup.setExpanded(!sensorTypeGroup.isExpanded());
                notifyItemChanged(holder.getAdapterPosition());
            });

            //fill the expanded section, if it is displayed

            holder.expandableLayout.removeAllViews();
            if (sensorTypeGroup.isExpanded()) {
                //starting with the sensor with index 1 because the one with index 0 is the default one that is already displayed
                for (int i = 1; i < sensors.size(); i++) {
                    Sensor childSensor = sensors.get(i);
                    View childView = LayoutInflater.from(context)
                            .inflate(R.layout.expanded_sensor_item, holder.expandableLayout, false);

                    TextView expandedSensorNameTextView = childView.findViewById(R.id.expandedSensorNameTextView);
                    TextView expandedSensorVendorTextView = childView.findViewById(R.id.expandedSensorVendorTextView);

                    expandedSensorNameTextView.setText(context.getString(R.string.sensor_name_label, childSensor.getName()));
                    expandedSensorVendorTextView.setText(context.getString(R.string.sensor_vendor_label, childSensor.getVendor()));

                    childView.setOnClickListener(v -> {
                        startDetailsActivity(childSensor.getType(), childSensor.getName());
                    });

                    //add the new view about the sensor to to expanded section
                    holder.expandableLayout.addView(childView);
                }
            }
        }
    }

    private void startDetailsActivity(int sensorTypeId, String sensorTypeName) {
        Intent sensorDetailsActivityIntent = new Intent(context, SensorDetailsActivity.class);
        sensorDetailsActivityIntent.putExtra("SENSOR_TYPE_ID", sensorTypeId);
        sensorDetailsActivityIntent.putExtra("SENSOR_TYPE_NAME", sensorTypeName);
        context.startActivity(sensorDetailsActivityIntent);
    }

    @Override
    public int getItemCount() {
        return sensorTypeGroups.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        TextView sensorTypeTextView, sensorNameTextView, sensorVendorTextView;
        ImageButton expandButton;
        LinearLayout expandableLayout;
        RelativeLayout sensorClickableArea;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorTypeTextView = itemView.findViewById(R.id.sensorTypeTextView);
            sensorNameTextView = itemView.findViewById(R.id.sensorNameTextView);
            sensorVendorTextView = itemView.findViewById(R.id.sensorVendorTextView);
            expandButton = itemView.findViewById(R.id.expandButton);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            sensorClickableArea = itemView.findViewById(R.id.sensorClickableArea);
        }
    }
}
