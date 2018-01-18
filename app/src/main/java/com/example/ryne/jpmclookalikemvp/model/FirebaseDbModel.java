package com.example.ryne.jpmclookalikemvp.model;

import android.util.Log;

import com.example.ryne.jpmclookalikemvp.presenter.MainPresenter;
import com.example.ryne.jpmclookalikemvp.view.MainContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

/**
 * Created by Ryne on 1/11/2018.
 */

public class FirebaseDbModel extends MainPresenter {

    //empty constructor
//    public FirebaseDbModel() {
//    }

    private DatabaseReference jpmcRef; //private reference for adding/using database data

    public FirebaseDbModel(MainContract.View mView) {
        super(mView);
    }

    public void getInstance() {

        //setting up dummy data
        // Write a message to the database...may need to space out reference objects
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        jpmcRef = database.getReference("Customer");
        jpmcRef.setValue("Ryne Green");

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

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