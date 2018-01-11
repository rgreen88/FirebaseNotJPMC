package com.example.ryne.jpmclookalikemvp.presenter;

import com.example.ryne.jpmclookalikemvp.view.MainContract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ryne on 1/11/2018.
 */

public class MainPresenter implements MainContract.Presenter{

    MainContract.View mView;

    //constructor to bind mView
    public MainPresenter (MainContract.View mView){
        this.mView = mView; //binding relationship from contract to presenter
    }

    @Override
    public void getInstance() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        String readFromDb = myRef.toString();
    }
}
