package com.example.finalproject;

import android.widget.ListView;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.ArrayList;

public class Menu {

    public int quantities[];
    public ListView listView;
    public ArrayList<ListElement> theList;
    public ListAdapter itemAdapter;

    public Menu (ListView lV, ArrayList<ListElement> tL, ListAdapter iA ){

        listView = lV;
        theList = tL;
        itemAdapter = iA;

        /**
         * add new items to the menu here whenever needed.
         * Parameters are the name of item (string), then price of item (double).
         */
        addItem("Grilled Chicken", 19.99);
        addItem("Ice Cream",10.49);
        addItem("Lasagna",11.25);
        addItem("Meatloaf",5.00);
        addItem("Meatball Sub",2.50);
        addItem("Penne Pasta",1.99);
        addItem("Caesar Salad",18.79);
        addItem("Steak and Cheese",3.49);
        addItem("White Rice",1.25);
        addItem("Oven Roasted Vegetables",8.75);

    }
    private void addItem(String name, double price){

        theList.add(new ListElement(name, price));

        quantities = new int[theList.size()];
        for (int i = 0; i < theList.size(); i++){
            quantities[i] = 0;
        }

        itemAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();

    }

    public void addToCart(int i, int quantity){
        quantities[i] = quantity;
    }
}
