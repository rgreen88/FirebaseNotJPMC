package com.example.ryne.jpmclookalikemvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryne.jpmclookalikemvp.presenter.MainPresenter;
import com.example.ryne.jpmclookalikemvp.view.MainContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "log";
    MainPresenter presenter;
    private FirebaseAuth mAuth;

    //TextView and EditView inits
    private TextView tv_status;
    private TextView tv_detail;
    private EditText ed_email;
    private EditText ed_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding views
        tv_status = findViewById(R.id.tv_status);
        tv_detail = findViewById(R.id.tv_detail);
        ed_email = findViewById(R.id.ed_email);
        ed_Password = findViewById(R.id.ed_password);

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

    public boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    public void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }
}