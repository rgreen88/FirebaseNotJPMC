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

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

/**
 * Created by Ryne on 1/11/2018.
 */

public class FirebaseDbModel extends BaseActivity {

    private DatabaseReference jpmcRef; //private reference for adding/using database data
    private CipherHandler cipherHandler;
    private Key key;

    public FirebaseDbModel() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dummy_layout);

        try {
            getInstance();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        getCheckingAccount();
        getTransactionsAccount();
        getMarketingInvestments();
    }

    public void getInstance() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {


        // Write a message to the database...may need to space out reference objects
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Cipher
        jpmcRef = database.getReference(cipherHandler.encrypt("Customer", key));
        jpmcRef.setValue(cipherHandler.encrypt("Ryne Green", key));

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = null;//hashmap to string issues
                try {
                    value = cipherHandler.decrypt(dataSnapshot.getValue(String.class), key);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
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
        //TODO: Encrypt
        jpmcRef = database.getReference("Checking Account");
        jpmcRef.setValue("Balance: 1000.00");
        jpmcRef.setValue("Avalailable Credit: 1000.00");

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: Decrypt
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
        //TODO: Encrypt
        jpmcRef = database.getReference("Transactions");
        jpmcRef.setValue("Billed: 0.00");
        jpmcRef.setValue("Avalailable Credit: 1000.00");

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: Decrypt
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
        //TODO: Encrypt
        jpmcRef = database.getReference("Marketing Investments Account");
        jpmcRef.push().setValue("Value");
        jpmcRef.push().push().setValue("$1000.00");


        //Market info current date
        //TODO: Encrypt
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
                //TODO: Decrypt
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
}