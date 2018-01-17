package com.example.ryne.jpmclookalikemvp.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ryne.jpmclookalikemvp.R;

/**
 * Created by rynel on 1/17/2018.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = fullView.findViewById(R.id.action_settings);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (useToolbar())
        {
            setSupportActionBar(toolbar);
            setTitle("Activity Title");
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }
    }
    protected boolean useToolbar()
    {
        return true;
    }
}