package fr.umontpellier.hai926i.tp2_app_2;


import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.List;

public class MissingSensorTypeAdapter extends RecyclerView.Adapter<MissingSensorTypeAdapter.SensorViewHolder> {

    private final Context context;
    private final List<SensorType> missingSensorTypesList;


    public MissingSensorTypeAdapter(Context context, List<SensorType> missingSensorTypesList) {
        this.context = context;
        this.missingSensorTypesList = missingSensorTypesList;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.missing_sensor_type_item, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        SensorType sensorType = missingSensorTypesList.get(position);
        holder.sensorTypeNameTextView.setText(sensorType.getName());

        //if the user clicks on a missing sensor type, we want to go to the next screen (features unavailable)
        holder.itemView.setOnClickListener(v -> {
            Intent unavailableFeaturesActivityIntent = new Intent(context, UnavailableFeaturesActivity.class);
            unavailableFeaturesActivityIntent.putExtra("SENSOR_TYPE_ID", sensorType.getId());
            unavailableFeaturesActivityIntent.putExtra("SENSOR_TYPE_NAME", sensorType.getName());
            context.startActivity(unavailableFeaturesActivityIntent);
        });
    }

    @Override
    public int getItemCount() {
        return missingSensorTypesList.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        TextView sensorTypeNameTextView;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorTypeNameTextView = itemView.findViewById(R.id.missingSensorTypeTextView);
        }
    }
}