package com.example.ryne.jpmclookalikemvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryne.jpmclookalikemvp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by rynel on 1/15/2018.
 */

//Firebase console needed to be enabled under authentication

public class EmailPasswordActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;

    //TextView and EditView inits
    private TextView tv_status;
    private TextView tv_detail;
    private EditText ed_email;
    private EditText ed_Password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpassword);

        //starting firebase
        mAuth = FirebaseAuth.getInstance();

        //binding views
        tv_status = findViewById(R.id.tv_status);
        tv_detail = findViewById(R.id.tv_detail);
        ed_email = findViewById(R.id.ed_email);
        ed_Password = findViewById(R.id.ed_password);

        //setting buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.btn_checking).setOnClickListener(this);

    }



    //Start checking user
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    //End checking user

    //Creating account
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //create account start process
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    //signing in
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        //sign-in with email
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            launchMainActivity();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        if (!task.isSuccessful()) {
                            tv_status.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    public boolean validateForm() {
        boolean valid = true;

        String email = ed_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            ed_email.setError("Required.");
            valid = false;
        } else {
            ed_email.setError(null);
        }

        String password = ed_Password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ed_Password.setError("Required.");
            valid = false;
        } else {
            ed_Password.setError(null);
        }

        return valid;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            tv_status.setText(R.string.verification_check);
            tv_detail.setText(R.string.verify);

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            //checking account activity
            findViewById(R.id.btn_checking).setEnabled(!user.isEmailVerified());
        } else {
            tv_status.setText(R.string.signed_out);
            tv_detail.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(ed_email.getText().toString(), ed_Password.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(ed_email.getText().toString(), ed_Password.getText().toString());
        } else if (i == R.id.sign_out_button) {
            signOut();
        } else if (i == R.id.btn_checking) {
            launchMainActivity();
        }
    }

    private void launchMainActivity(){
        Intent intent = new Intent(this, CheckingAccountActivity.class);
        startActivity(intent);

    }
}