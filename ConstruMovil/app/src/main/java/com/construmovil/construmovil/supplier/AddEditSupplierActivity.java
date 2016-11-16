package com.construmovil.construmovil.supplier;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.construmovil.construmovil.R;

public class AddEditSupplierActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_SUPPLIER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_supplier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String supplierId = getIntent().getStringExtra(SupplierActivity.EXTRA_SUPPLIER_ID);

        setTitle(supplierId == null ? "Añadir proveedor" : "Editar proveedor");

        AddEditSupplierFragment addEditSupplierFragment = (AddEditSupplierFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_supplier_container);
        if (addEditSupplierFragment == null) {
            addEditSupplierFragment = AddEditSupplierFragment.newInstance(supplierId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_supplier_container, addEditSupplierFragment)
                    .commit();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
