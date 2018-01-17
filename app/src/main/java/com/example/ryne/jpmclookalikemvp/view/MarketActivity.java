package com.example.ryne.jpmclookalikemvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;

/**
 * Created by rynel on 1/16/2018.
 */

public class MarketActivity extends BaseActivity{

    //TextViews
    TextView mGreeting, mCheckingAccount, mCurrentDate, mCurrency, mPayBills;

    //creating RecyclerViewAdapter variable called mAdapter
    RecyclerViewAdapter mAdapter;

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
        mCheckingAccount = findViewById(R.id.tv_checking_account);
        mCurrency = findViewById(R.id.tv_currency);
        mCheckingAccountList = findViewById(R.id.rv_recycler_view);
        mPayBills = findViewById(R.id.tv_pay_bills);

        //RecyclerView LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCheckingAccountList.setLayoutManager(layoutManager);
        mCheckingAccountList.setHasFixedSize(true);

        //setting adapter
        mAdapter = new RecyclerViewAdapter(NUM_LIST_ITEMS, this);
        mCheckingAccountList.setAdapter(mAdapter);
    }
}
