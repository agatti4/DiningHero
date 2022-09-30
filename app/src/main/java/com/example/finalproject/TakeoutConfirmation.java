package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TakeoutConfirmation extends AppCompatActivity {

    int listSize;
    private String listNames[];
    private double listPrices[];
    private int quantities[];

    ArrayList<CartListElement> theList = new ArrayList<CartListElement>();
    com.example.finalproject.CartListAdapter itemAdapter;

    private void updateOrderTotal() {
        TextView orderTotal = findViewById(R.id.orderSumTake);
        int itemSum = 0;
        double priceSum = 0;
        for (int i = 0; i < theList.size(); i++) {
            itemSum += theList.get(i).getQuantity();
            priceSum += theList.get(i).getQuantity() * theList.get(i).getPrice();
        }
        String orderText;
        orderText = "Order: " + itemSum + " items totalling $" + String.format("%.2f", priceSum);
        orderTotal.setText(orderText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeout_confirmation);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        itemAdapter = new CartListAdapter(this, theList);
        ListView listView = findViewById(R.id.cartListTake);
        listView.setAdapter(itemAdapter);

        Bundle extras = getIntent().getExtras();

        listSize = extras.getInt("listSize");
        listNames = new String[listSize];
        listPrices = new double[listSize];
        quantities = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            String nameKey = "list name " + i;
            String priceKey = "list price " + i;
            String quantityKey = "quantity " + i;
            listNames[i] = extras.getString(nameKey);
            listPrices[i] = extras.getDouble(priceKey);
            quantities[i] = extras.getInt(quantityKey);
            if (quantities[i] != 0) {
                theList.add(new CartListElement(listNames[i], listPrices[i], quantities[i], i));
            }
        }
        Log.i("hey", "test");
        updateOrderTotal();

        Button confirmTakeName = findViewById(R.id.confirmNameTakeButton);
        confirmTakeName.setOnClickListener(confirmNameTakeListener);
    }

    private View.OnClickListener confirmNameTakeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                //Hides The Keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //hides keyboard
            } catch (Exception e) {
                // TODO: handle exception
            }


            TextView namePlain = findViewById(R.id.enterNameTakePlain);
            namePlain.setVisibility(View.GONE);

            EditText nameEdit = findViewById(R.id.enterNameTakeEdit);
            String name = nameEdit.getText().toString();
            nameEdit.setVisibility(View.GONE);

            TextView confirmButton = findViewById(R.id.confirmNameTakeButton);
            confirmButton.setVisibility(View.GONE);

            TextView helloNameEdit = findViewById(R.id.helloNameTakeEdit);
            helloNameEdit.setText("Hello " + name);
            helloNameEdit.setVisibility(View.VISIBLE);

            TextView recieptEdit = findViewById(R.id.recieptPlain);
            recieptEdit.setVisibility(View.VISIBLE);

            TextView pickupEdit = findViewById(R.id.pickupPlain);
            pickupEdit.setVisibility(View.VISIBLE);

            TextView orderEdit = findViewById(R.id.thanksForOrderingEdit);
            orderEdit.setVisibility(View.VISIBLE);

            ListView listCart = findViewById(R.id.cartListTake);
            listCart.setVisibility(View.VISIBLE);

            TextView orderTakeEdit = findViewById(R.id.orderSumTake);
            orderTakeEdit.setVisibility(View.VISIBLE);


            //Hides The Keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); //hides keyboard

        }

    };
}


