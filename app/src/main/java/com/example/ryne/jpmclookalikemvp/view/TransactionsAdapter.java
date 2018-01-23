package com.example.ryne.jpmclookalikemvp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>{

    private int mTransactionItem;
    private Context context;
    //display position in list using getItemCount() - 1
    private TextView listCheckingBalanceView;
    private static final String TAG = "MainActivityTag";
    public static final java.lang.String TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding";
    public String alias = "master_key";
    public KeyStoreHandler keyStoreHandler;
    KeyPair masterKey;
    private CipherHandler cipherHandler;
    private TextView mPrice = null;

    public TransactionsAdapter(int numOfItems, Context context){
        mTransactionItem = numOfItems;
        this.context = context;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public TransactionsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_view_layout_transactions;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        TransactionsHolder viewHolder = null;

        viewHolder = new TransactionsHolder(view);
//
//        try {
//            initEncryptor();
//        }catch (CertificateException
//                | NoSuchAlgorithmException
//                | KeyStoreException
//                | NoSuchPaddingException
//                | IOException
//                | NoSuchProviderException
//                | InvalidAlgorithmParameterException
//                | UnrecoverableKeyException e) {
//            e.printStackTrace();
//        }
//        getPrice();

        return viewHolder;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onBindViewHolder(TransactionsHolder holder, int position) {
//        try {
//            initEncryptor();
//        }catch (CertificateException
//                | NoSuchAlgorithmException
//                | KeyStoreException
//                | NoSuchPaddingException
//                | IOException
//                | NoSuchProviderException
//                | InvalidAlgorithmParameterException
//                | UnrecoverableKeyException e) {
//            e.printStackTrace();
//        }
//        getPrice();
        Log.d(TAG, "#" + position); //check position
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mTransactionItem;
    }

    class TransactionsHolder extends RecyclerView.ViewHolder{



        //constructor for ViewHolder with int reference to position
        public TransactionsHolder(View itemView) {
            super(itemView);

            mPrice = itemView.findViewById(R.id.tv_amount);

            listCheckingBalanceView = itemView.findViewById(R.id.tv_checking_account); //TODO: Probably the culprit behind the crashes from list number views on rv

        }

        void bind(int listIndex) {
            listCheckingBalanceView.setText(String.valueOf(listIndex));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initEncryptor() throws CertificateException, NoSuchAlgorithmException,
            KeyStoreException, IOException, NoSuchPaddingException, NoSuchProviderException,
            InvalidAlgorithmParameterException, UnrecoverableKeyException {

        //initialize wrapper classes
        keyStoreHandler = new KeyStoreHandler(context);
        cipherHandler = new CipherHandler(TRANSFORMATION_ASYMMETRIC);

        //create asymmetric key pair
        keyStoreHandler.createKeyPair(alias);

        //get asymmetric key pair
        masterKey = keyStoreHandler.getAKSAsymmetricKeyPair(alias);

    }

    private void getPrice() {

        //getting reference
        final DatabaseReference dbPrice = FirebaseDatabase.getInstance().getReference("Transactions");

        //getting name from database
        dbPrice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String transactionPrice = dataSnapshot.getValue(String.class);
                try {
                    transactionPrice = cipherHandler.decrypt(transactionPrice, masterKey.getPrivate()); //System error as warning
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onDataChange: " + transactionPrice);
                mPrice.setText(transactionPrice);  //returns values still encrypted
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}