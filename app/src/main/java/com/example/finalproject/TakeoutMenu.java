package com.example.finalproject;

import android.app.Activity;
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

import java.util.ArrayList;

//public class TakeoutMenu extends android.app.Activity{
public class TakeoutMenu extends Activity {



    ArrayList<ListElement> theList = new ArrayList<ListElement>();
    com.example.finalproject.ListAdapter itemAdapter;
    Menu menu;


    private View.OnClickListener backButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("listSize", theList.size());
            for (int i = 0; i < theList.size(); i++){
                String quantityIndex = "quantity " + i;
                intent.putExtra(quantityIndex, menu.quantities[i]);
            }
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener goToCartButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), Cart.class);
            //Intent intent2 = new Intent(getApplicationContext(), TakeoutConfirmation.class);
            for (int i = 0; i < theList.size(); i++){
                String nameIndex = "list name " + i;
                String priceIndex = "list price " + i;
                String quantityIndex = "quantity " + i;
                intent.putExtra(nameIndex,theList.get(i).getName());
                intent.putExtra(priceIndex, theList.get(i).getPrice());
                intent.putExtra(quantityIndex, menu.quantities[i]);
/*                intent2.putExtra(nameIndex,theList.get(i).getName());
                intent2.putExtra(priceIndex, theList.get(i).getPrice());
                intent2.putExtra(quantityIndex, menu.quantities[i]);*/
            }
            intent.putExtra("listSize", theList.size());
            intent.putExtra("quantities", menu.quantities);
/*            intent2.putExtra("listSize", theList.size());
            intent2.putExtra("quantities", menu.quantities);*/
/*            if (intent2.resolveActivity(getPackageManager()) != null){
                startActivity(intent2);
            }*/
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
    };

    /**
     * listener for listView
     */
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            itemAdapter.setMostRecentlyClickedPosition(i);
            itemAdapter.notifyDataSetChanged();

            EditText quantity = view.findViewById(R.id.quantityEntry);
            Button addToCart = view.findViewById(R.id.addToCartButton);
            Button review = view.findViewById(R.id.reviewButton);


            addToCart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    InputMethodManager keyboardHider = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getCurrentFocus() != null){
                        keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    String quantityString = quantity.getText().toString();
                    if (!quantityString.equals("")){
                        menu.quantities[i] += Integer.parseInt(quantityString);
                        quantity.setText("");
                        updateCartNumber();
                    }
                }
            });
            review.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), Review.class);
                    intent.putExtra("item name", theList.get(i).getName());
                    for (int i = 0; i < theList.size(); i++){
                        String quantityIndex = "quantity " + i;
                        intent.putExtra(quantityIndex, menu.quantities[i]);
                    }
                    intent.putExtra("listSize", theList.size());
                    if (intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });
        }
    };

    private void updateCartNumber(){
        Button cartButton = findViewById(R.id.cartButton);
        int n = 0;
        for (int i = 0; i < theList.size(); i++){
            n += menu.quantities[i];
        }
        String cartText = "Go to cart (" + n + " items)";
        cartButton.setText(cartText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        ListView listView = findViewById(R.id.menuList);

        itemAdapter = new ListAdapter(this, theList);
        listView = findViewById(R.id.menuList);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(listener);
        menu = new Menu(listView, theList, itemAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            for (int i = 0; i < theList.size(); i++){
                String quantityIndex = "quantity " + i;
                menu.quantities[i] = extras.getInt(quantityIndex);
            }
            updateCartNumber();
        }

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(backButtonListener);

        Button goToCart = findViewById(R.id.cartButton);
        goToCart.setOnClickListener(goToCartButtonListener);

    }
}