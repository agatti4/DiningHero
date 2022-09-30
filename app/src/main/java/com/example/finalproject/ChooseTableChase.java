package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class ChooseTableChase extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerTable;
    private Spinner spinnerTimeSlot;
    private boolean choseValidTable = false;
    private boolean validTimeChosen = false;
    private int     tableNumberForIntent;
    private int     timeSlotNumber = 0;
    private String  timeForIntent;
    private FireBaseObjectInfo globalObject;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_table_chase);

        // get the intent that got us here
        Intent callerIntent = getIntent();

        spinnerTable = (Spinner) findViewById(R.id.ChaseChooseTable);
        String[] tableItems = {"Table #", "Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 6",
                "Table 7", "Table 8", "Table 9", "Table 10", "Table 11", "Table 12", "Table 13"};
        ArrayAdapter<String> tableChoiceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tableItems);

        tableChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTable.setAdapter(tableChoiceAdapter);
        spinnerTable.setOnItemSelectedListener(this);
/*
----------------------------------------------------------------------------------------------------
 */
        spinnerTimeSlot = (Spinner) findViewById(R.id.ChaseChooseTimeSlot);
        String[] timeSlotItems = {"Time Slot:", "7:00am - 7:45am", "7:45am - 8:30am", "8:30am - 9:15am", "9:15am - 10:00am", "10:00am - 10:45am",
                "10:45am - 11:30am", "11:30am - 12:15pm", "12:15pm - 1:00pm", "1:00pm - 1:45pm", "1:45pm - 2:30pm", "2:30pm - 3:15pm",
                "3:15pm - 4:00pm", "4:00pm - 4:45pm", "4:45pm - 5:30pm", "5:30pm - 6:15pm", "6:15pm - 7:00pm", "7:00pm - 7:45pm",
                "7:45pm - 8:30pm", "8:30pm - 9:15pm"};
        ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSlotItems);

        timeSlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeSlot.setAdapter(timeSlotAdapter);
        spinnerTimeSlot.setOnItemSelectedListener(this);


        Button confirmTable = findViewById(R.id.confirmButton);
        confirmTable.setOnClickListener(confirmTableListener);

        // WARNING
        // DO NOT USE THIS FUNCTION UNLESS YOU WANT TO RESET THE WHOLE DATA BASE
        // This voids all reservations at every table (clean slate)
        // createDataInDataBase();

    }



    /**
     * This is the button listener to move to the confirmation page.
     * Two booleans are set at the top of this page. If both of them are true,
     * start the intent.
     * Else, Display an error message and say a valid table and/or time slot was not chosen
     * and do not perform the intent
     */
    private View.OnClickListener confirmTableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int numberOfPeople = checkNumberOfPeople();
            if (choseValidTable == true && validTimeChosen == true && numberOfPeople != -1) {
                writeDataToFireBaseAndMoveToIntent(numberOfPeople);
            }

            // This is a very weird case where if a user selected an invalid time slot for a table,
            // and then chooses a valid one, it does notwork because the table variable is false.
            // To prevent that, just start the firebase process again to update the table boolean
            // if user selected a valid table
            else if (choseValidTable == false && validTimeChosen == true) {
                startFireBaseProcess("table" + tableNumberForIntent, tableNumberForIntent);

                Toast displayMessage = Toast.makeText(ChooseTableChase.this, "If table is unreserved, press the confirm button again!", Toast.LENGTH_LONG);
                displayMessage.setGravity(Gravity.CENTER_HORIZONTAL, 0,225);
                displayMessage.show();

            }

            else {
                if (numberOfPeople == -1)
                    Toast.makeText(ChooseTableChase.this, "An invalid number of people was entered! 1-10 Persons Only!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ChooseTableChase.this, "An invalid table or time slot was chosen!", Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * Occurs when a user clicks on one of the drop down menus. (Spinner)
     * Spinner is a parent ID so we can check which spinner was chosen (Table or timeslot)
     */
    public void onItemSelected(@NonNull AdapterView<?> parent, View v, int position, long id) {


        // Will most likely not be used
        Toast tableChosenToast;

        if (parent.getId() == R.id.ChaseChooseTable) {
            switch (position) {
                case 0:
                    break;

                case 1:
                    startFireBaseProcess("table1", 1);
                    break;

                case 2:
                    startFireBaseProcess("table2", 2);
                    break;

                case 3:
                    startFireBaseProcess("table3", 3);
                    break;

                case 4:
                    startFireBaseProcess("table4", 4);
                    break;

                case 5:
                    startFireBaseProcess("table5", 5);
                    break;

                case 6:
                    startFireBaseProcess("table6", 6);
                    break;

                case 7:
                    startFireBaseProcess("table7", 7);
                    break;

                case 8:
                    startFireBaseProcess("table8", 8);
                    break;

                case 9:
                    startFireBaseProcess("table9", 9);
                    break;

                case 10:
                    startFireBaseProcess("table10", 10);
                    break;

                case 11:
                    startFireBaseProcess("table11", 11);
                    break;

                case 12:
                    startFireBaseProcess("table12", 12);
                    break;

                case 13:
                    startFireBaseProcess("table13", 13);
                    break;

                default:
                    Toast.makeText(this, "No table chosen!", Toast.LENGTH_SHORT).show();

            }
        }

        else if (parent.getId() == R.id.ChaseChooseTimeSlot) {

            // MUST FOLLOW THIS CONDITION
            // "hh:mm aa"
            // Will be used inside function to compare passed time to current time
            switch (position) {
                case 0:
                    break;

                case 1:
                    timeForIntent = "07:00:00";
                    timeSlotNumber = 1;
                    isTimeSlotValid("07:00:00");    //7:00am
                    break;

                case 2:
                    timeForIntent = "07:45:00";
                    timeSlotNumber = 2;
                    isTimeSlotValid("07:45:00");    //7:45am
                    break;

                case 3:
                    timeForIntent = "08:30:00";
                    timeSlotNumber = 3;
                    isTimeSlotValid("08:30:00");    //8:30am
                    break;

                case 4:
                    timeForIntent = "09:15:00";
                    timeSlotNumber = 4;
                    isTimeSlotValid("09:15:00");    //9:15am
                    break;

                case 5:
                    timeForIntent = "10:00:00";
                    timeSlotNumber = 5;
                    isTimeSlotValid("10:00:00");    //10:00am
                    break;

                case 6:
                    timeForIntent = "10:45:00";
                    timeSlotNumber = 6;
                    isTimeSlotValid("10:45:00");    //10:45am
                    break;

                case 7:
                    timeForIntent = "11:30:00";
                    timeSlotNumber = 7;
                    isTimeSlotValid("11:30:00");    //11:30am
                    break;

                case 8:
                    timeForIntent = "12:15:00";
                    timeSlotNumber = 8;
                    isTimeSlotValid("12:15:00");    //12:15pm
                    break;

                case 9:
                    timeForIntent = "13:00:00";
                    timeSlotNumber = 9;
                    isTimeSlotValid("13:00:00");    //1:00pm
                    break;

                case 10:
                    timeForIntent = "13:45:00";
                    timeSlotNumber = 10;
                    isTimeSlotValid("13:45:00");    //1:45pm
                    break;

                case 11:
                    timeForIntent = "14:30:00";
                    timeSlotNumber = 11;
                    isTimeSlotValid("14:30:00");    //2:30pm
                    break;

                case 12:
                    timeForIntent = "15:15:00";
                    timeSlotNumber = 12;
                    isTimeSlotValid("15:15:00");    //3:15pm
                    break;

                case 13:
                    timeForIntent = "16:00:00";
                    timeSlotNumber = 13;
                    isTimeSlotValid("16:00:00");    //4:00pm
                    break;

                case 14:
                    timeForIntent = "16:45:00";
                    timeSlotNumber = 14;
                    isTimeSlotValid("16:45:00");    //4:45pm
                    break;

                case 15:
                    timeForIntent = "17:30:00";
                    timeSlotNumber = 15;
                    isTimeSlotValid("17:30:00");    //5:30pm
                    break;

                case 16:
                    timeForIntent = "18:15:00";
                    timeSlotNumber = 16;
                    isTimeSlotValid("18:15:00");    //6:15pm
                    break;

                case 17:
                    timeForIntent = "19:00:00";
                    timeSlotNumber = 17;
                    isTimeSlotValid("19:00:00");    //7:00pm
                    break;

                case 18:
                    timeForIntent = "19:45:00";
                    timeSlotNumber = 18;
                    isTimeSlotValid("19:45:00");    //7:45pm
                    break;

                case 19:
                    timeForIntent = "20:30:00";
                    timeSlotNumber = 19;
                    isTimeSlotValid("20:30:00");    //8:30pm
                    break;

                default:
                    tableChosenToast = Toast.makeText(this, "No table chosen!", Toast.LENGTH_SHORT);

            }
        }

    }



    // THIS IS VERY IMPORTANT, DO NOT DELETE
    // This allows the object extracted from the data base to be passed.
    // Extracting from a database is treated as an async like task so we need an
    // onSuccess function that is called from a listener
    public interface OnGetDataListener {

        //this is for callbacks
        void onSuccess(FireBaseObjectInfo objectFromDataBase);
        void onStart();
        void onFailure();
    }


    /* Call the getDataFromFireBase Function

       This uses a process of callbacks and an interface
       Essentially, in order for data to be accessed correctly,
       the three below functions must be used to prevent null pointer exceptions.
       This would occur because getting data from the firestore is done on a separate
       thread so we cannot use a variable that has not been computed and returned from
       the separate thread.
        */
    private void startFireBaseProcess(String tableID, int tableNum) {

        tableNumberForIntent = tableNum;
        getDataFromFireBase(tableID, new OnGetDataListener() {


            /**
             * When the specific table object is filled from the firestore database,
             * send it to isTableReserved() to check if it is currently reserved
             */
            @Override
            public void onSuccess(FireBaseObjectInfo objectFromDataBase) {
                isTableReserved(objectFromDataBase, tableNum);
            }

            @Override
            public void onStart() {
                // May be able to delete this
            }

            @Override
            public void onFailure() {
                // May be able to delete this
            }
        });
    }


    /**
     * Receives a string containing the tableID for the firebase extraction
     * Need to pass a listener in order to be able to use call backs.
     * Confusing, but it is necessary in order to pass the listener the object data
     * extracted from the database
     **/
    public void getDataFromFireBase(String tableID, final OnGetDataListener listener) {

        FirebaseFirestore database = FirebaseFirestore.getInstance();


        DocumentReference docRef = database.collection("chaseReservations").document(tableID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.e("Info", "Document exists");


                        // Create variable that will be used to create an object that is returned
                        boolean isReservedBool = false;
                        int numPeopleInt;
                        Date timeSlot;

                        // Extract fields from the data base using dummy variables to get strings
                        String isReservedStr = document.getData().get("boolReserved"+timeSlotNumber).toString();
                        String numPeopleStr = document.getData().get("numPeople"+timeSlotNumber).toString();
                        Timestamp timeStamp = (Timestamp) document.getData().get("timeSlot"+timeSlotNumber);


                        // Convert data to their respective data types
                        // Only need the "true" case, isReservedBool is already false
                        assert timeStamp != null;

                        if (isReservedStr.equals("true"))
                            isReservedBool = true;

                        numPeopleInt = Integer.parseInt(numPeopleStr);
                        timeSlot = timeStamp.toDate();

                        // Create new object from extracted data (table information from data base)
                        FireBaseObjectInfo objectFromDataBase = new FireBaseObjectInfo(isReservedBool, numPeopleInt, timeSlot);

                        globalObject = objectFromDataBase;

                        // Pass the new object created from the firestore data base.
                        // By doing it this was, the listener will pass it to onSuccess()
                        // Where it can be used in the main thread!
                        listener.onSuccess(objectFromDataBase);

                    } else {
                        Log.e("Info", "No Such Document Exists");
                    }
                } else {
                    Log.e("Info", "Error Getting Data", task.getException());
                }
            }
        });
    }


    /**
     * Check if the boolean reserved field in the firebase object is true or false
     * display a toast with the results
     * @param objectFromFireStore (Object created from the firestore extraction)
     * @param tableNum (table number)
     */
    private void isTableReserved(FireBaseObjectInfo objectFromFireStore, int tableNum) {

        // Check data that was stored correctly in object from .onSuccess
        // tableNum-1 to account for array index
        Log.e("Info", "Checking table" + tableNum);
        Log.e("Info", "boolReserved -> " + objectFromFireStore.getTableReserved());
        Log.e("Info", "numPeople -> " + objectFromFireStore.getNumPeople());
        Log.e("Info", "timeSlot -> " + objectFromFireStore.getTimeSlot());
        Log.e("Info", "-----------------------");


        Toast isTableReservedToast = null;

        // When a user reserves a table, the data base changed the reserve boolean for that time slot
        // to true. However, if it is the next day, someone should be able to reserve it again despite
        // the boolean stating that the time slot is reserved. So, we will need to compare date/times
        // whenever the getTableReserved() returns true. This is in the else if statement below.
        // But first, we need to initialize date variables.

        // Get timeslot from database
        Date reservedTime = objectFromFireStore.getTimeSlot();

        // Parse string of current time into a date that is comparable to reservedTime
        Date currentDate = new Date();

        Log.e("Info", "time for reservedTime variable => " + reservedTime);
        Log.e("Info", "time for currentDate variable => " + currentDate);

        // Get the difference in days between the reservedTime and the currentTime
        int dateDifference = (int) getDateDiff(reservedTime, currentDate);
        Log.e("dateDifference: ", String.valueOf(dateDifference));

        // If table reserved boolean from firestore object = false
        if (objectFromFireStore.getTableReserved() == false) {
            isTableReservedToast = Toast.makeText(this, "Table " + tableNum + " is NOT currently reserved!", Toast.LENGTH_LONG);
            tableNumberForIntent = tableNum;        // This will be used to pass the table number to confirmation page intent
            choseValidTable = true;                 // This bool (including another) must be true if user wants to proceed when clicking button
        }
        // If table reserved boolean from firestore object = true
        else if (objectFromFireStore.getTableReserved() == true){

            // This condition means that the user is trying to reserve a table that has already been reserved. However,
            // if the difference in # of days is > 0, then you will be able to reserve it.
            if (dateDifference>0){
                isTableReservedToast = Toast.makeText(this, "Table " + tableNum + " is NOT currently reserved!", Toast.LENGTH_LONG);
                tableNumberForIntent = tableNum;
                choseValidTable = true;
            }
            else {
                isTableReservedToast = Toast.makeText(this, "Table " + tableNum + " is currently reserved at that time slot!", Toast.LENGTH_LONG);
                choseValidTable = false;
            }
        }


        isTableReservedToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
        isTableReservedToast.show();

    }


    /**
     * Check if the chosen time slot is before or after the current time
     * Display a toast with the results
     * @param chosenTime
     */
    private void isTimeSlotValid(String chosenTime) {

        Log.e("Info", "Inside isTimeSlotValid()");

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            // Parse string of current time into a date that is comparable to chosenTime
            Date currentDate = sdf.parse(currentTime);
            Log.e("Info", "Current time in date format => " + currentDate);


            // Convert the passed in string time to current time that is comparable to above date we made
            Date userChosenTime = null;
            userChosenTime = sdf.parse(chosenTime);

            Log.e("Info", "Chosen time in date format => " + userChosenTime);

            Log.e("Info", "-----------------------");



            // Check if the chosen time slot (turned into a date) is before or after current time/day
            if (userChosenTime.after(currentDate)) {
                Toast.makeText(this, "Time slot is valid!", Toast.LENGTH_SHORT).show();
                validTimeChosen = true;
            }

            else if (userChosenTime.before(currentDate)) {
                Toast.makeText(this, "Time slot is NOT valid!", Toast.LENGTH_SHORT).show();
                validTimeChosen = false;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }



    /**
     * THIS MUST BE INCLUDED, DO NOT DELETE
     * @param parent
     */
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    // Not sure if this is needed, but don't delete regardless
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    /**
     *
     * @return value of numberOfPeopleINT. If -1, invalid number was entered. Else, valid number
     */
    private int checkNumberOfPeople(){

        EditText numOfPeopleET = findViewById(R.id.numberOfPeopleAtTable);

        String value = numOfPeopleET.getText().toString();
        int numberOfPeopleINT = Integer.parseInt(value);

        if (numberOfPeopleINT > 10)
            numberOfPeopleINT = -1;

        else if (numberOfPeopleINT <= 0)
            numberOfPeopleINT = -1;

        // If no statements are hit, this number is valid. Else, it equals -1
        Log.e("Info", "Number of people -> " + numberOfPeopleINT);
        Log.e("Info", "---------------------");

        return numberOfPeopleINT;
    }



    /**
     * This function writes the data to the tableID in the firestore firebase
     * Gets the timetamp needed through some conversions and then uses a hashMap to put
     * data into the document from firestore. After that is done, perform the intent
     * @param numPeopleAtTable passed the number of people at table to be reserved
     */
    private void writeDataToFireBaseAndMoveToIntent(int numPeopleAtTable){

        // Set up variables to write to firebase
        String tableID = "table" + tableNumberForIntent;                   // TableID to get firestore document
        Timestamp timestamp = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


        // Get Current date (DD/MM/YYYY)
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        try {
            today = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert today != null;
        Log.e("Info:", "Current Date -> " + today);

        // Gets the current calendar day with our desired timeslot using substrings of timeForIntent
        // Example: 19:45:00 (hr = 19, min = 45, sec = 00
        Calendar currentCalendarDay = Calendar.getInstance();
        currentCalendarDay.setTime(today);
        currentCalendarDay.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeForIntent.substring(0,2)));
        currentCalendarDay.set(Calendar.MINUTE, Integer.parseInt(timeForIntent.substring(3,5)));
        currentCalendarDay.set(Calendar.SECOND, Integer.parseInt(timeForIntent.substring(6,7)));



        Log.e("Info:", "Current Calendar Date -> " + currentCalendarDay.getTime());



        // Write to Firebase
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Convert calendar to date type
        Date willConvertToTimeStamp = currentCalendarDay.getTime();

        // Convert date to timestamp to enter into firebase field
        timestamp = new Timestamp(willConvertToTimeStamp);


        Log.e("Update Fire Base:", "Table being updated is table" + tableNumberForIntent);
        Log.e("Update Fire Base:", "Num of people -> " + numPeopleAtTable);
        Log.e("Update Fire Base:", "boolReserved switched to True");
        Log.e("Update Fire Base:", "TimeStamp being uploaded -> " + timestamp);



        // Create HashMap to use as a setter for the variables in the table for the firestore
        // tableNumberForIntent is used to find specific time slot
        // Ex: boolReserved9, numPeople9, timeSlot9
        Map<String, Object> firestoreObjectUpdate = new HashMap<>();
        firestoreObjectUpdate.put("boolReserved" + timeSlotNumber, true);
        firestoreObjectUpdate.put("numPeople" +  timeSlotNumber, numPeopleAtTable);
        firestoreObjectUpdate.put("timeSlot" + timeSlotNumber, timestamp);


        // To update table document
        database.collection("chaseReservations").document(tableID)
                .update(firestoreObjectUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("Update Fire Base:", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Update Fire Base:", "Error overwriting document!", e);
                    }
                });






        // Start Intent
        Intent intent = new Intent(getApplicationContext(), TableConfirmation.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            intent.putExtra("Table Number", tableNumberForIntent);
            intent.putExtra("Date and Time", timeForIntent);

            startActivity(intent);
        }
    }




    /**
     *
     * @param startDate the reserve date times slot chosen by user previously
     * @param endDate   the currentDate in time
     * @return          returns the # of days in difference between start and end dates
     */
    public long getDateDiff(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;


        long elapsedDays = different / daysInMilli;

        Log.e("startDate: ", startDate.toString());
        Log.e("endDate: ", endDate.toString());

        return elapsedDays;
    }




    /**
     *
     * THIS FUNCTION'S SOLE PURPOSE IS TO POPULATE ALL TABLES IN THE CHASERESERVATIONS
     * COLLECTION OF THE DATABASE. THIS SHOULD NOT BE TOUCHED UNLESS YOU KNOW WHAT YOU ARE DOING.
     * IF YOU CALL THIS FUNCTION, EVERY SINGLE VARIABLE IN EVERY DOCUMENT WILL GET RESET.
     */
    private void createDataInDataBase(){

        // Write to Firebase
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Timestamp timestamp = null;

        // Get Current date (DD/MM/YYYY)
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        try {
            today = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert today != null;
        Log.e("Info:", "Current Date -> " + today);

        Calendar currentCalendarDay = Calendar.getInstance();
        currentCalendarDay.setTime(today);
//        currentCalendarDay.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeForIntent.substring(0,2)));
//        currentCalendarDay.set(Calendar.MINUTE, Integer.parseInt(timeForIntent.substring(3,5)));
//        currentCalendarDay.set(Calendar.SECOND, Integer.parseInt(timeForIntent.substring(6,7)));

        // Convert calendar to date type
        Date willConvertToTimeStamp = currentCalendarDay.getTime();

        // Convert date to timestamp to enter into firebase field
        timestamp = new Timestamp(willConvertToTimeStamp);






        Map<String, Object> fireStoreObject = new HashMap<>();


        fireStoreObject.put("boolReserved1", false);
        fireStoreObject.put("numPeople1", 0);
        fireStoreObject.put("timeSlot1", timestamp);

        fireStoreObject.put("boolReserved2", false);
        fireStoreObject.put("numPeople2", 0);
        fireStoreObject.put("timeSlot2", timestamp);

        fireStoreObject.put("boolReserved3", false);
        fireStoreObject.put("numPeople3", 0);
        fireStoreObject.put("timeSlot3", timestamp);

        fireStoreObject.put("boolReserved4", false);
        fireStoreObject.put("numPeople4", 0);
        fireStoreObject.put("timeSlot4", timestamp);

        fireStoreObject.put("boolReserved5", false);
        fireStoreObject.put("numPeople5", 0);
        fireStoreObject.put("timeSlot5", timestamp);

        fireStoreObject.put("boolReserved6", false);
        fireStoreObject.put("numPeople6", 0);
        fireStoreObject.put("timeSlot6", timestamp);

        fireStoreObject.put("boolReserved7", false);
        fireStoreObject.put("numPeople7", 0);
        fireStoreObject.put("timeSlot7", timestamp);

        fireStoreObject.put("boolReserved8", false);
        fireStoreObject.put("numPeople8", 0);
        fireStoreObject.put("timeSlot8", timestamp);

        fireStoreObject.put("boolReserved9", false);
        fireStoreObject.put("numPeople9", 0);
        fireStoreObject.put("timeSlot9", timestamp);

        fireStoreObject.put("boolReserved10", false);
        fireStoreObject.put("numPeople10", 0);
        fireStoreObject.put("timeSlot10", timestamp);

        fireStoreObject.put("boolReserved11", false);
        fireStoreObject.put("numPeople11", 0);
        fireStoreObject.put("timeSlot11", timestamp);

        fireStoreObject.put("boolReserved12", false);
        fireStoreObject.put("numPeople12", 0);
        fireStoreObject.put("timeSlot12", timestamp);

        fireStoreObject.put("boolReserved13", false);
        fireStoreObject.put("numPeople13", 0);
        fireStoreObject.put("timeSlot13", timestamp);

        fireStoreObject.put("boolReserved14", false);
        fireStoreObject.put("numPeople14", 0);
        fireStoreObject.put("timeSlot14", timestamp);

        fireStoreObject.put("boolReserved15", false);
        fireStoreObject.put("numPeople15", 0);
        fireStoreObject.put("timeSlot15", timestamp);

        fireStoreObject.put("boolReserved16", false);
        fireStoreObject.put("numPeople16", 0);
        fireStoreObject.put("timeSlot16", timestamp);

        fireStoreObject.put("boolReserved17", false);
        fireStoreObject.put("numPeople17", 0);
        fireStoreObject.put("timeSlot17", timestamp);

        fireStoreObject.put("boolReserved18", false);
        fireStoreObject.put("numPeople18", 0);
        fireStoreObject.put("timeSlot18", timestamp);

        fireStoreObject.put("boolReserved19", false);
        fireStoreObject.put("numPeople19", 0);
        fireStoreObject.put("timeSlot19", timestamp);



        database.collection("chaseReservations").document("table1")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table2")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table3")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table4")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table5")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table6")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table7")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table8")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });

        database.collection("chaseReservations").document("table9")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });

        database.collection("chaseReservations").document("table10")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table11")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table12")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
        database.collection("chaseReservations").document("table13")
                .set(fireStoreObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Database", "Data Base overwritten!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Database", "Error overwriting data base!", e);
                    }
                });
    }

}