package com.example.tp1_app_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

//to create and assemble graphical components views, layouts, ...)
import android.widget.LinearLayout;
import android.view.Gravity;
import android.widget.TextView;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.EditText;
import android.text.InputType;
import android.widget.Button;
import android.view.View;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.widget.ProgressBar;

import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    //change value according to the layout definition method you want to use
    private static final boolean BUILD_LAYOUT_PROGRAMMATICALLY = false;

    //act as ids or keys used to retrieve extra intent data
    public static final String EXTRA_LAST_NAME = "com.example.tp1_app_1.EXTRA_LAST_NAME";
    public static final String EXTRA_FIRST_NAME = "com.example.tp1_app_1.EXTRA_FIRST_NAME";
    public static final String EXTRA_AGE = "com.example.tp1_app_1.EXTRA_AGE";
    public static final String EXTRA_AREA_OF_EXPERTISE = "com.example.tp1_app_1.EXTRA_AREA_OF_EXPERTISE";
    public static final String EXTRA_PHONE_NUMBER = "com.example.tp1_app_1.EXTRA_PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!BUILD_LAYOUT_PROGRAMMATICALLY) {
            //use the resource layout defined in XML
            setContentView(R.layout.activity_main);

        } else {
            //build the layout with Java code
            buildLayoutProgrammatically();
        }

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogBox();
            }
        });
    }

    private void buildLayoutProgrammatically() {
        //TLDR: building a GUI this way (programmatically/with Java code) was not worth the hassle in this case, XML is the way to go.
        //having to use LayoutParams objects instead of just writing the value of an XML attribute is a bit tiring...

        //creating a linear layout
        LinearLayout formLayout = new LinearLayout(this);
        formLayout.setOrientation(LinearLayout.VERTICAL);
        formLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams formLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        formLayout.setLayoutParams(formLayoutParams);
        int formLayoutPaddingInPixels = dpToPixels(15);
        formLayout.setPadding(formLayoutPaddingInPixels, formLayoutPaddingInPixels, formLayoutPaddingInPixels, formLayoutPaddingInPixels);


        //adding the form title
        TextView formTitleLabel = new TextView(this);
        formTitleLabel.setText(R.string.form_title);
        formTitleLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        formTitleLabel.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams formTitleLabelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        formTitleLabelParams.gravity = Gravity.CENTER_HORIZONTAL;
        formTitleLabelParams.bottomMargin = dpToPixels(15);
        formTitleLabel.setLayoutParams(formTitleLabelParams);

        formLayout.addView(formTitleLabel);


        //adding the inner form (labels + user input fields for each information...)

        LinearLayout.LayoutParams fieldLabelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams inputFieldParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //last name
        TextView lastNameFieldLabel = new TextView(this);
        lastNameFieldLabel.setText(R.string.last_name_label);
        lastNameFieldLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        lastNameFieldLabel.setLayoutParams(fieldLabelParams);
        formLayout.addView(lastNameFieldLabel);

        EditText lastNameInputField = new EditText(this);
        lastNameInputField.setHint(R.string.last_name_hint);
        lastNameInputField.setInputType(InputType.TYPE_CLASS_TEXT);
        lastNameInputField.setLayoutParams(inputFieldParams);
        formLayout.addView(lastNameInputField);

        //first name
        TextView firstNameFieldLabel = new TextView(this);
        firstNameFieldLabel.setText(R.string.first_name_label);
        firstNameFieldLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        firstNameFieldLabel.setLayoutParams(fieldLabelParams);
        formLayout.addView(firstNameFieldLabel);

        EditText firstNameInputField = new EditText(this);
        firstNameInputField.setHint(R.string.first_name_hint);
        firstNameInputField.setInputType(InputType.TYPE_CLASS_TEXT);
        firstNameInputField.setLayoutParams(inputFieldParams);
        formLayout.addView(firstNameInputField);

        //age
        TextView ageFieldLabel = new TextView(this);
        ageFieldLabel.setText(R.string.age_label);
        ageFieldLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        ageFieldLabel.setLayoutParams(fieldLabelParams);
        formLayout.addView(ageFieldLabel);

        EditText ageInputField = new EditText(this);
        ageInputField.setHint(R.string.age_hint);
        ageInputField.setInputType(InputType.TYPE_CLASS_NUMBER);
        ageInputField.setLayoutParams(inputFieldParams);
        formLayout.addView(ageInputField);

        //area of expertise
        TextView areaOfExpertiseFieldLabel = new TextView(this);
        areaOfExpertiseFieldLabel.setText(R.string.area_of_expertise_label);
        areaOfExpertiseFieldLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        areaOfExpertiseFieldLabel.setLayoutParams(fieldLabelParams);
        formLayout.addView(areaOfExpertiseFieldLabel);

        EditText areaOfExpertiseInputField = new EditText(this);
        areaOfExpertiseInputField.setHint(R.string.area_of_expertise_hint);
        areaOfExpertiseInputField.setInputType(InputType.TYPE_CLASS_TEXT);
        areaOfExpertiseInputField.setLayoutParams(inputFieldParams);
        formLayout.addView(areaOfExpertiseInputField);

        //phone number
        TextView phoneNumberFieldLabel = new TextView(this);
        phoneNumberFieldLabel.setText(R.string.phone_number_label);
        phoneNumberFieldLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        phoneNumberFieldLabel.setLayoutParams(fieldLabelParams);
        formLayout.addView(phoneNumberFieldLabel);

        EditText phoneNumberInputField = new EditText(this);
        phoneNumberInputField.setHint(R.string.phone_number_hint);
        phoneNumberInputField.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneNumberInputField.setLayoutParams(inputFieldParams);
        formLayout.addView(phoneNumberInputField);


        //adding the form confirm button
        Button confirmButton = new Button(this);
        confirmButton.setText(R.string.confirm_button_label);
        LinearLayout.LayoutParams confirmButtonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        confirmButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        confirmButtonParams.topMargin = dpToPixels(20);
        confirmButton.setLayoutParams(confirmButtonParams);

        confirmButton.setId(R.id.confirm_button);
        formLayout.addView(confirmButton);

        //finally...
        setContentView(formLayout);
    }

    //I have to set up view layout params using a pixel as a unit, so here's a way to convert from dp to px
    private int dpToPixels(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void showConfirmationDialogBox() {
        AlertDialog.Builder dialogBoxBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogBoxView = inflater.inflate(R.layout.dialog_box, null);

        ProgressBar progressBar = dialogBoxView.findViewById(R.id.dialog_box_progress_bar);
        LinearLayout dialogBoxMessageLayout = dialogBoxView.findViewById(R.id.dialog_box_message_layout);
        Button closeButton = dialogBoxView.findViewById(R.id.dialog_box_close_button);

        dialogBoxBuilder.setView(dialogBoxView);

        //this prevents the user from closing the dialog box by clicking outside of it
        dialogBoxBuilder.setCancelable(false);

        AlertDialog dialogBox = dialogBoxBuilder.create();
        dialogBox.show();

        //defining event listener for the dialog box close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to info recap activity
                launchInfoRecapActivity();
                //close the dialog box
                dialogBox.dismiss();
            }
        });

        //waiting 2 seconds before showing the confirmation message after the dialog box is rendered (progress bar)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //after 2 seconds, hide progress bar
                progressBar.setVisibility(View.GONE);
                //and then reveal success message
                dialogBoxMessageLayout.setVisibility(View.VISIBLE);
                //allow user to close dialog box by clicking outside of it
                dialogBox.setCancelable(true);
            }
        }, 2000);
    }

    private void launchInfoRecapActivity() {

        EditText lastNameField = findViewById(R.id.last_name_input_field);
        EditText firstNameField = findViewById(R.id.first_name_input_field);
        EditText ageField = findViewById(R.id.age_input_field);
        EditText areaOfExpertiseField = findViewById(R.id.area_of_expertise_input_field);
        EditText phoneNumberField = findViewById(R.id.phone_number_input_field);

        String lastName = lastNameField.getText().toString();
        String firstName = firstNameField.getText().toString();
        String age = ageField.getText().toString();
        String areaOfExpertise = areaOfExpertiseField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();

        //creating the explicit intent
        Intent intentInfoRecapActivity = new Intent(MainActivity.this, InfoRecapActivity.class);
        //and adding data to it
        intentInfoRecapActivity.putExtra(EXTRA_LAST_NAME, lastName);
        intentInfoRecapActivity.putExtra(EXTRA_FIRST_NAME, firstName);
        intentInfoRecapActivity.putExtra(EXTRA_AGE, age);
        intentInfoRecapActivity.putExtra(EXTRA_AREA_OF_EXPERTISE, areaOfExpertise);
        intentInfoRecapActivity.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
        //then start last activity with it
        startActivity(intentInfoRecapActivity);
    }

}