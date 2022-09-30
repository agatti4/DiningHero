package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableConfirmation extends AppCompatActivity {


    private int tableNumberFromIntent;
    private String timeFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_confirmation);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Bundle extras = getIntent().getExtras();

        tableNumberFromIntent = extras.getInt("Table Number");
        timeFromIntent = extras.getString("Date and Time");

        // Get Current date (DD/MM/YYYY)
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        String todayWithZeroTime = today.toString().substring(0, 11);  // Output : Mon Dec 06


        TextView tableNumberEdit = findViewById(R.id.tableNumberEdit);
        tableNumberEdit.setText("You Have Table #" + tableNumberFromIntent);

        TextView dateEdit = findViewById(R.id.reservedTimeEdit);
        dateEdit.setText("Reserved at: " + todayWithZeroTime + " " + timeFromIntent);


        try {
            today = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Button confirmName = findViewById(R.id.confirmNameButton);
        confirmName.setOnClickListener(confirmNameListener);


        Log.e("Info", "-----------------------");
        Log.e("Info", "Table number -> " + tableNumberFromIntent);
        Log.e("Info", "Date -> " + todayWithZeroTime);
        Log.e("Info", "Time -> " + timeFromIntent);


    }

    private View.OnClickListener confirmNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                //Hides The Keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //hides keyboard
            } catch (Exception e) {
                // TODO: handle exception
            }

            TextView namePlain = findViewById(R.id.enterNamePlain);
            namePlain.setVisibility(View.GONE);

            EditText nameEdit = findViewById(R.id.enterNameEdit);
            String name = nameEdit.getText().toString();
            nameEdit.setVisibility(View.GONE);

            TextView confirmButton = findViewById(R.id.confirmNameButton);
            confirmButton.setVisibility(View.GONE);

            TextView helloNameEdit = findViewById(R.id.helloNameEdit);
            helloNameEdit.setText("Hello " + name);
            helloNameEdit.setVisibility(View.VISIBLE);

            TextView tableNumberEdit = findViewById(R.id.tableNumberEdit);
            tableNumberEdit.setVisibility(View.VISIBLE);

            TextView dateEdit = findViewById(R.id.reservedTimeEdit);
            dateEdit.setVisibility(View.VISIBLE);

            //Hides The Keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); //hides keyboard

        }

    };
}

