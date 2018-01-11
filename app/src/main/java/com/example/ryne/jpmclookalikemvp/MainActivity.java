package com.example.ryne.jpmclookalikemvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ryne.jpmclookalikemvp.presenter.MainPresenter;
import com.example.ryne.jpmclookalikemvp.view.MainContract;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.getInstance();
    }


    @Override
    public void showInstance(String readFromDb) {
        Toast.makeText(this, "reading", Toast.LENGTH_SHORT).show();
    }
}
