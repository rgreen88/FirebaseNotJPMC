package com.example.ryne.jpmclookalikemvp.presenter;

import com.example.ryne.jpmclookalikemvp.view.MainContract;

/**
 * Created by Ryne on 1/11/2018.
 */

public class MainPresenter implements MainContract.Presenter{

    MainContract.View mView;

    public MainPresenter (MainContract.View mView){
        this.mView = mView; //binding relationship from contract to presenter
    }

    @Override
    public void getInstance() {

    }
}
