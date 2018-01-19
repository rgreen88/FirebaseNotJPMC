package com.example.ryne.jpmclookalikemvp.view;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Created by Ryne on 1/11/2018.
 */

public interface MainContract {

    interface  View {

        void showInstance();
    }

    interface Presenter{

        void getInstance() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException;
    }
}
