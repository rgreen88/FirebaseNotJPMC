package com.example.ryne.jpmclookalikemvp.model;

import android.os.Bundle;
import android.util.Log;

import com.example.ryne.jpmclookalikemvp.R;
import com.example.ryne.jpmclookalikemvp.model.util.CipherHandler;
import com.example.ryne.jpmclookalikemvp.view.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

/**
 * Created by Ryne on 1/11/2018.
 */

public class FirebaseDbModel extends BaseActivity {

    private DatabaseReference jpmcRef; //private reference for adding/using database data
    CipherHandler cipherHandler;

    public FirebaseDbModel() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dummy_layout);

        getInstance();
        getCheckingAccount();
        getTransactionsAccount();
        getMarketingInvestments();
    }

    public void getInstance() {


        // Write a message to the database...may need to space out reference objects
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Cipher
        jpmcRef = database.getReference("Customer");
        jpmcRef.setValue("Ryne Green");

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);//hashmap to string issues
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
        //TODO: Cipher
        jpmcRef = database.getReference("Checking Account");
        jpmcRef.setValue("Balance: 1000.00");
        jpmcRef.setValue("Avalailable Credit: 1000.00");

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

    public void getTransactionsAccount (){
        //Transactions info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Cipher
        jpmcRef = database.getReference("Transactions");
        jpmcRef.setValue("Billed: 0.00");
        jpmcRef.setValue("Avalailable Credit: 1000.00");

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

    public void getMarketingInvestments (){
        //Market info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Cipher
        jpmcRef = database.getReference("Marketing Investments Account");
        jpmcRef.push().setValue("Value");
        jpmcRef.push().push().setValue("$1000.00");


        //Market info current date
        //TODO: Cipher
        jpmcRef = database.getReference("As of (Current Date)");
        jpmcRef.setValue("$1000000.00 (increase symbol)"); //TextView on left (UNREALIZED GAIN/LOSS)
        jpmcRef.setValue( "10000.00 (increase symbol)"); //TextView on left (TODAY'S CHANGE)
        //Three TextViews divided by the above using view: Left: Price, Center: Unrealized Gain/Loss, Right: "Value"
        jpmcRef.setValue("10.00");                       //under Price an Equity division before info on left


        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class); //com.google.firebase.database.DatabaseException: Failed to convert value of type java.util.HashMap to String
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}