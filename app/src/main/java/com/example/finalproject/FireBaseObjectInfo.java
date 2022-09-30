package com.example.finalproject;

import java.util.Date;

public class FireBaseObjectInfo {

    // Data members
    private boolean tableReserved;
    private int     numPeople;
    private Date    timeSlot;


    // Constructor
    public FireBaseObjectInfo(boolean isReserved, int numPeople, Date timeSlot){
        this.tableReserved = isReserved;
        this.numPeople  = numPeople;
        this.timeSlot   = timeSlot;
    }

    // Getters
    public boolean getTableReserved() { return tableReserved; }
    public int getNumPeople() { return numPeople; }
    public Date getTimeSlot() { return timeSlot; }

    // Setters
    private void setNumPeople(int numPeople) { this.numPeople = numPeople; }
    private void setTimeSlot(Date timeSlot) { this.timeSlot = timeSlot; }
    private void setTableReserved(boolean tableReserved) { this.tableReserved = tableReserved; }
}
