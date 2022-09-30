package com.example.finalproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;



public class ListAdapter extends ArrayAdapter<ListElement> {

    private int mostRecentlyClickedPosition;

    public ListAdapter(Activity context, ArrayList<ListElement> list){
        super(context, 0, list);
        mostRecentlyClickedPosition = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_element, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.item);
        TextView itemPrice = convertView.findViewById(R.id.price);
        TextView numberSymbol = convertView.findViewById(R.id.numberSymbol);
        EditText itemQuantity = convertView.findViewById(R.id.quantityEntry);
        Button addToCart = convertView.findViewById(R.id.addToCartButton);
        Button reviewButton = convertView.findViewById(R.id.reviewButton);

        ListElement item = getItem(position);
        String name = item.getName();
        double price = item.getPrice();
        itemName.setText(name);
        itemPrice.setText(String.format("%.2f",price));


        if (position == mostRecentlyClickedPosition){
            numberSymbol.setVisibility(View.VISIBLE);
            itemQuantity.setVisibility(View.VISIBLE);
            addToCart.setVisibility(View.VISIBLE);
            reviewButton.setVisibility(View.VISIBLE);

        }
        else{
            numberSymbol.setVisibility(View.GONE);
            itemQuantity.setVisibility(View.GONE);
            addToCart.setVisibility(View.GONE);
            reviewButton.setVisibility(View.GONE);

        }

        return convertView;
    }

    public void setMostRecentlyClickedPosition(int mostRecentlyClickedPosition) {
        this.mostRecentlyClickedPosition = mostRecentlyClickedPosition;
    }
}
