package com.example.ryne.jpmclookalikemvp.view;

/**
 * Created by Ryne on 1/11/2018.
 */

public interface MainContract {

    interface  View {

        void onDataChange(String readFromDb);

    }

    interface Presenter{

        void getInstance();

    }
}
