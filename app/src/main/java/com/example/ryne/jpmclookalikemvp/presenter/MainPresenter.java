package com.example.ryne.jpmclookalikemvp.presenter;

import com.example.ryne.jpmclookalikemvp.model.FirebaseDbModel;
import com.example.ryne.jpmclookalikemvp.view.MainContract;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;


/**
 * Created by Ryne on 1/11/2018.
 */

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;
    private FirebaseDbModel firebaseDbModel;

    //constructor to bind mView
    public MainPresenter (MainContract.View mView){
        this.mView = mView; //binding relationship from contract to presenter
        firebaseDbModel = new FirebaseDbModel();
    }

    @Override
    public void getInstance() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        firebaseDbModel.getDbInstance();
        mView.showInstance();
    }
}
