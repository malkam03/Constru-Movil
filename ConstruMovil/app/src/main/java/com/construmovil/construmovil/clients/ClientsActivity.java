package com.construmovil.construmovil.clients;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.construmovil.construmovil.R;

public class ClientsActivity extends AppCompatActivity {

    public static final String EXTRA_CLIENT_ID = "extra_client_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClientsFragment fragment = (ClientsFragment)
                getSupportFragmentManager().findFragmentById(R.id.clients_container);

        if (fragment == null) {
            fragment = ClientsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.clients_container, fragment)
                    .commit();
        }
    }
}
