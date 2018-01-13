package com.example.ryne.jpmclookalikemvp.model.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

/**
 * Created by Ryne on 1/12/2018.
 */

public class KeyStoreHandler {

    private Context context;

    //Final to TAG, KEYSTORE_PROVIDER, ALGORITHM
    private static final String TAG = "KeyStoreWrapTag";
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    public static final String CURRENT_ALGORITHM = "RSA";

    //Key generator and key storage
    private KeyPairGenerator keyPairGenerator;
    private KeyStore keyStore;

    //constructor running handler each start
    public KeyStoreHandler(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException{

        this.context = context;
        initHandler();
    }

    private void initHandler() throws  CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException{

        keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);
        keyStore.load(null);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public KeyPair createAKSKeyPair(String alias) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException{

        keyPairGenerator = KeyPairGenerator.getInstance(CURRENT_ALGORITHM, KEYSTORE_PROVIDER); //algorith = ecrypt key type---KEYSTORE_PROVIDER = manufacturer (?)

        initGeneratorWithKeyPairParam(keyPairGenerator, alias);
        return keyPairGenerator.generateKeyPair();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initGeneratorWithKeyPairParam(KeyPairGenerator keyPairGenerator, String alias) throws InvalidAlgorithmParameterException{

        Log.d(TAG, "initGenKeyPairWithParamSpecs: ");
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //adding date to have first two digits to start with 20 to indicate 2000
        endDate.add(Calendar.YEAR, 20);

        //KeyPairGeneratorSpec deprecated in api 23 --- using KeyGenParameterSpec in KeyGen method
        KeyPairGeneratorSpec keyPairSpec = new KeyPairGeneratorSpec.Builder(context)
                .setAlias(alias)
                .setSerialNumber(BigInteger.ONE)
                .setSubject(new X500Principal("CN=${alias} CA Certificate"))
                .setStartDate(startDate.getTime())
                .setEndDate(endDate.getTime())
                .build();

        keyPairGenerator.initialize(keyPairSpec);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initGeneratorWithKeyGenParamSpec(KeyPairGenerator keyPairGenerator, String alias) throws InvalidAlgorithmParameterException {

        Log.d(TAG, "initGeneratorWithKeyGenParamSpec: ");

        //KeyGenParameterSpec for newer devices being targeted
        KeyGenParameterSpec keyGenSpec = new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build();

        keyPairGenerator.initialize(keyGenSpec);
    }

    public boolean hasMarshMellow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

//    public KeyPair

}
