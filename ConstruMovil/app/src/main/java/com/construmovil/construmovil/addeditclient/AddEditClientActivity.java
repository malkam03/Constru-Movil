package com.construmovil.construmovil.addeditclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.construmovil.construmovil.R;
import com.construmovil.construmovil.clients.ClientsActivity;

public class AddEditClientActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_CLIENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String clientId = getIntent().getStringExtra(ClientsActivity.EXTRA_CLIENT_ID);

        setTitle(clientId == null ? "Añadir cliente" : "Editar cliente");

        AddEditClientFragment addEditClientFragment = (AddEditClientFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_client_container);
        if (addEditClientFragment == null) {
            addEditClientFragment = AddEditClientFragment.newInstance(clientId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_client_container, addEditClientFragment)
                    .commit();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
