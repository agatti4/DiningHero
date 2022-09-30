package com.example.finalproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ReviewListAdapter extends ArrayAdapter<ReviewListElement> {

    private int mostRecentlyClickedPosition;

    public ReviewListAdapter(Activity context, ArrayList<ReviewListElement> list){
        super(context, 0, list);
        mostRecentlyClickedPosition = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_element, parent, false);
        }

        TextView dateAndTime = convertView.findViewById(R.id.date);
        TextView review = convertView.findViewById(R.id.review);

        ReviewListElement item = getItem(position);

        String dateText = "Review From: " + item.getReviewDate();

        dateAndTime.setText(dateText);
        review.setText(item.getReviewString());

        if (position == mostRecentlyClickedPosition){
        }
        else{
        }

        return convertView;
    }

    public void setMostRecentlyClickedPosition(int mostRecentlyClickedPosition) {
        this.mostRecentlyClickedPosition = mostRecentlyClickedPosition;
    }
}