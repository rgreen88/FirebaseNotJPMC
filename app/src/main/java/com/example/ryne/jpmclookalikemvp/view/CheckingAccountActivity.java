package com.example.ryne.jpmclookalikemvp.view;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
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

public class CheckingAccountActivity extends BaseActivity {

    private static final String TAG = "checkingacct";

    public DatabaseReference jpmcRef; //reference for adding/using database data

    //cipher
    public CipherHandler cipherHandler;
    public static final java.lang.String TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding";
    public String alias = "master_key";
    public KeyStoreHandler keyStoreHandler;
    public KeyPair masterKey;


    RunnableDemo demoThread;
    //TextViews
    TextView mGreeting, mCheckingAccount, mCurrentDate, mCurrency, mPayBills;

    private String time;

    //creating CheckingAccountAdapter variable called mAdapter
    CheckingAccountAdapter mAdapter;

    //creating RecyclerView variable called mWeatherList
    private RecyclerView mCheckingAccountList;

    private static final int NUM_LIST_ITEMS = 50;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        mGreeting = findViewById(R.id.tv_greeting);
        mCurrentDate = findViewById(R.id.tv_current_date);
        mCheckingAccount = findViewById(R.id.tv_checking_account); //position counter rv
        mCurrency = findViewById(R.id.tv_currency);
        mCheckingAccountList = findViewById(R.id.rv_recycler_view);
        mPayBills = findViewById(R.id.tv_pay_bills);

        //setting time in TextView
        time = DateFormat.getDateTimeInstance().format(new Date());
        mCurrentDate.setText(time);

        try {
            initEncryptor();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        //starting thread for data retrieval and decryption
        demoThread.start();
        demoThread.start();

        //RecyclerView LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckingAccountList.setLayoutManager(layoutManager);
        mCheckingAccountList.setHasFixedSize(true);

        //setting adapter
        mAdapter = new CheckingAccountAdapter(NUM_LIST_ITEMS, this);
        mCheckingAccountList.setAdapter(mAdapter);
    }

    //TODO: create threading for initEncryptor (should be able to reuse and recall right?)
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

    //TODO: implementing thread in parts starting with a structure
    class RunnableDemo implements Runnable {
        private Thread t;
        private String threadName;

        RunnableDemo( String name) {
            threadName = name;
            System.out.println("Creating " +  threadName );
        }

        public void run() {
            // Referencing database path...may need to space out reference objects
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            //TODO: Cipher decrypt from database
            jpmcRef = database.getReference("Customer").child("Name");
            jpmcRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //TODO: Decrypt
                    String value = dataSnapshot.getValue(String.class);
                    try {
                        value = cipherHandler.decrypt(value, masterKey.getPrivate());
                    } catch (InvalidKeyException
                            | IllegalBlockSizeException
                            | BadPaddingException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "Value is: " + value);
                    mGreeting.setText(value);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Cancelled");
                }

            });

        }

        public void start () {

            //start the database data retrieval
            if (t == null) {
                t = new Thread (this, threadName);
                t.start ();
            }
        }
    }

    //does this need to be static?
    public class TestThread {

        public void main(String args[]) {
            RunnableDemo R1 = new RunnableDemo( "Thread-1");
            R1.start();

            RunnableDemo R2 = new RunnableDemo( "Thread-2");
            R2.start();
        }
    }


//    //TODO: create threading for datasnap
//    public void getCustomerGreeting() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
//        // Referencing database path...may need to space out reference objects
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        //TODO: Cipher decrypt from database
//        jpmcRef = database.getReference("Customer").child("Name");
//        jpmcRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                //TODO: Decrypt
//                String value = dataSnapshot.getValue(String.class);
//                try {
//                    value = cipherHandler.decrypt(value, masterKey.getPrivate());
//                } catch (InvalidKeyException
//                        | IllegalBlockSizeException
//                        | BadPaddingException e) {
//                    e.printStackTrace();
//                }
//                Log.d(TAG, "Value is: " + value);
//                mGreeting.setText(value);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "Cancelled");
//            }
//
//        });

//    }
    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}