package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Cart extends android.app.Activity {

    int listSize;
    private String listNames[];
    private double listPrices[];
    private int quantities[];
    Menu menu;

    ArrayList<CartListElement> theList = new ArrayList<CartListElement>();
    com.example.finalproject.CartListAdapter itemAdapter;

    private View.OnClickListener backButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), TakeoutMenu.class);
            for (int i = 0; i < listSize; i++){
                String quantityIndex = "quantity " + i;
                intent.putExtra(quantityIndex, quantities[i]);
            }
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener placeOrderListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){ ;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            String fileName = "Order_" + currentDateandTime + ".txt";
            String text = "";
            for (int i = 0; i < theList.size(); i++){
                text = text + "Item: " + theList.get(i).getName() + " Quantity: " + theList.get(i).getQuantity() + "\n";
            }

            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(fileName , Context.MODE_PRIVATE);
                outputStream.write(text.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String toastText = "Order placed at " + currentDateandTime + " for the following items:\n" + text;
            Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), TakeoutConfirmation.class);
            for (int i = 0; i < theList.size(); i++) {
                String nameIndex = "list name " + i;
                String priceIndex = "list price " + i;
                String quantityIndex = "quantity " + i;
                intent.putExtra(nameIndex, theList.get(i).getName());
                intent.putExtra(priceIndex, theList.get(i).getPrice());
                intent.putExtra(quantityIndex, theList.get(i).getQuantity());

            }
            intent.putExtra("listSize", theList.size());
            intent.putExtra("quantities", quantities);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    };

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            itemAdapter.setMostRecentlyClickedPosition(i);
            itemAdapter.notifyDataSetChanged();

            TextView quantity = view.findViewById(R.id.quantity);
            Button minus = view.findViewById(R.id.minusButton);
            Button plus = view.findViewById(R.id.plusButton);
            Button delete = view.findViewById(R.id.deleteButton);

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(quantity.getText().toString()) > 0){
                        int q = Integer.parseInt(quantity.getText().toString());
                        q--;
                        quantity.setText(Integer.toString(q));
                        theList.get(i).setQuantity(q);
                        quantities[theList.get(i).getQuantityIndex()] = q;
                        updateOrderTotal();
                    }
                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int q = Integer.parseInt(quantity.getText().toString());
                    q++;
                    quantity.setText(Integer.toString(q));
                    theList.get(i).setQuantity(q);
                    quantities[theList.get(i).getQuantityIndex()] = q;
                    updateOrderTotal();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantities[theList.get(i).getQuantityIndex()] = 0;
                    theList.remove(i);
                    itemAdapter.notifyDataSetChanged();
                    updateOrderTotal();
                }
            });


        }
    };

    private void updateOrderTotal(){
        TextView orderTotal = findViewById(R.id.orderSum);
        int itemSum = 0;
        double priceSum = 0;
        for (int i = 0; i < theList.size(); i++){
            itemSum += theList.get(i).getQuantity();
            priceSum += theList.get(i).getQuantity() * theList.get(i).getPrice();
        }
        String orderText;
        orderText = "Order: " + itemSum + " items totalling $" + String.format("%.2f", priceSum);
        orderTotal.setText(orderText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        itemAdapter = new CartListAdapter(this,theList);
        ListView listView = findViewById(R.id.cartList);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(listener);

        Bundle extras = getIntent().getExtras();

        listSize = extras.getInt("listSize");
        listNames = new String[listSize];
        listPrices = new double[listSize];
        quantities = new int[listSize];
        for (int i = 0; i < listSize; i++){
            String nameKey = "list name " + i;
            String priceKey = "list price " + i;
            String quantityKey = "quantity " + i;
            listNames[i] = extras.getString(nameKey);
            listPrices[i] = extras.getDouble(priceKey);
            quantities[i] = extras.getInt(quantityKey);
            if (quantities[i] != 0){
                theList.add(new CartListElement(listNames[i], listPrices[i], quantities[i], i));
            }
        }
        updateOrderTotal();


        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(backButtonListener);

        Button placeOrder = findViewById(R.id.orderButton);
        placeOrder.setOnClickListener(placeOrderListener);

    }
}

