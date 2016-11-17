package com.construmovil.construmovil.office;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.construmovil.construmovil.R;

public class AddEditOfficeActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_OFFICE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_office);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String officeId = getIntent().getStringExtra(OfficeActivity.EXTRA_OFFICE_ID);

        setTitle(officeId == null ? "Añadir Sucursal" : "Editar Sucursal");

        AddEditOfficeFragment addEditOfficeFragment = (AddEditOfficeFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_office_container);
        if (addEditOfficeFragment == null) {
            addEditOfficeFragment = AddEditOfficeFragment.newInstance(officeId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_office_container, addEditOfficeFragment)
                    .commit();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
