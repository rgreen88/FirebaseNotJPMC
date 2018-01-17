package com.example.ryne.jpmclookalikemvp.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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

public class EmailPasswordActivity extends CheckingAccountActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;

    //TextView and EditView inits
    private TextView tv_status;
    private TextView tv_detail;
    private EditText ed_email;
    private EditText ed_Password;

    //nav drawer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpassword);

        // Find our drawer view
        nvDrawer = findViewById(R.id.nvView);

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

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

        //starting firebase
        mAuth = FirebaseAuth.getInstance();

//        // Lookup navigation view
//        NavigationView navigationView = findViewById(R.id.nvView);
//
//        // Inflate the header view at runtime
//        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
//        headerLayout = navigationView.getHeaderView(0);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_checking_fragment:
                fragmentClass = CheckingAccountActivity.class;
                break;
            case R.id.nav_transactions_fragment:
                fragmentClass = TransactionsActivity.class;
                break;
            case R.id.nav_market_fragment:
                fragmentClass = MarketActivity.class;
                break;
            default:
                fragmentClass = CheckingAccountActivity.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference. ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    //in case some change such as screen rotation occurs
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        showProgressDialog();

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
                        hideProgressDialog();
                    }
                });
    }

    //signing in
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

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
                        hideProgressDialog();
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
        hideProgressDialog();
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