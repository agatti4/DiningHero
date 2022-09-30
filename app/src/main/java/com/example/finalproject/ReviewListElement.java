package com.example.finalproject;

import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ReviewListElement {

    // Data members
    private String    reviewString;
    private Date reviewDate;

    // Constructor
    public ReviewListElement(String reviewString, Date reviewDate){
        this.reviewString = reviewString;
        this.reviewDate   = reviewDate;
    }

    // Getters
    public String getReviewString()      { return reviewString; }
    public Date   getReviewDate()        { return reviewDate; }


    // Setters
    private void setReviewString(String writtenReview)      { this.reviewString = writtenReview; }
    private void setReviewDate  (Date timeOfReview)         { this.reviewDate = timeOfReview; }

}