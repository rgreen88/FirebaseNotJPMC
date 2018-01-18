package com.example.ryne.jpmclookalikemvp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rynel on 1/18/2018.
 */

public class FbDummyData {

    private DatabaseReference jpmcRef; //private reference for adding/using database data

    //explicitly writing to database only
    public void getCheckingAccount (){
        //checking info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        jpmcRef = database.getReference("Checking Account");
        jpmcRef.push().setValue("Balance: 1000.00");
        jpmcRef.push().setValue("Avalailable Credit: 1000.00");
    }

    public void getTransactionsAccount (){
        //Transactions info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        jpmcRef = database.getReference("Transactions");
        jpmcRef.push().setValue("Billed: 0.00");
        jpmcRef.push().setValue("Avalailable Credit: 1000.00");
    }

    public void getMarketingInvestments (){
        //Market info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        jpmcRef = database.getReference("Marketing Investments Account");
        jpmcRef.push().setValue("$1000.00");
        jpmcRef.push().setValue("Value");

        //Market info current date
        jpmcRef = database.getReference("As of (Current Date)");
        jpmcRef.setValue("$1000000.00 (increase symbol)"); //TextView on left (UNREALIZED GAIN/LOSS)
        jpmcRef.setValue( "10000.00 (increase symbol)"); //TextView on left (TODAY'S CHANGE)
        //Three TextViews divided by the above using view: Left: Price, Center: Unrealized Gain/Loss, Right: "Value"
        jpmcRef.setValue("10.00");                       //under Price an Equity division before info on left
        jpmcRef.setValue("10000.00 (decrease symbol)");
        jpmcRef.setValue("$100000.00");
    }
}
