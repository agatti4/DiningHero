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

public class CartListAdapter extends ArrayAdapter<CartListElement> {

    private int mostRecentlyClickedPosition;

    public CartListAdapter(Activity context, ArrayList<CartListElement> list){
        super(context, 0, list);
        mostRecentlyClickedPosition = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_element, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.item);
        TextView itemPrice = convertView.findViewById(R.id.price);
        TextView itemQuantity = convertView.findViewById(R.id.quantity);
        Button minus = convertView.findViewById(R.id.minusButton);
        Button plus = convertView.findViewById(R.id.plusButton);
        Button delete = convertView.findViewById(R.id.deleteButton);

        CartListElement item = getItem(position);
        String name = item.getName();
        double price = item.getPrice();
        int quantity = item.getQuantity();
        itemName.setText(name);
        itemPrice.setText(String.format("%.2f",price));
        itemQuantity.setText(Integer.toString(quantity));


        if (position == mostRecentlyClickedPosition){
            minus.setVisibility(View.VISIBLE);
            plus.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
        else{
            minus.setVisibility(View.GONE);
            plus.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setMostRecentlyClickedPosition(int mostRecentlyClickedPosition) {
        this.mostRecentlyClickedPosition = mostRecentlyClickedPosition;
    }
}

