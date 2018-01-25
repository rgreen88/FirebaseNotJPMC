package com.example.ryne.jpmclookalikemvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;

import java.text.DateFormat;
import java.util.Date;

public class CheckingAccountActivity extends BaseActivity{

    private static final String TAG = "log";

    //TextViews
    TextView mGreeting, mCheckingAccount, mCurrentDate, mCurrency, mPayBills;

    private String time;

    //creating CheckingAccountAdapter variable called mAdapter
    CheckingAccountAdapter mAdapter;

    //creating RecyclerView variable called mWeatherList
    private RecyclerView mCheckingAccountList;

    private static final int NUM_LIST_ITEMS = 50;

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

//        //RecyclerView LinearLayoutManager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mCheckingAccountList.setLayoutManager(layoutManager);
//        mCheckingAccountList.setHasFixedSize(true);
//
//        //setting adapter
//        mAdapter = new CheckingAccountAdapter(NUM_LIST_ITEMS, this);
//        mCheckingAccountList.setAdapter(mAdapter);

        //setting time in TextView
        time = DateFormat.getDateTimeInstance().format(new Date());
        mCurrentDate.setText(time);
    }

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