package fr.umontpellier.hai926i.tp2_app_2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;

import android.view.View;
import android.widget.TextView;


//the activity linked to the screen informing the user about which features may be unavailable
//because the smartphone doesn't have a specific type of sensor
public class UnavailableFeaturesActivity extends AppCompatActivity {

    private TextView unavailableFeaturesTitleTextView;
    private TextView unavailableFeaturesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unavailable_features);

        View mainView = findViewById(R.id.unavailable_features_activity_view);
        int initialLeftPadding = mainView.getPaddingLeft();
        int initialTopPadding = mainView.getPaddingTop();
        int initialRightPadding = mainView.getPaddingRight();
        int initialBottomPadding = mainView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            //apply system padding + layout padding defined in XML (prevents the text on the UI to stick to the left border of the screen)
            v.setPadding(
                    systemBars.left + initialLeftPadding,
                    systemBars.top + initialTopPadding,
                    systemBars.right + initialRightPadding,
                    systemBars.bottom + initialBottomPadding
            );
            return insets;
        });

        unavailableFeaturesTitleTextView = findViewById(R.id.unavailableFeaturesTitleTextView);
        unavailableFeaturesTextView = findViewById(R.id.unavailableFeaturesTextView);

        int sensorTypeId = getIntent().getIntExtra("SENSOR_TYPE_ID", -1);
        String sensorTypeName = getIntent().getStringExtra("SENSOR_TYPE_NAME");

        if (sensorTypeName == null) {
            sensorTypeName = "Error";
        }

        unavailableFeaturesTitleTextView.setText(getString(R.string.unavailable_features_title, sensorTypeName));
        unavailableFeaturesTextView.setText(unavailableFeaturesText(this, sensorTypeId));
    }

    private String unavailableFeaturesText(Context context, int sensorTypeId) {
        Resources appResources = context.getResources();
        String textResourceName = "sensor_features_" + sensorTypeId;
        int textResourceId = appResources.getIdentifier(textResourceName, "string", context.getPackageName());

        if (textResourceId != 0) {
            return appResources.getString(textResourceId);
        } else {
            return appResources.getString(R.string.sensor_features_unknown);
        }
    }

}