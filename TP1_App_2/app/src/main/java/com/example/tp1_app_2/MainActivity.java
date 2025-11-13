package com.example.tp1_app_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_DEPARTURE_TOWN = "com.example.tp1_app_2.EXTRA_DEPARTURE_TOWN";
    public static final String EXTRA_ARRIVAL_TOWN = "com.example.tp1_app_2.EXTRA_ARRIVAL_TOWN";

    private EditText departureTownField;
    private EditText arrivalTownField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        departureTownField = findViewById(R.id.departure_town_input_field);
        arrivalTownField = findViewById(R.id.arrival_town_input_field);
        Button searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSearchResultActivity();
            }
        });
    }

    private void launchSearchResultActivity() {
        String departureTown = departureTownField.getText().toString();
        String arrivalTown = arrivalTownField.getText().toString();

        Intent searchResultActivityIntent = new Intent(MainActivity.this, SearchResultActivity.class);
        searchResultActivityIntent.putExtra(EXTRA_DEPARTURE_TOWN, departureTown);
        searchResultActivityIntent.putExtra(EXTRA_ARRIVAL_TOWN, arrivalTown);

        startActivity(searchResultActivityIntent);
    }

}