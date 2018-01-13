package com.example.ryne.jpmclookalikemvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ryne.jpmclookalikemvp.model.util.FirebaseAuthHandler;
import com.example.ryne.jpmclookalikemvp.presenter.MainPresenter;
import com.example.ryne.jpmclookalikemvp.view.MainContract;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MainContract.View {

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
    protected void onStart(){
        super.onStart();
        presenter.getInstance();
    }


    //toast to inform user app is reading/writing db
    @Override
    public void showInstance() {
        Toast.makeText(this, "Reading/Writing", Toast.LENGTH_SHORT).show();
    }
}

//TODO: CipherHandler class
//TODO: FirebaseAuthenticationHandler