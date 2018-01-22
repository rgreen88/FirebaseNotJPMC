package com.example.ryne.jpmclookalikemvp.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;
import com.example.ryne.jpmclookalikemvp.model.util.CipherHandler;
import com.example.ryne.jpmclookalikemvp.model.util.KeyStoreHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by rynel on 1/16/2018.
 */

public class TransactionsActivity extends BaseActivity {

    //TODO: rv adapter containing a separate card view setup for TransactionActivity

    //TextViews
    TextView mGreeting, mCheckingAccount, mCurrentDate, mCurrency, mPayBills;

    //CipherHandler for decrypt
    CipherHandler cipherHandler;
    private static final String TAG = "MainActivityTag";
    private static final java.lang.String TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding";
    private String alias = "master_key";
    private KeyStoreHandler keyStoreHandler;
    private KeyPair masterKey;

    //Database Ref object
    DatabaseReference mCustomer;

    //creating CheckingAccountAdapter variable called mAdapter
    TransactionsAdapter mAdapter;

    //creating RecyclerView variable called mWeatherList
    private RecyclerView mCheckingAccountList;

    private static final int NUM_LIST_ITEMS = 50;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        //binding views
        mGreeting = findViewById(R.id.tv_greeting);
        mCurrentDate = findViewById(R.id.tv_current_date);
        mCheckingAccount = findViewById(R.id.tv_checking_account);
        mCurrency = findViewById(R.id.tv_currency);
        mCheckingAccountList = findViewById(R.id.rv_recycler_view);
        mPayBills = findViewById(R.id.tv_pay_bills);

        //RecyclerView LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckingAccountList.setLayoutManager(layoutManager);
        mCheckingAccountList.setHasFixedSize(true);

        //setting adapter
        mAdapter = new TransactionsAdapter(NUM_LIST_ITEMS, this);
        mCheckingAccountList.setAdapter(mAdapter);

        try {
            //init cipher, keystore, keys for encryption and decryption
            initEncryptor();
            getCustomerName();

            //catch exceptions for all crypto messages
        } catch (CertificateException
                | NoSuchAlgorithmException
                | KeyStoreException
                | NoSuchPaddingException
                | IOException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | UnrecoverableKeyException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initEncryptor() throws CertificateException, NoSuchAlgorithmException,
            KeyStoreException, IOException, NoSuchPaddingException, NoSuchProviderException,
            InvalidAlgorithmParameterException, UnrecoverableKeyException {

        //initialize wrapper classes
        keyStoreHandler = new KeyStoreHandler(this);
        cipherHandler = new CipherHandler(TRANSFORMATION_ASYMMETRIC);

        //create asymmetric key pair
        keyStoreHandler.createKeyPair(alias);

        //get asymmetric key pair
        masterKey = keyStoreHandler.getAKSAsymmetricKeyPair(alias);

    }

    public void getCustomerName() {
        //getting reference
        mCustomer = FirebaseDatabase.getInstance().getReference("Customer");

        //getting name from database
        mCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String customerName = dataSnapshot.getValue(String.class);
                try {
                    customerName = cipherHandler.decrypt(customerName, masterKey.getPrivate());
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onDataChange: " + customerName);
//                 mCheckingAccount.setText(customerName);   returning null even though log picks up encrypted values
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}