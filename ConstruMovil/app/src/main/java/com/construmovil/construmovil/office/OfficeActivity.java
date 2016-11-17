package com.construmovil.construmovil.office;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.construmovil.construmovil.R;

public class OfficeActivity extends AppCompatActivity {

    public static final String EXTRA_OFFICE_ID = "extra_office_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OfficeFragment fragment = (OfficeFragment)
                getSupportFragmentManager().findFragmentById(R.id.office_container);

        if (fragment == null) {
            fragment = OfficeFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.office_container, fragment)
                    .commit();
        }
    }
}