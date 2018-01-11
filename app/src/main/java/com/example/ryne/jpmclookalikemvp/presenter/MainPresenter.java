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
    public void getInstance(String readFromDb) {
        readFromDb = firebaseDbModel.getInstance();
        mView.showInstance(readFromDb);
    }

    @Override
    public void onDataChanged(String writeToDb) {
        writeToDb = firebaseDbModel.onDataChanged();
        mView.showInstance(writeToDb);
    }
}
