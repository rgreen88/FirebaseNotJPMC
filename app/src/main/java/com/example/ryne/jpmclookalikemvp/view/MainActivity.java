package com.example.ryne.jpmclookalikemvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ryne.jpmclookalikemvp.R;
import com.example.ryne.jpmclookalikemvp.presenter.MainPresenter;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "log";
    MainPresenter presenter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init firebase instance
        mAuth = FirebaseAuth.getInstance();

        presenter = new MainPresenter(this);
    }


    @Override
    public void showInstance() {

    }
}