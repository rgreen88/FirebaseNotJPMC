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
import java.text.DateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by rynel on 1/16/2018.
 */

public class MarketActivity extends BaseActivity{

    private static final String TAG = "MarketActivity";
    public DatabaseReference jpmcRef; //reference for adding/using database data

    //cipher
    public CipherHandler cipherHandler;
    public static final java.lang.String TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding";
    public String alias = "master_key";
    public KeyStoreHandler keyStoreHandler;
    public KeyPair masterKey;


    //TextViews
    TextView mCheckingAccount, mCurrentDate, mCurrency, mGainLoss, mValue, mShares;
    private String time;

    public MarketActivity(){

    }

    //creating MarketAdapter variable called mAdapter
    MarketAdapter mAdapter;

    //creating RecyclerView variable called mWeatherList
    private RecyclerView mCheckingAccountList;

    private static final int NUM_LIST_ITEMS = 50;

    //TODO: Don't forget to re-enable RecyclerView

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        //binding views
        mCurrentDate = findViewById(R.id.tv_current_date_header);
        mCheckingAccount = findViewById(R.id.tv_checking_account); //position counter rv
        mCurrency = findViewById(R.id.tv_currency);
        mCheckingAccountList = findViewById(R.id.rv_recycler_view);
        mGainLoss = findViewById(R.id.tv_shares_gains_losses);
        mValue = findViewById(R.id.tv_value);
        mShares = findViewById(R.id.tv_shares_changes);

        //RecyclerView LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckingAccountList.setLayoutManager(layoutManager);
        mCheckingAccountList.setHasFixedSize(true);

        //setting adapter
        mAdapter = new MarketAdapter(NUM_LIST_ITEMS, this);
        mCheckingAccountList.setAdapter(mAdapter);

        try {
            initEncryptor();
            getInvestment();
        } catch (CertificateException
                | NoSuchAlgorithmException
                | KeyStoreException
                | NoSuchPaddingException
                | IOException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | UnrecoverableKeyException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeyException e) {
            e.printStackTrace();
        }

        //setting time in TextView
        time = DateFormat.getDateTimeInstance().format(new Date());
        mCurrentDate.setText(time);

        //setting TextView mValue
        mValue.setText(R.string.value);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initEncryptor() throws CertificateException, NoSuchAlgorithmException,
            KeyStoreException, IOException, NoSuchPaddingException, NoSuchProviderException,
            InvalidAlgorithmParameterException, UnrecoverableKeyException {

        //initialize wrapper classes
        keyStoreHandler = new KeyStoreHandler(this);
        cipherHandler= new CipherHandler(TRANSFORMATION_ASYMMETRIC);

        //create asymmetric key pair
        keyStoreHandler.createKeyPair(alias);

        //get asymmetric key pair
        masterKey = keyStoreHandler.getAKSAsymmetricKeyPair(alias);

    }

    public void getInvestment() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {


        // Write a message to the database...may need to space out reference objects
        jpmcRef = FirebaseDatabase.getInstance().getReference("Customer").child("Name");

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: Decrypt
                String value = dataSnapshot.getValue(String.class);
                try {
                    value = cipherHandler.decrypt(value, masterKey.getPrivate());
                } catch ( InvalidKeyException
                        | IllegalBlockSizeException
                        | BadPaddingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Value is: " + value);
//                mCurrency.setText(value);
//                mGainLoss.setText(value);  //TODO: Reactivate these after xml is done
//                mShares.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
