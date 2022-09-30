package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Review extends android.app.Activity {

    int listSize;
    int numberOfCurrentReviews;
    String foodID;
    private int quantities[];

    ArrayList<ReviewListElement> theList = new ArrayList<ReviewListElement>();
    com.example.finalproject.ReviewListAdapter itemAdapter;


    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            itemAdapter.setMostRecentlyClickedPosition(i);
            itemAdapter.notifyDataSetChanged();

        }
    };

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

    private View.OnClickListener submitButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TextView newReview = findViewById(R.id.userInputReview);

//            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//            int currentYear = calendar.get(Calendar.YEAR);
//            int currentMonth = calendar.get(Calendar.MONTH) + 1;
//            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);


            /* PUT THESE IN DATABASE */
            String review = newReview.getText().toString();


            // Get Current date of this submitted review
            Date currentDate = new Date();


            // Add review (with timestamp) to firebase/listview
            addReviewToFireBase(currentDate, review);

            // Set/Update editText to empty
            newReview.setText("");



            InputMethodManager keyboardHider = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null){
                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    };


    // This gives the user the option to see if they would like to see the reviews or not
    private View.OnClickListener clickMeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            itemAdapter.notifyDataSetChanged();
            view.setVisibility(View.INVISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_layout);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(submitButtonListener);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(backButtonListener);

        Button clickToSeeReviews = findViewById(R.id.clickToSeeReviews);
        clickToSeeReviews.setOnClickListener(clickMeListener);

        ListView listView = findViewById(R.id.reviewList);


        itemAdapter = new ReviewListAdapter(this, theList);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(listener);

        Bundle extras = getIntent().getExtras();
        foodID = extras.getString("item name");
        TextView itemName = findViewById(R.id.itemName);
        itemName.setText(foodID);

        listSize = extras.getInt("listSize");
        quantities = new int[listSize];
        for (int i = 0; i < listSize; i++){
            String quantityKey = "quantity " + i;
            quantities[i] = extras.getInt(quantityKey);
        }



        // Get reviews from firebase and display them in the list view
        getReviewsFromFireBase(foodID);

    }


    // THIS IS VERY IMPORTANT, DO NOT DELETE
    // This allows the object list extracted from the data base to be passed.
    // Extracting from a database is treated as an async like task so we need an
    // onSuccess function that is called from a listener
    public interface OnGetDataListener {

        //this is for callbacks
        void onSuccess(ArrayList<ReviewListElement> objectFromDataBase);
        void onStart();
        void onFailure();
    }



    private void addReviewToFireBase(Date dateOfReview, String review){
        ReviewListElement testElement = new ReviewListElement(review, dateOfReview);
        theList.add(testElement);

        // Start adding review to firebase below:

        // Get firebase instance
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Map<String, Object> reviewObjectSetter = new HashMap<>();

        reviewObjectSetter.put("reviewDate", dateOfReview);
        reviewObjectSetter.put("reviewText", review);

        // This creates a new document with a document name of 'review#' (# = # of current reviews + 1)
        database.collection("menu").document(foodID)
                .collection("reviews")
                .document("review"+theList.size())
                .set(reviewObjectSetter);



//        database.collection("menu").document(foodID).collection("reviews")
//                .add(reviewObjectSetter)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.e("Review", "Review added to FireBase!");
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Review", "Error writing Review to FireBase!", e);
//                    }
//                });


    }


    private void getReviewsFromFireBase(String foodID){

        getDataFromFireBase(foodID, new OnGetDataListener() {

            // onSuccess of storing review objects in an arrayList, send it to the function
            @Override
            public void onSuccess(ArrayList<ReviewListElement> objectFromDataBase) {
                addReviewsToListView(objectFromDataBase);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
    }






    /**
     * Receives a string containing the foodID for the firebase extraction
     * Need to pass a listener in order to be able to use call backs.
     * Confusing, but it is necessary in order to pass the listener the object data
     * extracted from the database
     **/
    public void getDataFromFireBase(String foodID, final Review.OnGetDataListener listener) {

        // Get firebase instance
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Array list to store all reviews from firebase
        ArrayList<ReviewListElement> myArrayList = new ArrayList<ReviewListElement>();


        database.collection("menu").document(foodID).collection("reviews").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Reviews from FireBase", document.getId() + " => " + document.getData());


                                // Extract data from firestore documents (reviews)
                                String reviewStr = document.getData().get("reviewText").toString();
                                Timestamp timeOfReview = (Timestamp) document.getData().get("reviewDate");

                                // Convert timestamp from firebase into a date that is printable
                                Date dateOfReview = timeOfReview.toDate();


                                // Convert each document into object and add it to our array list
                                ReviewListElement reviewFromFireBase = new ReviewListElement(reviewStr, dateOfReview);

                                // Add each object (all reviews when loop is finished) to the list
                                myArrayList.add(reviewFromFireBase);
                            }
                            Log.d("Reviews from FireBase", "List of reviews: " + myArrayList);

                            // Pass the new list of reviews created from the firestore data base.
                            // By doing it this way, the listener will pass it to onSuccess()
                            // Where it can be used in the main thread!
                            listener.onSuccess(myArrayList);

                        } else {
                            Log.e("Reviews from FireBase", "Error getting Reviews! ", task.getException());
                        }
                    }
                });
    }






    /**
     * Receives an arrayList with review objects created from the data base of the specific food
     * item being looked at. This function will add the objects to a list view so that they can
     * be displayed to the user
     **/
    private void addReviewsToListView(ArrayList<ReviewListElement> reviewObjectList){

        // add all review objects from database into the list view
        theList.addAll(reviewObjectList);

        // We need to do have this for when user submits a review to the database.
        // Prevents over writing of current reviews
        numberOfCurrentReviews = theList.size();


    }

}