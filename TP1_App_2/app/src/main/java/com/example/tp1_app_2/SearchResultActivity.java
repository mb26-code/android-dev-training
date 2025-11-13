package com.example.tp1_app_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.tp1_app_2.model.Train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ListView resultList;
    private TextView resultTitle;
    private Button backButton;
    private TrainAdapter adapter;
    private List<Train> trains = new ArrayList<>();
    private List<Train> filteredTrains = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        resultTitle = findViewById(R.id.result_title);
        resultList = findViewById(R.id.result_list);
        backButton = findViewById(R.id.back_button);

        loadTrainsData();

        Intent intentFromMainActivity = getIntent();
        String departureTown = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_DEPARTURE_TOWN);
        String arrivalTown = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_ARRIVAL_TOWN);

        resultTitle.setText(getString(R.string.result_title_template, departureTown, arrivalTown));

        filterTrains(departureTown, arrivalTown);

        adapter = new TrainAdapter(this, R.layout.result_item, filteredTrains);
        resultList.setAdapter(adapter);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void filterTrains(String departureTown, String arrivalTown) {
        filteredTrains.clear();

        for (Train train : trains) {

            if (train.getDepartureTown().equalsIgnoreCase(departureTown) && train.getArrivalTown().equalsIgnoreCase(arrivalTown)) {
                filteredTrains.add(train);
            }
        }
    }

    private void loadTrainsData() {
        XmlResourceParser parser = getResources().getXml(R.xml.trains_data);
        try {
            String departureTown = "", arrivalTown = "", departureTime = "", arrivalTime = "", departurePlatform = "", arrivalPlatform = "";
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if (tagName.equalsIgnoreCase("departureTown")) {
                            departureTown = parser.nextText();
                        } else if (tagName.equalsIgnoreCase("arrivalTown")) {
                            arrivalTown = parser.nextText();
                        } else if (tagName.equalsIgnoreCase("departureTime")) {
                            departureTime = parser.nextText();
                        } else if (tagName.equalsIgnoreCase("arrivalTime")) {
                            arrivalTime = parser.nextText();
                        } else if (tagName.equalsIgnoreCase("departurePlatform")) {
                            departurePlatform = parser.nextText();
                        } else if (tagName.equalsIgnoreCase("arrivalPlatform")) {
                            arrivalPlatform = parser.nextText();
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("train")) {
                            trains.add(new Train(departureTown, arrivalTown, departureTime, arrivalTime, departurePlatform, arrivalPlatform));

                            // Réinitialiser les champs (bonne pratique)
                            departureTown = "";
                            arrivalTown = "";
                            departureTime = "";
                            arrivalTime = "";
                            departurePlatform = "";
                            arrivalPlatform = "";
                        }

                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            Log.e("TP1_App_2", "Erreur lors du parsing XML des données de trains.", e);
        } finally {
            parser.close();
        }
    }
}

