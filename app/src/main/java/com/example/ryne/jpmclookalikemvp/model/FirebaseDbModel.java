package com.example.ryne.jpmclookalikemvp.model;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;
import com.example.ryne.jpmclookalikemvp.model.util.CipherHandler;
import com.example.ryne.jpmclookalikemvp.model.util.KeyStoreHandler;
import com.example.ryne.jpmclookalikemvp.view.BaseActivity;
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



public class FirebaseDbModel extends BaseActivity {

    public DatabaseReference jpmcRef; //reference for adding/using database data
    public static final String TAG = "DummyTag";

    //cipher
    public CipherHandler cipherHandler;
    public static final java.lang.String TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding";
    public String alias = "master_key";
    public KeyStoreHandler keyStoreHandler;
    public KeyPair masterKey;

    TextView tvDbCustomer, tvDbChecking, tvDbTransaction, tvDbMarket;

    public FirebaseDbModel() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dummy_layout);

        //binding dummy value
        tvDbCustomer = findViewById(R.id.tv_test_db_customer);
        tvDbChecking = findViewById(R.id.tv_test_db_checking);
        tvDbTransaction = findViewById(R.id.tv_test_db_transaction);
        tvDbMarket = findViewById(R.id.tv_test_db_market);

        try {
            //init cipher, keystore, keys for encryption and decryption
            initEncryptor();
            getDbInstance();
            getCheckingAccount();
            getTransactionsAccount();
            getMarketingInvestments();

            //catch exceptions for all crypto messages
        } catch (CertificateException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException
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
        cipherHandler= new CipherHandler(TRANSFORMATION_ASYMMETRIC);

        //create asymmetric key pair
        keyStoreHandler.createKeyPair(alias);

        //get asymmetric key pair
        masterKey = keyStoreHandler.getAKSAsymmetricKeyPair(alias);

    }




    public void getDbInstance() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {


        // Write a message to the database...may need to space out reference objects
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Cipher
        jpmcRef = database.getReference("Customer").child("Name");
        jpmcRef.setValue(cipherHandler.encrypt("Ryne Green", masterKey.getPublic()));


        // Could use loop to read each datum in db if I can understand accessing info better
        // instead of invoking method manually each time with too much redundancy
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
                // dbRef.child(key).child("description").setValue(item);
                tvDbCustomer.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //explicitly writing to database only
    public void getCheckingAccount () throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        //checking info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Encrypt
        jpmcRef = database.getReference("Checking Account").child("Balance");
        jpmcRef.setValue(cipherHandler.encrypt("Balance: 1000.00", masterKey.getPublic()));
//        jpmcRef.setValue(cipherHandler.encrypt("Avalailable Credit: 1000.00", masterKey.getPublic()));

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: Decrypt (one at a time? use iterator?)
                String value = dataSnapshot.getValue(String.class);
                try {
                    value = cipherHandler.decrypt(value, masterKey.getPrivate());
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Value is: " + value);
                tvDbChecking.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void getTransactionsAccount () throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        //Transactions info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Encrypt
        jpmcRef = database.getReference("Transactions").child("Purchases");
        jpmcRef.push().setValue(cipherHandler.encrypt("Billed: 0.00", masterKey.getPublic()));
        jpmcRef.setValue(cipherHandler.encrypt("Avalailable Credit: 1000.00", masterKey.getPublic()));

        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: Decrypt (one at a time? use iterator?)
                String value = dataSnapshot.getValue(String.class);
                try {
                    value = cipherHandler.decrypt(value, masterKey.getPrivate());
                } catch ( InvalidKeyException
                        | IllegalBlockSizeException
                        | BadPaddingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Value is: " + value);
                tvDbTransaction.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void getMarketingInvestments () throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        //Market info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //TODO: Encrypt
        jpmcRef = database.getReference("Marketing Investments Account").child("Investments");
        jpmcRef.setValue(cipherHandler.encrypt("$1000.00", masterKey.getPublic()));


        //Market info current date
        //TODO: Encrypt
        jpmcRef = database.getReference("Marketing Investments Account/Investments").child("Gain");
        jpmcRef.setValue(cipherHandler.encrypt("$1000000.00 (increase symbol)",masterKey.getPublic())); //TextView on left (UNREALIZED GAIN/LOSS)

        jpmcRef = database.getReference("Marketing Investments Account/Investments").child("Loss");
        jpmcRef.push().setValue(cipherHandler.encrypt( "10000.00 (increase symbol)", masterKey.getPublic())); //TextView on left (TODAY'S CHANGE)
        //Three TextViews divided by the above using view: Left: Price, Center: Unrealized Gain/Loss, Right: "Value"
        jpmcRef.setValue(cipherHandler.encrypt("10.00", masterKey.getPublic()));                       //under Price an Equity division before info on left


        // Read from the database
        jpmcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: Decrypt (one at a time? use iterator?)
                String value = dataSnapshot.getValue(String.class);
                try {
                    value = cipherHandler.decrypt(value, masterKey.getPrivate());
                } catch ( InvalidKeyException
                        | IllegalBlockSizeException
                        | BadPaddingException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "Value is: " + value);
                tvDbMarket.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}