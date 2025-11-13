package com.example.tp1_app_2;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tp1_app_2.model.Train;

import java.util.List;

public class TrainAdapter extends ArrayAdapter<Train> {

    private Context mContext;
    private int mResource;

    public TrainAdapter(@NonNull Context context, int resource, @NonNull List<Train> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Train train = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView departureTimeLabel = convertView.findViewById(R.id.departure_time_label);
        TextView arrivalTimeLabel = convertView.findViewById(R.id.arrival_time_label);
        TextView departurePlatformLabel = convertView.findViewById(R.id.departure_platform_label);
        TextView arrivalPlatformLabel = convertView.findViewById(R.id.arrival_platform_label);

        departureTimeLabel.setText(train.getDepartureTime());
        arrivalTimeLabel.setText(train.getArrivalTime());
        departurePlatformLabel.setText(
                mContext.getString(R.string.departure_platform_template, train.getDeparturePlatform())
        );
        arrivalPlatformLabel.setText(
                mContext.getString(R.string.arrival_platform_template, train.getArrivalPlatform())
        );

        return convertView;
    }
}
