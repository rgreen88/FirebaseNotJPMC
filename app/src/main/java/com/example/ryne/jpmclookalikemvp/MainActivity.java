package com.example.ryne.jpmclookalikemvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ryne.jpmclookalikemvp.presenter.MainPresenter;
import com.example.ryne.jpmclookalikemvp.view.MainContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    protected void onStart(){
        super.onStart();

        //checks if user is currently signed on
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        presenter.getInstance();
    }


    //toast to inform user app is reading/writing db
    @Override
    public void showInstance() {
        Toast.makeText(this, "Reading/Writing", Toast.LENGTH_SHORT).show();
    }

    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
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

//TODO: CipherHandler class
//TODO: FirebaseAuthenticationHandler