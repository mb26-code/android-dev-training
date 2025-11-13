package com.example.tp1_app_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class InfoRecapActivity extends AppCompatActivity {

    public static final String EXTRA_PHONE_NUMBER = "com.example.tp1_app_1.EXTRA_PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_recap);

        Intent intentFromMainActivity = getIntent();

        String lastName = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_LAST_NAME);
        String firstName = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        String age = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_AGE);
        String areaOfExpertise = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_AREA_OF_EXPERTISE);
        String phoneNumber = intentFromMainActivity.getStringExtra(MainActivity.EXTRA_PHONE_NUMBER);

        TextView nameTextView = findViewById(R.id.recap_full_name);
        TextView ageTextView = findViewById(R.id.recap_age);
        TextView areaOfExpertiseTextView = findViewById(R.id.recap_area_of_expertise);
        TextView phoneNumberTextView = findViewById(R.id.recap_phone_number);

        String fullName = firstName + " " + lastName;
        nameTextView.setText(getString(R.string.recap_full_name, fullName));
        ageTextView.setText(getString(R.string.recap_age, age));
        areaOfExpertiseTextView.setText(getString(R.string.recap_area_of_expertise, areaOfExpertise));
        phoneNumberTextView.setText(getString(R.string.recap_phone_number, phoneNumber));


        Button backButton = findViewById(R.id.recap_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //destroy current recap activity and go back to the previous one
                finish();
            }
        });

        Button nextButton = findViewById(R.id.recap_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start last activity (new screen)
                Intent lastActivityIntent = new Intent(InfoRecapActivity.this, LastActivity.class);
                lastActivityIntent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
                startActivity(lastActivityIntent);
            }
        });
    }
}
