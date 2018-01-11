package com.example.ryne.jpmclookalikemvp.presenter;

import android.util.Log;

import com.example.ryne.jpmclookalikemvp.model.FirebaseDbModel;
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

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;
    private FirebaseDbModel firebaseDbModel;
    private DatabaseReference myRef; //private reference for use in different methods

    //constructor to bind mView
    public MainPresenter (MainContract.View mView){
        this.mView = mView; //binding relationship from contract to presenter
        firebaseDbModel = new FirebaseDbModel();
    }

    @Override
    public void getInstance() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        String readFromDb = myRef.setValue("Hello, World!").toString();
        mView.showInstance(readFromDb);
    }

    @Override
    public void onDataChanged() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
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
