package com.example.ryne.jpmclookalikemvp.model;

import android.os.Bundle;
import android.util.Log;

import com.example.ryne.jpmclookalikemvp.R;
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

    public FirebaseDbModel() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dummy_layout);

        getInstance();
    }

    public void getInstance() {

        //setting up dummy data: created FbDummyData.class to clean model
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
}