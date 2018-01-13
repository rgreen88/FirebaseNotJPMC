package com.example.ryne.jpmclookalikemvp.model.util;


import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * Created by Ryne on 1/11/2018.
 */

public class CipherHandler {

    private final Cipher cipher;
    private String transformation;

    //Cipher constructor
    public CipherHandler(String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException{

        this.transformation = transformation;

        cipher = Cipher.getInstance(transformation);
    }

    //encrypt method
    public String encrypt(String data, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException{

        //initiating encrypt mode for key
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //creating bytes array and encrypting data using .getBytes method
        byte[] bytes = cipher.doFinal(data.getBytes());

        //returning encryption data using Base64 default
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

    //decrypt method
    public String decrypt(String data, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException{

        //initializing decrypt mode for key
        byte[] encryptedData = Base64.decode(data, Base64.DEFAULT);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        //convert decryptedData to String upon return
        return new String(decryptedData);
    }
}
