package com.example.ryne.jpmclookalikemvp.model.util;

import com.example.ryne.jpmclookalikemvp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by rynel on 1/15/2018.
 */

public class EmailPasswordActivity extends MainActivity{

    private FirebaseAuth mAuth;

    //Start checking user
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    //End checking user


}
