package com.example.ryne.jpmclookalikemvp.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ryne.jpmclookalikemvp.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by rynel on 1/17/2018.
 */

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    @Resolve
    private void onResolved() {
        nameTxt.setText("Ryne Green");
        emailTxt.setText("rynelgreen@gmail.com");
    }
}