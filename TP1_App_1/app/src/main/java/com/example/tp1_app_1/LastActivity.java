package com.example.tp1_app_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.Manifest;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.net.Uri;
import android.content.pm.PackageManager;


public class LastActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION_CODE = 1;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_last);
        TextView phoneNumberLabel = findViewById(R.id.phone_number_label);
        Button callButton = findViewById(R.id.call_button);

        Intent intentFromRecapActivity = getIntent();
        this.phoneNumber = intentFromRecapActivity.getStringExtra(InfoRecapActivity.EXTRA_PHONE_NUMBER);
        phoneNumberLabel.setText(phoneNumber);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCallPermissionThenAct();
            }
        });
    }

    private void checkCallPermissionThenAct() {
        //check if call permission is already granted by the system
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //if it's not, we ask the user for it
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.CALL_PHONE },
                    REQUEST_CALL_PERMISSION_CODE);
        } else {
            //if it is, launch the call
            launchCall();
        }
    }

    private void launchCall() {
        Uri phoneNumberURI = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNumberURI);

        //we have to add this second check here otherwise Android Studio is grumpy (even though we already checked...)
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //the user clicked on "authorize"
                launchCall();
            } else {
                //the user did not grant the permission
                Toast.makeText(this, R.string.call_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
