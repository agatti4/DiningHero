package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    /**
     * NOTE: THIS APPLICATION WAS TESTED MOSTLY ON A LANDSCAPE ORIENTATED PHONE
     * (NEXUS 9 API 27) AND A PORTRAIT ORIENTATION PHONE (PIXEL 2 API 27)
     */

    private View.OnClickListener goToMenuListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), TakeoutMenu.class);


            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);


        Button takeoutChase = findViewById(R.id.ChaseTakeoutButton);
        takeoutChase.setOnClickListener(goToMenuListener);

        Button takeoutEmerson = findViewById(R.id.EmersonTakeoutButton);
        takeoutEmerson.setOnClickListener(goToMenuListener);


        Button dineInChase = findViewById(R.id.ChaseReserveButton);
        dineInChase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                moveToChase(v1);
            }
        });

        Button dineInEmerson = findViewById(R.id.EmersonReserveButton);
        dineInEmerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                moveToEmerson(v2);
            }
        });
    }


    public void moveToChase(View view) {
        Intent move = new Intent(this, ChooseTableChase.class);
        startActivity(move);
    }

    public void moveToEmerson(View view) {
        Intent move = new Intent(this, ChooseTableEmerson.class);
        startActivity(move);
    }
}
